/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.runme.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author Duy
 */
public class SocketCommand implements Serializable {

    public ArrayList<String> Command = new ArrayList<String>();
    
    public LinkedHashMap<String, Object> Parameter = new LinkedHashMap<String, Object>();
    
    public static String Parse(SocketCommand object) throws Exception {
        return ObjectUtils.Write(object);
    }

    public static SocketCommand Parse(String pp) throws Exception {
        SocketCommand o = (SocketCommand) ObjectUtils.Read(pp);;
        return o;
    }
    
    public String Parse() throws Exception{
        return SocketCommand.Parse(this);
    }

}
