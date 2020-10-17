/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.runme.model;

import com.dn.runme.dto.Account;
import com.dn.socket.Command;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Duy
 */
public class Device {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    Thread TConnect;

    Account Account;

    public Device(Account account) {
        this.Account = account;
    }

    private static void Script_Execute_Input(BufferedWriter p_stdin, String command) {
        try {
            // single execution
            p_stdin.write(command);
            p_stdin.newLine();
            p_stdin.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void Connect(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;

        Device device = this;

        TConnect = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        SocketCommand skc = SocketCommand.Parse(device.dis.readUTF());

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
                    } catch (Exception ex) {
                    }
                }
            }
        });
        TConnect.start();

    }

    public void Command_Send(SocketCommand skc) throws Exception {
        dos.writeUTF(skc.Parse());
    }

    BufferedWriter Script_Execute_STDIN;

    public void Command_Execute(SocketCommand skc) {
        switch (skc.Command.get(0)) {
            case "Script":
                switch (skc.Command.get(1)) {
                    case "Execute":
                        try {
                        String id = (String) skc.Parameter.get("ID");

                        com.dn.runme.dto.Script script = Script.Get_ID(id);
                        if (!script.Account.equals(Account.ID)) {
                            return;
                        }

                        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "cd \"C:\\Users\\Duy\\Desktop\" && dir");
                        final Process p = builder.start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(600000);
                                    if (p.isAlive()) {
                                        p.destroy();
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }).start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                long i = 0;
                                String line;
                                try {
                                    while ((line = reader.readLine()) != null) {
                                        if (i == 4) {
                                            SocketCommand skc = new com.dn.runme.model.SocketCommand();
                                            skc.Command.add("Script");
                                            skc.Command.add("Output");
                                            skc.Parameter.put("Content", line.substring(line.indexOf(">") + ">".length()));
                                            dos.writeUTF(skc.Parse());
                                        } else {
                                            i++;
                                        }
                                    }
                                } catch (IOException ex) {
                                } catch (Exception ex) {
                                    Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }).start();

                        Script_Execute_STDIN = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

                        String file = "F:\\Runme\\Script\\" + Account.ID + id + Time.Unix.Now() + ".py";
                        FileUtils.writeStringToFile(new File(file), script.Content, "UTF-8", false);

                        Script_Execute_Input(Script_Execute_STDIN, "python " + file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                    case "Input":
                        try {
                        Script_Execute_Input(Script_Execute_STDIN, (String) skc.Parameter.get("Content"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                break;
        }
    }

}
