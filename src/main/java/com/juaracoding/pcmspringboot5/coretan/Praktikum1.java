package com.juaracoding.pcmspringboot5.coretan;


import java.util.Scanner;

public class Praktikum1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Masukkan Kata : ");
        String strInput = sc.nextLine();
        char [] chArr = strInput.toCharArray();
        int intTotal = 0;
        int intTampung = 0;
        for (int i = 0; i < chArr.length; i++) {
            intTampung = chArr[i];
            if(!(intTampung>=97 && intTampung<=122)){
                intTotal += intTampung;
            }
        }
        System.out.println("Jadi Ouput Dari "+strInput+" Adalah : "+intTotal);
    }
}
