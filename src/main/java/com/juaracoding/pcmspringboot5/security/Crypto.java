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
        String strToEncrypt = "password";//put text to encrypt in here
        System.out.println("Encryption Result : "+performEncrypt(strToEncrypt));

//        jdbc:sqlserver://java-be-1:3377;databaseName=BEB24;schema=dbproject;trustServerCertificate=true
//        String strToDecrypt = "09ce6bf5076fd9ad1c2d8b7f745884726ceb17ce145e01772186f25d9632ce49";//put text to decrypt in here
//        String strToDecrypt = "121fb7ba5db0dacc7e7b24116df3d7ff";//put text to decrypt in here
//        System.out.println("Decryption Result : "+performDecrypt(strToDecrypt));
    }
}