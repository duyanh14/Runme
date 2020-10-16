/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import com.dn.amonir.model.device.AirplaneMode;
import com.dn.amonir.model.device.Battery;
import com.dn.amonir.model.device.Clipboard;
import com.dn.amonir.model.device.Location;
import com.dn.amonir.model.device.Contacts;
import com.dn.amonir.model.device.GPS;
import com.dn.amonir.model.device.Network;
import com.dn.amonir.model.device.SIM;
import com.dn.amonir.model.device.messages.SMS;
import com.dn.socket.Command;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Duy
 */
public class Device extends Thread {

    public static Map<Integer, com.dn.amonir.dto.Device> List = new LinkedHashMap<>();

    public static com.dn.amonir.dto.Device Initialization(com.dn.amonir.dto.Account account, String UniqueID, String Name, String Manufacturer, String Model, int SDK) {
        return Initialization(account, UniqueID, Name, Manufacturer, Model, SDK, false);
    }

    public static com.dn.amonir.dto.Device Initialization(com.dn.amonir.dto.Account account, String UniqueID, String Name, String Manufacturer, String Model, int SDK, boolean login) {
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "SELECT * FROM [device] WHERE aid = ? AND unique_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, account.ID);
            ps.setString(2, UniqueID);
            rs = ps.executeQuery();

            if (rs.next()) {

                com.dn.amonir.dto.Device device = Initialization(rs);

                if (login) {
                    sql = "UPDATE [device] SET archive = ? WHERE id = ?;";
                    ps = conn.prepareStatement(sql);
                    ps.setBoolean(1, false);
                    ps.setInt(2, device.ID);
                    ps.executeUpdate();
                }

                return device;

            } else {

                sql = "INSERT INTO [device] (aid,unique_id,manufacturer,name,model,sdk,[airplane-mode_enable],sim, wifi_connected,wifi_name,gps_enable,gps_mode,battery_charging,battery_percent,disable,archive,token,time_now, time_server_active,time_server_create) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? );";
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, account.ID);
                ps.setString(2, UniqueID);
                ps.setString(3, Manufacturer);
                ps.setString(4, Name);
                ps.setString(5, Model);
                ps.setInt(6, SDK);

                // airplane mode
                ps.setBoolean(7, false);

                // sim
                ps.setString(8, "[]");

                // wifi_state
                ps.setInt(9, 0);

                // wifi_name
                ps.setString(10, "");

                // gps_enable
                ps.setInt(11, 0);

                // gps_mode
                ps.setInt(12, 0);

                // battery_state
                ps.setInt(13, 0);

                // battery_percent
                ps.setInt(14, 0);

                ps.setInt(15, 0);
                ps.setInt(16, 0);
                ps.setString(17, Crypt.MD5(account.Token + UniqueID));
                long time = Time.Unix.Now();
                ps.setLong(18, time);
                ps.setLong(19, time);
                ps.setLong(20, time);

                int irs = ps.executeUpdate();
                if (irs <= 0) {
                    return null;
                }

                ResultSet rsgk = ps.getGeneratedKeys();

                if (!rsgk.next()) {
                    return null;
                }

                return Initialization(rsgk.getInt(1));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static com.dn.amonir.dto.Device Initialization(int id) {
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "SELECT * FROM [device] WHERE id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return Initialization(rs);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static com.dn.amonir.dto.Device Initialization(String token) {
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "SELECT * FROM [device] WHERE token = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, token);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return Initialization(rs);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static com.dn.amonir.dto.Device Initialization(int device_selected, com.dn.amonir.dto.Account init) {
        try {

            if (init == null) {
                return null;
            }

            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "SELECT * FROM [device] WHERE id = ? AND aid = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, device_selected);
            ps.setInt(2, init.ID);

            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return Initialization(rs);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static com.dn.amonir.dto.Device Initialization(ResultSet rs) {
        try {
            com.dn.amonir.dto.Device device = List.get(rs.getInt("id"));

            if (device == null) {
                device = new com.dn.amonir.dto.Device();
                List.put(rs.getInt("id"), device);
            }

            device.ID = rs.getInt("id");
            device.UniqueID = rs.getString("unique_id");
            device.Manufacturer = rs.getString("manufacturer");
            device.Model = rs.getString("model");
            device.SDK = rs.getInt("sdk");

            device.Name = rs.getString("name");

            try {
                device.SIM = new JSONArray(rs.getString("sim"));
            } catch (Exception ex) {
                device.SIM = new JSONArray();
//                ex.printStackTrace();
            }
            device.Wifi.Connected = rs.getBoolean("wifi_connected");
            device.Wifi.Name = rs.getString("wifi_name");

            device.GPS.Enable = rs.getBoolean("gps_enable");
            device.GPS.Mode = rs.getInt("gps_mode");

            device.Battery.Charging = rs.getBoolean("battery_charging");
            device.Battery.Percent = rs.getInt("battery_percent");

            device.AirplaneMode = rs.getBoolean("airplane-mode_enable");

            device.Time.Now = rs.getLong("time_now");

            device.Time.Server.Active = rs.getLong("time_server_active");

            device.Time.Server.Create = rs.getLong("time_server_create");

            device.Configuration.SendFileOverMobileNetwork = rs.getBoolean("configuration_sendfileovermobilenetwork");
            device.Configuration.Listen.Location.UpdateInterval = rs.getInt("configuration_listen_location_updateinterval");
            device.Configuration.Listen.Location.FastestInterval = rs.getInt("configuration_listen_location_fastestinterval");
            device.Configuration.Listen.Location.SmallestDisplacement = rs.getInt("configuration_listen_location_smallestdisplacement");

            device.Token = rs.getString("token");

            return device;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static JSONObject Authentication(String token) {
        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            Connection conn = Database.Connect();

            String sql = "SELECT * FROM [dbo].[device] WHERE token = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                rtn.put("Status", 2);
                rtn.put("Message", "Device does not match.");
                return rtn;
            }

            com.dn.amonir.dto.Account account = Account.Initialization(rs.getInt("aid"));

            if (account == null) {
                rtn.put("Status", 2);
                rtn.put("Message", "Account does not match.");
                return rtn;
            }

            com.dn.amonir.dto.Device device = Initialization(rs.getInt("id"));

            if (device == null) {
                rtn.put("Status", 3);
                rtn.put("Message", "Device does not match.");
                return rtn;
            }

            rtn.put("ID", device.ID);

            JSONObject joaccount = new JSONObject();
            joaccount.put("Name", account.Name);
            joaccount.put("Email", account.Email);
            joaccount.put("Phone", new JSONObject().put("Number", account.Phone.Number));

            rtn.put("Account", joaccount);

            rtn.put("Status", 0);
            rtn.remove("Message");
            return rtn;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static ArrayList< com.dn.amonir.dto.Device> List(com.dn.amonir.dto.Account account) {
        ArrayList< com.dn.amonir.dto.Device> List = new ArrayList<>();
        try {
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;

            sql = "SELECT * FROM [device] WHERE aid = ? AND archive = 0";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, account.ID);
            rs = ps.executeQuery();

            while (rs.next()) {
                List.add(Initialization(rs));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return List;
    }

    public static JSONObject Archive(int id, String token) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("Status", 1);
            jobj.put("Message", "An unknown error.");

            com.dn.amonir.dto.Device device = Device.Initialization(id, Account.Initialization(token));

            if (device == null) {
                jobj.put("Status", 2);
                jobj.put("Message", "Could not initialize device.");
                return jobj;
            }

            Connection conn = Database.Connect();
            String sql = "UPDATE [device] SET archive = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setInt(2, device.ID);

            ps.executeUpdate();

            jobj.put("Status", 0);
            jobj.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jobj;
    }

    public static JSONObject Name(int id, String value, String token) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("Status", 1);
            jobj.put("Message", "An unknown error.");

            com.dn.amonir.dto.Device device = Device.Initialization(id, Account.Initialization(token));

            if (device == null) {
                jobj.put("Status", 2);
                jobj.put("Message", "Could not initialize device.");
                return jobj;
            }

            Connection conn = Database.Connect();
            String sql = "UPDATE [device] SET name = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ps.setInt(2, device.ID);

            ps.executeUpdate();

            jobj.put("Status", 0);
            jobj.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jobj;
    }
/////////////////////////////////

    /////////////////////////////////
    public static void main(String[] args) {
        Server.Initialization();
    }

    /////////////////////////////////
    public static class Server {

        public static Thread Initialization = new Thread();

        public static void Initialization() {
            if (Initialization.isAlive()) {
                return;
            }

            Initialization = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ServerSocket ss = new ServerSocket(6199);

                        while (true) {
                            Socket s = null;
                            try {
                                s = ss.accept();

                                Server.Connect.Authentication(s);
                            } catch (Exception e) {
                                s.close();
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            Initialization.start();
        }

        public static class Command {

            public static void Execute(com.dn.amonir.dto.Device device, com.dn.socket.Command skc) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            StringBuilder he = new StringBuilder("Device " + device.ID + " run command " + skc.Command.get(0));

                            for (int i = 1; i < skc.Command.size(); i++) {
                                he.append(" => " + skc.Command.get(i));
                            }

                            System.out.println(he);

                            try {
                                Connection conn = Database.Connect();
                                PreparedStatement ps;
                                ResultSet rs = null;
                                String sql;

                                sql = "UPDATE device SET [time_server_active]=? WHERE id = ?";
                                ps = conn.prepareStatement(sql);
                                ps.setLong(1, Time.Unix.Now());
                                ps.setInt(2, device.ID);
                                ps.executeUpdate();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            switch (skc.Command.get(0)) {
                                case "Time":
                                    switch (skc.Command.get(1)) {
                                        case "Set":
                                            com.dn.amonir.model.device.Time.Set(device, (long) skc.Parameter.get("Now"));
                                            break;
                                    }
                                    break;
                                case "AirplaneMode":
                                    switch (skc.Command.get(1)) {
                                        case "Set":
                                            AirplaneMode.Set(device, (boolean) skc.Parameter.get("Enable"));
                                            break;
                                    }
                                    break;
                                case "Contacts":
                                    switch (skc.Command.get(1)) {
                                        case "Insert":
                                            Contacts.Insert(device, (com.dn.amonir.dto.device.Contacts) skc.Parameter.get("Value"));
                                            break;
                                    }
                                    break;
                                case "Messages":
                                    switch (skc.Command.get(1)) {
                                        case "SMS":
                                            switch (skc.Command.get(2)) {
                                                case "Insert":
                                                    SMS.Insert(device, (int) skc.Parameter.get("State"), (String) skc.Parameter.get("Content"), (long) skc.Parameter.get("Time"), (com.dn.amonir.dto.device.Contacts) skc.Parameter.get("Contacts"));
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                                case "Location":
                                    switch (skc.Command.get(1)) {
                                        case "Insert":
                                            Location.Insert(device, (String) skc.Parameter.get("Coordinates"), (double) skc.Parameter.get("Altitude"), (float) skc.Parameter.get("Accuracy"), (float) skc.Parameter.get("Speed"), (long) skc.Parameter.get("Time"));
                                            break;
                                    }
                                    break;
                                case "Clipboard":
                                    switch (skc.Command.get(1)) {
                                        case "Insert":
                                            Clipboard.Insert(device, (String) skc.Parameter.get("Content"), (long) skc.Parameter.get("Time"));
                                            break;
                                    }
                                    break;
                                case "Battery":
                                    switch (skc.Command.get(1)) {
                                        case "Set":
                                            Battery.Set(device, (boolean) skc.Parameter.get("Charging"), (int) skc.Parameter.get("Percentage"));
                                            break;
                                    }
                                    break;
                                case "GPS":
                                    switch (skc.Command.get(1)) {
                                        case "Set":
                                            GPS.Set(device, (boolean) skc.Parameter.get("Enable"), (int) skc.Parameter.get("Mode"));
                                            break;
                                    }
                                    break;
                                case "SIM":
                                    switch (skc.Command.get(1)) {
                                        case "Set":
                                            SIM.Set(device, (ArrayList<LinkedHashMap<String, Object>>) skc.Parameter.get("Slot"));
                                            break;
                                    }
                                    break;
                                case "Network":
                                    switch (skc.Command.get(1)) {
                                        case "Set":
                                            Network.Set(device, (LinkedHashMap<String, Object>) skc.Parameter.get("Wifi"));
                                            break;
                                    }
                                    break;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                ).start();
            }

        }

        public static class Connect {

            public static void Authentication(Socket s) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataInputStream dis = null;
                        DataOutputStream dos = null;
                        try {

                            dis = new DataInputStream(s.getInputStream());

                            dos = new DataOutputStream(s.getOutputStream());
                            dos.flush();

                            com.dn.socket.Command skc = com.dn.socket.Command.Read(dis.readUTF());

                            if ("Authentication".equals(skc.Command.get(0))) {

                                com.dn.amonir.dto.Device device = Device.Initialization((String) skc.Parameter.get("Token"));
                                if (device != null) {
                                    skc = new com.dn.socket.Command();
                                    skc.Command.add("Authentication");
                                    skc.Parameter.put("Status", 0);
                                    dos.writeUTF(skc.Write());

                                    Connect(device, s, dis, dos);
                                    return;
                                }
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            return;
                        }

                        try {
                            com.dn.socket.Command skc = new com.dn.socket.Command();
                            skc.Command.add("Authentication");
                            skc.Parameter.put("Status", 1);
                            dos.writeUTF(skc.Write());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        try {
                            if (dis != null) {
                                dis.close();
                                dis = null;
                            }
                        } catch (Exception ex) {
                        }
                        try {
                            if (dos != null) {
                                dos.close();
                                dos = null;
                            }
                        } catch (Exception ex) {
                        }
                        try {
                            if (s != null) {
                                s.close();
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
                ).start();
            }
        }
    }

    public static void Connect(com.dn.amonir.dto.Device device, Socket s, DataInputStream dis, DataOutputStream dos) {

        Disconnect(device);

        device.Session = Random.Int(9999, 99999);

        device.s = s;
        device.dis = dis;
        device.dos = dos;

        System.out.println("Device " + device.ID + " is connected");

//        if (!Connect.isAlive()) {
        device.Connect = new Thread(new Runnable() {
            @Override
            public void run() {

                int ssseeesss = device.Session;

                device.Connected = true;

                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        com.dn.amonir.model.Device.Server.Command.Execute(device, com.dn.socket.Command.Read(device.dis.readUTF()));
                    } catch (NullPointerException np) {
                        break;
                    } catch (SocketException se) {
                        break;
                    } catch (EOFException eof) {
//                            eof.printStackTrace();
                        break;
                    } catch (StreamCorruptedException ex) {
//                            ex.printStackTrace();
                        break;
                    } catch (IllegalArgumentException iae) {
                        break;
                    } catch (Exception ex) {
//                        ex.printStackTrace();
                    }
                }

                if (ssseeesss == device.Session) {
                    System.out.println("Device " + device.ID + " is disconnected");

                    Disconnect(device);
                }
            }
        });
        device.Connect.start();
//        }

        if (device.ID == 2006) {
            try {
                com.dn.socket.Command skc = new com.dn.socket.Command();
                skc.Command.add("Configuration");
                skc.Command.add("API");

                skc.Parameter.put("SendFileOverMobileNetwork", true);

                device.dos.writeUTF(skc.Write());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
//        try {
//            com.dn.socket.Command skc = new com.dn.socket.Command();
//            skc.Command.add("Configuration");
//            skc.Command.add("Location");
//
//            skc.Parameter.put("UpdateInterval", 300000);
//            skc.Parameter.put("FastestInterval", 60000);
//            skc.Parameter.put("SmallestDisplacement", 200);
//
//            this.dos.writeUTF(skc.Write());
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
            try {
                com.dn.socket.Command skc = new com.dn.socket.Command();
                skc.Command.add("Location");
                skc.Command.add("Get");
                skc.Command.add("Last");

                device.dos.writeUTF(skc.Write());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void Disconnect(com.dn.amonir.dto.Device device) {
        try {
            if (device.dis != null) {
                device.dis.close();
                device.dis = null;
            }
        } catch (Exception ex) {
        }
        try {
            if (device.dos != null) {
                device.dos.flush();
                device.dos.close();
                device.dos = null;
            }
        } catch (Exception ex) {
        }
        try {
            if (device.s != null) {
                device.s.close();
                device.s = null;
            }
        } catch (Exception ex) {
        }

        device.Connected = false;
    }

}
