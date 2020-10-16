/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.dto.device;

import com.dn.amonir.model.Base64Coder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Duy
 */
public class Contacts implements Serializable {

    public int ID = 0;

    public String Name = null;

    public ArrayList<Number> Number = new ArrayList<>();

    public ArrayList<Email> Email = new ArrayList<>();

    public By By = new By();

    public static class By implements Serializable {

        public int Type;
        public String Value;
    }

    public static class Number implements Serializable {

        public int Type;
        public String Value;
    }

    public static class Email implements Serializable {

        public int Type;
        public String Value;
    }
    
    public String Write() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        oos.close();
        return new String(Base64Coder.encode(baos.toByteArray()));
    }
    
    public static Contacts Read(final String pp) throws Exception {
        final byte[] data = Base64Coder.decode(pp);
        final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        final Object o = ois.readObject();
        ois.close();
        return (Contacts)o;
    }
    
}
