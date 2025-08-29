package com.juaracoding.pcmspringboot5.utils;

import com.juaracoding.pcmspringboot5.config.OtherConfig;

import java.util.regex.Pattern;


public class GlobalFunction {

    public static void print(Object obj){
        if(OtherConfig.getEnablePrintConsole().equals("y"))
        {
            System.out.println(obj);
        }
    }

    public static Boolean checkValue(String value){
        Boolean isValid = Pattern.compile("^[\\w\\s\\.]{1,30}$").matcher(value).find();
//        System.out.println("CUMI");
        GlobalFunction.print("CUMI");
        return isValid;
    }
}
