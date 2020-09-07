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

    @GetMapping(value = "/hupu")
    public String hupuPage(Model model) {
        model.addAttribute("mainTitle", "虎扑热榜");
        model.addAttribute("entities", ms.getAllEntities("hupu"));
        return "hot";
    }

    @GetMapping(value = "/v2ex")
    public String v2Page(Model model) {
        model.addAttribute("mainTitle", "v2ray热榜");
        model.addAttribute("entities", ms.getAllEntities("v2ex"));
        return "hot";
    }

    @GetMapping(value = "/zhihu")
    public String zhihuPage(Model model) {
        model.addAttribute("mainTitle", "知乎热榜");
        model.addAttribute("entities", ms.getAllEntities("zhihu"));
        return "hot";
    }
}
