package com.juaracoding.pcmspringboot5.security;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class Crypto {

//    private static final String defaultKey = "4d05ac36ce72f080dc9e29af7d03bf0dd15a530305415c811a6a64767108145d";
    private static final String defaultKey = "c953245d42feab21959733db0300bb2c296f54fc4fc7f1c150776b7800d27365";

    public static String performEncrypt(String keyText, String plainText) {
        try{
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] ptBytes = plainText.getBytes();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(true, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
            int oLen = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(Hex.encode(rv));
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performEncrypt(String cryptoText) {
        return performEncrypt(defaultKey, cryptoText);
    }

    public static String performDecrypt(String keyText, String cryptoText) {
        try {
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] cipherText = Hex.decode(cryptoText.getBytes());
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(false, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(cipherText.length)];
            int oLen = cipher.processBytes(cipherText, 0, cipherText.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(rv).trim();
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performDecrypt(String cryptoText) {
        return performDecrypt(defaultKey, cryptoText);
    }

    public static void main(String[] args) {
//        String strToEncrypt = "jdbc:sqlserver://host.docker.internal;databaseName=BEB24;schema=dbproject;trustServerCertificate=true";//put text to encrypt in here
//        String strToEncrypt = "jdbc:sqlserver://host.docker.internal;trustServerCertificate=true;databaseName=BEB25";//put text to encrypt in here
        String strToEncrypt = "sa123";//put text to encrypt in here
        System.out.println("Encryption Result : "+performEncrypt(strToEncrypt));

//        jdbc:sqlserver://java-be-1:3377;databaseName=BEB24;schema=dbproject;trustServerCertificate=true
//        String strToDecrypt = "09ce6bf5076fd9ad1c2d8b7f745884726ceb17ce145e01772186f25d9632ce49";//put text to decrypt in here
        String strToDecrypt = "588bc46a110c808697d6fcc0c23896b3886382d67b10c002ee90e8a71ba414ba0eae40c76708242af6bb57baf4a4b2054e8ba73462bd486fd4144bb34abbceadebd6c4256e41fb155dd5c99e626a5b70b31586215b24ed3c44625403018543379b7128e254f2ee1e2854e98f824862c2296c175fa8226838d15ce375e82c6d3c96595193a0dbd778018c070a5e48206d34d385e937eb686709f66d4732d4be636373579fec5829688bbae72e97aa5d63b710f7ef1894699d64d1e821bb9bc5c22e4aec90fff4e04d463054ba51d6d70139956286765f20c681ed30f42d261b317241893faaf5c299c5568cb0f15e5fb63b0be4270d47f0b2daf0b474e211947684ab3219a23a63fad7ad8d31c39c2b300b919355aefcb7a7e93d10d40208a2d2";//put text to decrypt in here
        System.out.println("Decryption Result : "+performDecrypt(strToDecrypt));
    }
}