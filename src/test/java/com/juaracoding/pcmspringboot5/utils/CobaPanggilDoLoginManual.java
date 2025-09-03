package com.juaracoding.pcmspringboot5.utils;

public class CobaPanggilDoLoginManual {

public static void main(String[] args) {
//        System.out.println(new TokenGenerator(null).getToken());

        DataGenerator gen = new DataGenerator();
        for (int i = 0; i < 10; i++) {
            System.out.println(gen.dataEmail());
        }
    }
}
