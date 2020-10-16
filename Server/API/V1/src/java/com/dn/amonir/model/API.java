/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Duy
 */
public class API {

    public static class Config {

        public static String DataPath = "G:\\";
    }

    public static class Spring {

        public static void uploadFile(@RequestParam("file") MultipartFile file, String path, String name) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    File serverFile = new File(Config.DataPath + "\\" + path);
                    if (!serverFile.exists()) {
                        serverFile.mkdirs();
                    }
                    serverFile = new File(Config.DataPath + "\\" + path + "\\" + name);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
