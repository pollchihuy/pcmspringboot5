package com.juaracoding.pcmspringboot5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContohSSRController {

    @GetMapping("/cobadoank")
    public String cobaDoankz(Model model){
        model.addAttribute("valueTest","Ini Loch");
        return "testaja/coba";
    }
}