package com.juaracoding.pcmspringboot5.coretan;

import java.util.Random;
import java.util.UUID;

public class RandomDataEmail {

    public static void main(String[] args) {


        /**
         * asoij123@gmail.com
         * 1. huruf kecil (min 5 maks 20)
         * 2. angka 3 digit
         * 3. @
         * 4. provider
         * 5. titik .
         * 6. domain
         */
        Random rand = new Random();
        char [] chAlfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String [] strProviderArr = {"gmail","yahoo","ymail","rocketmail"};
        String [] strDomainArr = {"com","co.id","gov","net","id"};
        int intLengthProvider= strProviderArr.length;
        int intLengthDomain= strDomainArr.length;

        for (int i = 0; i < 1000000; i++) {
            int intAlfabetLength = rand.nextInt(5,21);
//            System.out.println("Panjang Random char email "+intAlfabetLength);
            for (int j = 0; j < intAlfabetLength; j++) {
                System.out.print(chAlfabet[rand.nextInt(chAlfabet.length)]);
            }
            System.out.print(rand.nextInt(1000)+"@"+strProviderArr[rand.nextInt(intLengthProvider)]+"."+strDomainArr[rand.nextInt(intLengthDomain)]);//0 s.d 999
            System.out.println();
        }
    }
}
