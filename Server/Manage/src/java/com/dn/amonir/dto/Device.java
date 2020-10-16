/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.dto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import org.json.JSONArray;

/**
 *
 * @author Duy
 */
public class Device {

    public boolean Connected;

    public int Session;

    public Socket s;
    public DataInputStream dis;
    public DataOutputStream dos;

    public int ID;

    public String UniqueID = null;

    public String Manufacturer = null;

    public String Model = null;

    public int SDK;

    public String Name = null;

    public JSONArray SIM = new JSONArray();

    public String Token = null;

    public Wifi Wifi = new Wifi();

    public GPS GPS = new GPS();

    public Battery Battery = new Battery();

    public boolean AirplaneMode;

    public Time Time = new Time();

    public Configuration Configuration = new Configuration();

    public Statics Statics = new Statics();

    public Thread Connect = new Thread();

    public class Wifi {

        public boolean Connected;
        public String Name;
    }

    public class GPS {

        public boolean Enable;
        public int Mode;
    }

    public class Battery {

        public boolean Charging;
        public int Percent;
    }

    public class Time {

        public long Now;

        public Server Server = new Server();

        public class Server {

            public long Active;

            public long Create;

        }
    }

    public class Configuration {

        public boolean SendFileOverMobileNetwork;

        public Listen Listen = new Listen();

        public class Listen {

            public Location Location = new Location();

            public class Location {

                public int UpdateInterval;
                public int FastestInterval;
                public int SmallestDisplacement;

            }
        }
    }

    public class Statics {

        public Call Call = new Call();
        public Messages Messages = new Messages();

        public class Call {

            public int Unread;

        }

        public class Messages {

            public SMS SMS = new SMS();

            public class SMS {

                public int Unread;
            }
        }

    }
}
