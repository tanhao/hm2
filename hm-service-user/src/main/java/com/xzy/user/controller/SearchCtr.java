package com.xzy.user.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.xzy.controller.BaseController;
import com.xzy.user.WebSiteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

@Slf4j
@Controller
@RequestMapping("search")
public class SearchCtr extends BaseController {
    @DubboReference
    WebSiteService webSiteService;


    @RequestMapping("code")
    public String search(String code, String url, HttpServletResponse httpServletResponse) {
        log.info(url + "---" + code);
        try {
            if (StrUtil.isNotBlank(code) && null != webSiteService.selectByCode(code)) {
                httpServletResponse.addHeader("code",code);
                return "redirect:" + url + "setling?code="+code;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd" + HttpUtil.encodeParams(code, Charset.defaultCharset());
    }

}
