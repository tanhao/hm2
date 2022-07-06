package com.xzy.auth.config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzy.auth.ext.UserLoginExt;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.common.model.response.ResultCode;
import com.xzy.pojo.user.code.AuthCode;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.user.UserLoginService;
import com.xzy.user.UserService;
import com.xzy.utils.LoginUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Pattern;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    LoginUtil loginUtil;
    @DubboReference
    private UserService userService;
    @DubboReference
    private UserLoginService userLoginService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    /**
     * 登录验证成功处理方法
     *
     * @return
     */
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            //获取登录数据保存redis中
            UserLoginExt principal = (UserLoginExt) (authentication.getPrincipal());
            UserLogin userLogin = principal.getUserLogin();
            String userId = userLogin.getUserId().toString();
            String content = JSONObject.toJSONString(userLogin);
            loginUtil.saveToken(userLogin.getUid(), userId, content);
            userService.passwordErrorReset(userLogin.getEnabledSon(),userLogin.getUserId());
            userLoginService.saveLogin(userLogin);//添加登录日志
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(objectMapper.writeValueAsString(new ResponseData(AuthCode.AUTH_LOGIN_SUCCESS, userLogin)));
            out.flush();
            out.close();
        };
    }

    public static <T extends ResultCode> T getByCode(Class<T> enumClass, int code) {
        return Arrays.asList(enumClass.getEnumConstants()).stream().filter(enumItem -> enumItem.code() == code).findFirst().get();
    }

    /**
     * 登录验证失败处理
     *
     * @return
     */
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, ex) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            ResponseResult responseResult;
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
                responseResult = ResponseResult.ERROR(AuthCode.AUTH_CREDENTIAL_ERROR);
            } else if (pattern.matcher(ex.getMessage()).matches()) {
                //处理验证码错误，返回提示
                int code = Integer.parseInt(ex.getMessage());
                ResultCode byCode = getByCode(AuthCode.class, code);
                responseResult = ResponseResult.ERROR(byCode);
            } else {
                responseResult = ResponseResult.ERROR(AuthCode.AUTH_LOGIN_ERROR);
            }
            out.write(objectMapper.writeValueAsString(responseResult));
            out.flush();
            out.close();
        };
    }

    /**
     * 未登录返回json提示
     *
     * @return
     */
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            out.write(objectMapper.writeValueAsString(ResponseData.ERROR(CommonCode.UNAUTHENTICATED)));
            out.flush();
            out.close();
        };
    }

    /**
     * 登录成功json提示
     *
     * @return
     */
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(objectMapper.writeValueAsString(ResponseData.SUCCESS()));
            out.flush();
            out.close();
        };
    }

    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(objectMapper.writeValueAsString(ResponseData.ERROR(CommonCode.UNAUTHORISE)));
            out.flush();
            out.close();
        };
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    /**
     * 不拦截url地址
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/captcha");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().formLogin();
        //未登录拦截
        http.authenticationProvider(authenticationProvider()).httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint());
        //退出成功，返回json
        http.logout().logoutSuccessHandler(logoutSuccessHandler());
        //验证操作权限
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        //进行自定义验证账号
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //开启跨域访问
        http.cors().disable();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();
    }


}