/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.runme.model;

import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author admin
 */
public class Crypt {

    public static String MD5(String s) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(s.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static class RC4 {

        private int[] S = new int[256];
        private final int keylen;

        public RC4(String string) {

            byte[] key = string.getBytes();

            if (key.length < 1 || key.length > 256) {
                throw new IllegalArgumentException("key must be between 1 and 256 bytes");
            } else {
                keylen = key.length;
                for (int i = 0; i < 256; i++) {
                    S[i] = i;
                }

                int j = 0;

                for (int i = 0; i < 256; i++) {
                    j = (j + S[i] + key[i % keylen]) % 256;
                    int temp = S[i];
                    S[i] = S[j];
                    S[j] = temp;
                }
            }
        }

        public String Encrypt(final String message) {
            byte[] plaintext = new byte[0];
            try {
                plaintext = message.getBytes("UTF-8");
            } catch (Exception e) {
            }

            byte[] ciphertext = toBytes(transform(toInts(plaintext)));
            return new String(Hex.encodeHex(ciphertext));

        }

        private int[] transform(final int[] input) {
            final int[] output = new int[input.length];

            final int[] S = new int[this.S.length];
            System.arraycopy(this.S, 0, S, 0, S.length);

            int i = 0, j = 0, k, t;
            for (int counter = 0; counter < input.length; counter++) {
                i = (i + 1) & 0xFF;
                j = (j + S[i]) & 0xFF;
                S[i] ^= S[j];
                S[j] ^= S[i];
                S[i] ^= S[j];
                t = (S[i] + S[j]) & 0xFF;
                k = S[t];
                output[counter] = input[counter] ^ k;
            }

            return output;
        }

        private int[] toInts(byte[] bytes) {
            int[] output = new int[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                output[i] = bytes[i];
            }
            return output;
        }

        private byte[] toBytes(int[] ints) {
            byte[] output = new byte[ints.length];
            for (int i = 0; i < ints.length; i++) {
                output[i] = (byte) ints[i];
            }
            return output;
        }

        public String Decrypt(final String ciphertext) {
            try {
                int[] cipherints = toInts(Hex.decodeHex(ciphertext.toCharArray()));
                for (int i = 0; i < cipherints.length; i++) {
                    cipherints[i] = cipherints[i] & 0xff;
                }
                byte[] plaintext = toBytes(transform(cipherints));
                return new String(plaintext, Charset.forName("UTF-8"));
            } catch (DecoderException e) {
            }
            return null;
        }

        public static String Key() {
            return "4060219D25445F555F595F4E5F475F555F595F255F4E2D592D525F565F4F5F4C5F545F47252D2140402B2B2B2B2B2B2B5E5E3C33686968692C74696E686C616D6A6861";
        }

    }

}
