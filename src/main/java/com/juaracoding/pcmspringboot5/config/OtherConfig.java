package com.juaracoding.pcmspringboot5.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 18/08/2025 21:58
@Last Modified 18/08/2025 21:58
Version 1.0
*/
@Configuration
@PropertySource("classpath:otherconfig.properties")
public class OtherConfig {

    private static String enablePrint;

    public static String getEnablePrint() {
        return enablePrint;
    }

    @Value("${enable.print}")
    private void setEnablePrint(String enablePrint) {
        this.enablePrint = enablePrint;
    }
}