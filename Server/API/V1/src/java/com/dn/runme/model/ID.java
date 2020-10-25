/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.runme.model;

import static com.dn.runme.model.Crypt.MD5;

/**
 *
 * @author Duy
 */
public class ID {
   
    public static String Gen(String s){
        return MD5(java.util.UUID.randomUUID().toString()+s+Time.Unix.Now());
    }
}
