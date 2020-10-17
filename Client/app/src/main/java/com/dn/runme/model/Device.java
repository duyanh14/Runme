package com.dn.runme.model;

import android.view.KeyEvent;

import com.dn.runme.MainActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Device {
    public static class Server {

        public static String IP = "10.1.10.174";
        public static int Port = 6199;

        public DataInputStream dis;
        public DataOutputStream dos;

        public Thread TConnect;

        public Server() {

        }

        ///////////////////////////

        public boolean Connect() throws Exception {
            try {

                SocketAddress sockaddr = new InetSocketAddress(IP, Port);
                Socket socket = new Socket();
                socket.connect(sockaddr, 10000);

                final DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                SocketCommand skc = new SocketCommand();
                skc.Command.add("Authentication");
                skc.Parameter.put("Token", Account.Token);
                dos.writeUTF(SocketCommand.Parse(skc));

                skc = SocketCommand.Parse(dis.readUTF());

                switch ((String) skc.Command.get(0)) {
                    case "Authentication":
                        switch ((int) skc.Parameter.get("Status")) {
                            case 0:
                                this.dos = dos;
                                this.dis = dis;

                                final Server server = this;

                                TConnect = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (!Thread.currentThread().isInterrupted()) {
                                            try {
                                                SocketCommand skc = SocketCommand.Parse(server.dis.readUTF());
                                                Command_Execute(skc);
                                            } catch (NullPointerException np) {
                                                break;
                                            } catch (SocketException se) {
                                                break;
                                            } catch (EOFException eof) {
                                                break;
                                            } catch (StreamCorruptedException ex) {
                                                break;
                                            } catch (IllegalArgumentException iae) {
                                                break;
                                            } catch(InterruptedException e) {
                                               break;
                                            } catch (Exception ex) {
                                            }
                                        }
                                        System.out.println("dis");
                                    }
                                });
                                TConnect.start();


                                return true;
                            default:
                                throw new Exception("Authentication failed");
                        }
                }

            } catch (ConnectException exxx) {
                throw new Exception("Disconnected");
            } catch (SocketTimeoutException exx) {
                throw new Exception("Timeout");
            } catch (Exception ex) {
                if ("Authentication failed".equals(ex.getMessage())) {
                    throw ex;
                }
                ex.printStackTrace();
            }
            throw new Exception("Unknown error");
        }

        public void Command_Send(SocketCommand skc) throws Exception {
            dos.writeUTF(skc.Parse());
        }

        public void Command_Execute(final SocketCommand skc) {
            switch (skc.Command.get(0)) {
                case "Script":
                    switch (skc.Command.get(1)) {
                        case "Output":
                            MainActivity.webView.post(new Runnable() {
                                @Override
                                public void run() {
                                    MainActivity.webView.loadUrl("javascript:page_dashboard_view_script_editor_terminal_append('"+skc.Parameter.get("Content")+"')");
                                }
                            });
                            break;
                    }
                    break;
            }
        }

        public void Disconnect() {
            try {
                this.dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                this.dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
