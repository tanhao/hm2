package com.xzy.result.ctr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageCtr {

    @GetMapping("/")
    public String login() {
        return "/pages/login.html";
    }

}
