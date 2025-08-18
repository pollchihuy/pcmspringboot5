package com.juaracoding.pcmspringboot5.utils;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class WriteExcel {

    //UDF User Defined Function
    public void readFileExcel(){

    }

    public void readFileExcel207(){

    }

    @Scheduled(cron = "0 0 12 * * *")
    public void printData(){
        System.out.println("Ke Print Broh ");
    }

    @Async
    public void printData2() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
//            System.out.println("Detik Ke "+(i+1));
            GlobalFunction.print("Detik Ke "+(i+1));
        }
    }
}
