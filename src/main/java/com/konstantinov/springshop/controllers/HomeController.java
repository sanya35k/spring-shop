package com.konstantinov.springshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {

        return "redirect:/products";
    }
}
