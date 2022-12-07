package hr.fer.oprpp1.hw05;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class Util {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] a = hextobyte("");
        System.out.println(a[0]);
//        System.out.println(a[1]);
//        System.out.println(a[2]);

        String keyText = "e52217e3ee213ef1ffdee3a192e2ac7e";
        String ivText = "000102030405060708090a0b0c0d0e0f";
        SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        boolean encrypt = true;
        cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
    }

    public static byte[] hextobyte(String keyText) {
        if (keyText == null) {
            throw new IllegalArgumentException("Key text cannot be null");
        }
        if (keyText.length() % 2 != 0) {
            throw new IllegalArgumentException("keyText parameter must be of even length, but is " + keyText.length());
        }
        keyText = keyText.toLowerCase();
        byte[] bytearray = new byte[keyText.length() / 2];
        int[] offset = new int[] {0, 1};
        for (int i = 0; i < keyText.length(); i += 2) {
            for (int o : offset) {
                char c = keyText.charAt(i + o);
                if ((int) c >= 48 && (int) c <= 57 || (int) c >= 97 && (int) c <= 102) {
                    bytearray[i / 2] += (byte) ((int) c - (c >= 97 ? 87 : 48)) * (o == 0 ? 16 : 1);
                } else {
                    throw new IllegalArgumentException("Key text must be in hexadecimal format");
                }
            }
        }
        return bytearray;
    }

    public static String bytetohex(byte[] bytearray) {
        if (bytearray == null) {
            throw new IllegalArgumentException("Byte array cannot be null");
        }
        if (bytearray.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytearray) {
            sb.append()
        }
    }
}
