package com.xzy.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzy.auth.ext.UserLoginExt;
import com.xzy.pojo.user.code.AuthCode;
import com.xzy.pojo.user.ext.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 自定义拦截器，判断登录的参数是否json格式
 * 如果是json格式进行处理，不是就调用父类原有方法
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Value("${auth.captcha-enabled}")
    Boolean captchaEnabled;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            String username = null;
            String password = null;
            try (InputStream is = request.getInputStream()) {
                Map<String, String> map = new ObjectMapper().readValue(is, Map.class);
                username = map.get("username");
                password = map.get("password");
                request.getSession().setAttribute("loginType", map.get("loginType"));
                request.getSession().setAttribute("loginMode", map.get("loginMode"));
                if (null != captchaEnabled && captchaEnabled) {
                    String captcha = map.get("captcha");
                    if (StringUtils.isEmpty(captcha)) {
                        //抛出验证码错误
                        throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_VERIFYCODE_NONE.code()));
                    }
                    //在session中获取验证码
                    String genCaptcha = (String) request.getSession().getAttribute("captcha_code");
                    if (StringUtils.isEmpty(genCaptcha)) {
                        throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_CAPTCHA_GET.code()));
                    }
                    //不管是否匹配修需要先删除session中验证码，重新请求图片
                    request.getSession().removeAttribute("captcha_code");
                    //验证吗是否比配
                    if (!genCaptcha.equals(captcha)) {
                        throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_CAPTCHA_ERROR.code()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);
            Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
            this.addValid(authenticate);
            log.info("{}登录成功", username);
            return authenticate;
        }
        return super.attemptAuthentication(request, response);
    }

    /**
     * 登录成功后验证需要验证的内容
     *
     * @param authenticate
     */
    public void addValid(Authentication authenticate) {
        UserLoginExt principal = (UserLoginExt) (authenticate.getPrincipal());
        UserLogin userLogin = principal.getUserLogin();
        //登录成功后还需要验证是否需要修改密码
        if(userLogin.getUserType().equals("MEMBER")){
            if (userLogin.getResetPassword()>0) {
                if(userLogin.getMarketSelect().equals("SG")){
                    throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_PASSWORD_UPDATE_SG.code()));
                }else {
                    throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_PASSWORD_UPDATE_IDC.code()));
                }
            }
        }else {
            if (userLogin.getResetPassword().equals(1)) {
                throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_PASSWORD_UPDATE.code()));
            } else if (userLogin.getResetPassword().equals(2)) {
                throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_PASSWORD_EXPIRE.code()));
            }
        }
    }

}