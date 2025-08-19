package com.juaracoding.pcmspringboot5.coretan;

import java.util.Random;

public class RandomExample {

    public static void main(String[] args) {
        Random rand = new Random();
        String strVokal = "aiueo";
        String strVokalKapital = "AIUEO";
        String strKonsonan = "bcdfghjklmnpqrstvwxyz";
        String strKonsonanKapital = "BCDFGHJKLMNPQRSTVWXYZ";
        String strAlfabet = "abcdfghiejklmnopqrstuvwxyz";
        String strAlfabetKapital = "ABCDFGHIEJKLMNOPQRSTUVWXYZ";
        char [] crVokal = strVokal.toCharArray();

        int intRandInitial = rand.nextInt(2);
        System.out.println("Initial: " + intRandInitial);
        if(intRandInitial == 0){
            intRandInitial = 1;
            System.out.print(strVokalKapital.charAt(rand.nextInt(strVokalKapital.length())));
        }else{
            intRandInitial = 0;
            System.out.print(strKonsonanKapital.charAt(rand.nextInt(strKonsonanKapital.length())));
        }//step 1

        for(int i = 0 ; i <6; i++){
            if(intRandInitial == 0){
                intRandInitial = 1;
                System.out.print(strVokal.charAt(rand.nextInt(strVokal.length())));
            }else{
                intRandInitial = 0;
                System.out.print(strKonsonan.charAt(rand.nextInt(strKonsonan.length())));
            }
        }
//        System.out.print(strAlfabet.charAt(rand.nextInt(strAlfabet.length())));
//        flag

        //step 2
//        int intLength = rand.nextInt(12,26);
//        for (int i = 0; i < intLength; i++) {
////            System.out.println(strVokal.charAt(rand.nextInt(strVokal.length())));
//            System.out.print(crVokal[rand.nextInt(strVokal.length())]);
//        }
//        System.out.println(strAlfabetKapital.toUpperCase());
//        int intLength = rand.nextInt(12,25);
//        for (int i = 0; i < 10; i++) {
////            System.out.println(rand.nextInt(10));//random dari 0 s.d 9
////            System.out.println(rand.nextInt(11));//random dari 0 s.d 10
////            System.out.println(rand.nextInt(10,21));// ini adalah random dari 10 s.d 20
//        }
    }
}
