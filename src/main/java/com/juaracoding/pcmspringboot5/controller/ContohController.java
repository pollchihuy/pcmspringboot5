package com.juaracoding.pcmspringboot5.controller;

import com.juaracoding.pcmspringboot5.coretan.Coba;
import com.juaracoding.pcmspringboot5.service.Contoh2Service;
import com.juaracoding.pcmspringboot5.service.ContohService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ContohController {

    @Autowired
    ContohService contohService;

    //serverLess
    @GetMapping("/hello")
    public Map<String,Object> hello(){
        Coba coba = new Coba();
        coba.setNama("Paul");
        coba.setPesan("Cobaan");
        Map<String,Object> map = new HashMap<>();
        map.put("message","Hello World");
        map.put("status",true);
        map.put("data",coba);
        map.put("timestamp", LocalDateTime.now().toString());
        return map;
    }
    @GetMapping("/save")
    public String save(){

        return contohService.save();
    }
}
