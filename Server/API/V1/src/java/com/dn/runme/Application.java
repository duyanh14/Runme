/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.runme;

import com.dn.runme.model.Account;
import com.dn.runme.model.Device;
import com.dn.runme.model.SocketCommand;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 *
 * @author Duy
 */
public class Application {

    public static void main(String[] args) throws IOException {
        Server.Initialization();
    }

}

class Server {

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

                        com.dn.runme.model.SocketCommand skc = com.dn.runme.model.SocketCommand.Parse(dis.readUTF());

                        if ("Authentication".equals(skc.Command.get(0))) {
                            String Token = (String) skc.Parameter.get("Token");

                            com.dn.runme.dto.Account account = Account.Authentication_Token(Token);

                            if (account != null) {
                                skc = new com.dn.runme.model.SocketCommand();
                                skc.Command.add("Authentication");
                                skc.Parameter.put("Status", 0);
                                dos.writeUTF(skc.Parse());

                                Device device = new Device(account);
                                device.Connect(s, dis, dos);
                                
                                return;
                            }

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }

                    try {
                        com.dn.runme.model.SocketCommand skc = new com.dn.runme.model.SocketCommand ();
                        skc.Command.add("Authentication");
                        skc.Parameter.put("Status", 1);
                        dos.writeUTF(skc.Parse());
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
