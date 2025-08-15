package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.utils.WriteExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class Contoh2Service {

    @Autowired
    WriteExcel writeExcel;

    @Autowired
    private Random random;

    public String save(){
        return "Contoh 2";
    }
}
