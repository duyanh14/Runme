/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.runme.model;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
/**
 *
 * @author Duy
 */
public class Database {
    
    public static String Host = "puinit.com";
    public static String User = "cassandra";
    public static String Password = "cassandra";
    public static String Keyspace = "Runme";
    public static Session Sess = null;

    public static Session Connect() {
        if (Sess != null) {
            return Sess;
        }
        try {
              Cluster cluster = Cluster.builder()
                .addContactPoints(Host)
                .withCredentials("cassandra", "cassandra")
                .build();

                Sess = cluster.connect(Keyspace);
        
            return Sess;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void Close() {
        if (Sess != null) {
            try {
                Sess.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Sess = null;
        }
    }

}
