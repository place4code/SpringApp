package com.place4code.reddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foo")
public class FooController {

    //#########################################
    // foo view for testing
    @GetMapping("/foo")
    public String foo(Model model) {
        model.addAttribute("title", "foo page for testing");
        return "foo";
    }

}
