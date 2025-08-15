package com.juaracoding.pcmspringboot5.controller;

import com.juaracoding.pcmspringboot5.config.SMTPConfig;
import com.juaracoding.pcmspringboot5.coretan.Coba;
import com.juaracoding.pcmspringboot5.service.Contoh2Service;
import com.juaracoding.pcmspringboot5.service.ContohService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @RequestBody
 * @PathVariable
 * @RequestParam 
 */
@RestController
@RequestMapping("contoh")
public class ContohController {

    @Autowired
    ContohService contohService;

// hello
// /save
// /callAsync
// /callProp

// contoh/hello
// contoh/save
// contoh/callAsync
// contoh/**
    @Value("${cumi.cumi}")
    private String propertiesCumi;

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

    @GetMapping("/callcumi")
    public String callCumi(){

        return propertiesCumi;
    }
    @GetMapping("/callasync")
    public String callAsync(){
        contohService.callAsync();
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error "+e.getMessage());
            }
            System.out.println("Controller "+(i+1));
        }
        return "Selesai";
    }

    @GetMapping("/callprop")
    public String callProperties(){
        String username = SMTPConfig.getEmailUsername();
        String password = SMTPConfig.getEmailPassword();
        System.out.println("Print Email Username "+ username);
        System.out.println("Print Email Password "+password);

        return "Username : "+username+" -- Password : "+password;
    }


}
