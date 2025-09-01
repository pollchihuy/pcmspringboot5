package com.juaracoding.pcmspringboot5.security;

import java.util.function.Function;

public class BcryptImpl {

    private static final BcryptCustom bcrypt = new BcryptCustom(11);

    public static String hash(String password) {
        return bcrypt.hash(password);
    }

    public static boolean verifyAndUpdateHash(String password,
                                              String hash,
                                              Function<String, Boolean> updateFunc) {
        return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

    public static boolean verifyHash(String password , String hash)
    {
        return bcrypt.verifyHash(password,hash);
    }
    
    public static void main(String[] args) {
//        System.out.println(hash("GilangKeren@1234"));

        System.out.println(BcryptImpl.verifyHash("gilang.321"+"Paul@1234","$2a$11$sDMdffX.mmt4xJXOBTvR/.tzlGChFRTNlafCWa/yjsYeOcUEqnEui"));
//        System.out.println(verifyHash("paul.321"+"Paul@1234","$2a$11$hiOENl4e2BCTRKvjJOIM4eVy/DghgypcnNb9qtEEdOM1IhcM.3MKm"));
//        System.out.println(verifyHash("gilang.321"+"Paul@1234","$2a$11$hiOENl4e2BCTRKvjJOIM4eVy/DghgypcnNb9qtEEdOM1IhcM.3MKm"));
    }
}