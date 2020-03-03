package com.tonyzhang.touchfish.controller;

import com.tonyzhang.touchfish.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private MainService ms;

    @GetMapping(value = "")
    public String formPage(Model model) {
        return "hahaha";
    }
}
