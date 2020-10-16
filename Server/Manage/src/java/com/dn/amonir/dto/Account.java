/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author pc
 */
public class Account {

    public int ID;

    public String Name;

    public String Email;

    public String Password;

    public int Balance;

    public boolean Disable = false;

    public int Permission;

    public String Token;

    public Phone Phone = new Phone();

    public class Phone {

        public String Number;
    }

    public Create Create = new Create();

    public static class Create {

        public long Time;
    }

    public Device Device = new Device();

    public class Device {

        public int Count;

        public ArrayList<com.dn.amonir.dto.Device> List = new ArrayList();

        public com.dn.amonir.dto.Device Selected = null;

    }

}
