package com.juaracoding.pcmspringboot5.coretan;

import java.util.regex.Pattern;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 18/08/2025 21:35
@Last Modified 18/08/2025 21:35
Version 1.0
*/
public class ContohRegex {

    public static void main(String[] args) {

        String strInput = "CumiBakar13!";
        if(Pattern.compile("^[\\w]{2,}$")
                .matcher(strInput)
                .matches())
        {
            System.out.println("VALID");
        }else {
            System.out.println("TIDAK VALID");
        }
    }
}