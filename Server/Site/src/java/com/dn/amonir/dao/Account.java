/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.dao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author pc
 */
public class Account {

    public int ID;
    public String Name;
    public String Email;
    public String Password;
    public String PhoneNumber;
    public int Balance;
    public int BalanceFrozen;
    public int Disable;
    public int Permission;
    public String Token;
    public long CreateTime;

    public void Hehe(String Name) {
        this.Name = Name;
    }

    public static void main(String[] args) throws IOException {
        Path path = new File("E:\\sdsa.mkv").toPath();

        try {
Files.setAttribute(path, "user:encryption used", "x`");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(Files.getAttribute(path, "encryption used"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
