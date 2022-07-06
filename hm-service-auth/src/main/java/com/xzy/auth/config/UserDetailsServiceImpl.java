package com.xzy.auth.config;

import cn.hutool.core.bean.copier.BeanCopier;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgentUtil;
import com.nepxion.discovery.plugin.strategy.service.annotation.ServiceStrategy;
import com.xzy.auth.ext.UserLoginExt;
import com.xzy.auth.utils.BCryptUtil;
import com.xzy.enums.UserEnums;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.HmUserSon;
import com.xzy.pojo.user.HmWebSite;
import com.xzy.pojo.user.code.AuthCode;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.user.UserService;
import com.xzy.user.UserSonService;
import com.xzy.user.WebSiteService;
import com.xzy.utils.IPUtil;
import com.xzy.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@ServiceStrategy
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private HttpServletRequest request;
    @DubboReference
    private UserService userService;

    @DubboReference
    private UserSonService userSonService;
    @DubboReference
    WebSiteService webSiteService;

    /**
     * 重写登录逻辑，主要判断账号，密码是否正确
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username + "进行登录验证...");
        UserLogin userLogin = new UserLogin();
        HmUser hmUser = userService.findByUsername(username);
        if (hmUser == null) {
            //主账号查询不到，去子账号表查询
            HmUserSon userSon = userSonService.findByUsername(username);
            if (userSon == null) {
                //都查不到抛出异常
                throw new UsernameNotFoundException("用户名不存在");
            } else {
                //复制子账号数据
                BeanCopier.create(userSon, userLogin, CopyOptions.create()).copy();
                HmUser superUser = userService.findById(userSon.getPrimaryId());
                //主账号关闭，子账号也不能登录
                if (superUser.getStatus().equals(UserEnums.Status.CLOSE.toString())) {
                    throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_ACCOUNT_CLOSE.code()));
                }
                userLogin.setUserLevel(superUser.getUserLevel());
                userLogin.setUserId(userSon.getSonId());
                userLogin.setOddsAgentId(superUser.getOddsAgentId());
                userLogin.setEnabledSon(true);
                userLogin.setParentId(superUser.getParentId());
                userLogin.setParentPath(superUser.getParentPath());
                userLogin.setUserType(superUser.getUserType());
                userLogin.setLoginIpBound(superUser.getLoginIpBound());
            }
        } else {
            //复制主账号数据
            BeanCopier.create(hmUser, userLogin, CopyOptions.create()).copy();
            if (hmUser.getUserType().equals(UserEnums.Utype.MEMBER.toString())) {
                UserInfo info = userService.findBaseById(hmUser.getUserId());
                userLogin.setMarketSelect(info.getMarketSelect());
                userLogin.setMarketOpen(info.getMarketOpen());
            }
        }
        String url = request.getHeader("realServerName");
        HmWebSite hmWebSite = webSiteService.getCompanyName(userLogin.getCompanyId());
        if (!hmWebSite.getSiteUrls().contains(url)) {
            log.info("[{}]网址错误[{}]", userLogin.getCompanyId(), url);
            throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_CREDENTIAL_ERROR.code()));
        }
        Object loginType = request.getSession().getAttribute("loginType");
        Object loginMode = request.getSession().getAttribute("loginMode");
        if (loginType == null || !loginType.equals(userLogin.getUserType())) {
            throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_LOGIN_TYPE_ERROR.code()));
        }
        if (userLogin.getStatus().equals(UserEnums.Status.CLOSE.toString())) {
            throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_ACCOUNT_CLOSE.code()));
        }
        if (userLogin.getPasswordError() >= 5) {
            throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_PASSWORD_ERROR.code()));
        }
        String ipAddr = IPUtil.getIpAddress(request);
        if (StrUtil.isNotBlank(userLogin.getLoginIpBound()) && !userLogin.getLoginIpBound().contains(ipAddr)) {
            log.info("登录ip[{}]不在[{}]", ipAddr, userLogin.getLoginIpBound());
            throw new AuthenticationServiceException(String.valueOf(AuthCode.AUTH_BOUND_IP.code()));
        }
        String agent = request.getHeader("user-agent");
        userLogin.setLoginTime(new Date());
        userLogin.setLoginIp(ipAddr);
        userLogin.setLoginDevice(WebUtil.getBrowserName(agent));
        userLogin.setHttpHeader(WebUtil.getRequestUserAgent(UserAgentUtil.parse(request.getHeader("User-Agent"))));
        //todo 角色信息还需要另外查询
        //构建角色列表
        List<GrantedAuthority> grantAuths = new ArrayList();
        grantAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UserLoginExt userLoginExt = new UserLoginExt(username, userLogin.getPassword(), grantAuths);
        userLogin.setPassword(null);
        String uid = BCryptUtil.uuid();
        userLogin.setUid(uid);
        userLogin.setLoginMode(loginMode.toString());
        userLoginExt.setUserLogin(userLogin);
        return userLoginExt;
    }
}