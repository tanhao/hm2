package com.xzy.auth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;


@RestController
@Slf4j
public class AuthController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/captcha")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        ServletOutputStream out = response.getOutputStream();
        try {
            String pictureKey = UUID.randomUUID().toString();
            //返回图片key
            response.setHeader("pictureKey", pictureKey);
            //定义图形验证码的长和宽
            LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100, 4, 500);
            captcha.write(out);
            session.setAttribute("captcha_code", captcha.getCode());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("图形验证码生成出错");
        } finally {
            out.close();
        }
    }
}
