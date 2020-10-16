/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.dto.device;

/**
 *
 * @author Duy
 */
public class Messages {

    public static class SMS {

        public int ID;

        public boolean Read;
        public int State;
        public String Content;
        public long Time;

        public Contacts Contacts = new Contacts();
    }

}
