package com.juaracoding.pcmspringboot5.utils;

import com.juaracoding.pcmspringboot5.config.OtherConfig;


public class GlobalFunction {

    public static void print(Object obj){
        if(OtherConfig.getEnablePrint().equals("y"))
        {
            System.out.println(obj);
        }
    }
}
