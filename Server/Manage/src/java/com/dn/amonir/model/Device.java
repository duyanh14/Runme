/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Device {

    public static JSONObject Selected(int id, HttpServletResponse response) {
        try {

            JSONObject rs = new JSONObject();

            javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("Device_Selected", String.valueOf(id));
            cookie.setPath("/");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);

            rs.put("Status", 0);

            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject Archive(int id, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("ID", id);
            parameters.put("Token", com.dn.amonir.model.Cookie.Get.By.Key(request, "Account").getValue());

            JSONObject rs = new JSONObject(API.Request.Make("Device", "Archive", parameters));

            if (rs.getInt("Status") == 0) {

                javax.servlet.http.Cookie account_token = com.dn.amonir.model.Cookie.Get.By.Key(request, "Device_Selected");
                if (account_token != null) {
                    if (Integer.valueOf(account_token.getValue()) == id) {
                        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("Device_Selected", "0");
                        cookie.setPath("/");
                        cookie.setMaxAge(-1);
                        response.addCookie(cookie);
                    }
                }

            }

            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject Name(int id, String value, HttpServletRequest request) {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("ID", id);
            parameters.put("Value", value);
            parameters.put("Token", com.dn.amonir.model.Cookie.Get.By.Key(request, "Account").getValue());

            JSONObject rs = new JSONObject(API.Request.Make("Device", "Name", parameters));


            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
