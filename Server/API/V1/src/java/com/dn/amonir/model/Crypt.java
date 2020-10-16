// 
// Decompiled by Procyon v0.5.36
// 

package com.dn.amonir.model;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Crypt
{
    public static String MD5(final String md5) {
        try {
            final MessageDigest md6 = MessageDigest.getInstance("MD5");
            final byte[] array = md6.digest(md5.getBytes());
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }
}
