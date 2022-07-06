package com.xzy.auth.config;

import com.xzy.user.UserService;
import com.xzy.utils.IPUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆失败监听
 */
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @DubboReference
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        try {
            AuthenticationException ex = event.getException();
            if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
                String account = event.getAuthentication().getPrincipal().toString();
                WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
                String ipAddr = IPUtil.getIpAddress(request);
                userService.passwordErrorIncrement(account, ipAddr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}