package com.juaracoding.pcmspringboot5.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContohService {
    @Autowired
    Contoh2Service  contoh2Service;

    public String save(){
        return "Data Berhasil Disimpan";
    }

    public String contoh2ServiceReturn(){
        return contoh2Service.save();
    }
}
