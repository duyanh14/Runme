/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Account {

    public static JSONObject Login(String email, String password, HttpServletResponse response) {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Email", email);
            parameters.put("Password", password);

            JSONObject rs = new JSONObject(API.Request.Make("Account", "Login", parameters));

            if (rs.getInt("Status") == 0) {
                Cookie cookie = new Cookie("Account", rs.getString("Token"));
                cookie.setPath("/");
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
            }

            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject Register(String name, String email, String phone_number, String password, HttpServletResponse response) {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Name", name);
            parameters.put("Email", email);
            parameters.put("PhoneNumber", phone_number);
            parameters.put("Password", password);

            JSONObject rs = new JSONObject(API.Request.Make("Account", "Register", parameters));

            if (rs.getInt("Status") == 0) {
                Cookie cookie = new Cookie("Account", rs.getString("Token"));
                cookie.setPath("/");
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
            }

            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String isLogin(HttpServletRequest request) {
        Cookie account_token = com.dn.amonir.model.Cookie.Get.By.Key(request, "Account");
        if (account_token != null) {
            return account_token.getValue();
        }
        return null;
    }

    public static com.dn.amonir.dto.Account Authentication(HttpServletRequest request) {

        String Token = isLogin(request);

        if (Token == null) {
            return null;
        }

        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Token", Token);

            int dvs = 0;
            try {
                dvs = Integer.valueOf(com.dn.amonir.model.Cookie.Get.By.Key(request, "Device_Selected").getValue());
            } catch (Exception ex) {
            }
            parameters.put("Device_Selected", dvs);

            ArrayList<String> actions = new ArrayList<>();
            actions.add("Authentication");

            JSONObject rs = new JSONObject(API.Request.Make("Account", actions, parameters));
            if (rs.getInt("Status") == 0) {
                JSONObject rs_account = rs.getJSONObject("Information");

                com.dn.amonir.dto.Account ac = new com.dn.amonir.dto.Account();

                ac.ID = rs_account.getInt("ID");
                ac.Name = rs_account.getString("Name");
                ac.Email = rs_account.getString("Email");
                ac.Phone.Number = rs_account.getJSONObject("Phone").getString("Number");
                ac.Disable = rs_account.getBoolean("Disable");

                ac.Device.Count = rs.getJSONObject("Device").getInt("Count");

                JSONArray rs_account_device_list = rs.getJSONObject("Device").getJSONArray("List");

                for (int i = 0; i < rs_account_device_list.length(); i++) {
                    JSONObject rs_account_device_list_object = rs_account_device_list.getJSONObject(i);

                    com.dn.amonir.dto.Device e = new com.dn.amonir.dto.Device();
                    e.ID = rs_account_device_list_object.getInt("ID");
                    e.Name = rs_account_device_list_object.getString("Name");

                    e.Time.Now = rs_account_device_list_object.getJSONObject("Time").getLong("Now");

                    e.Time.Server.Active = rs_account_device_list_object.getJSONObject("Time").getJSONObject("Server").getLong("Active");

                    e.Time.Server.Create = rs_account_device_list_object.getJSONObject("Time").getJSONObject("Server").getLong("Create");
                    ac.Device.List.add(e);

                }

                if (!rs.getJSONObject("Device").isNull("Selected")) {
                    JSONObject device_selected = rs.getJSONObject("Device").getJSONObject("Selected");

                    ac.Device.Selected = new com.dn.amonir.dto.Device();

                    ac.Device.Selected.ID = device_selected.getInt("ID");

                    ac.Device.Selected.Name = device_selected.getString("Name");

                    ac.Device.Selected.Manufacturer = device_selected.getString("Manufacturer");
                    ac.Device.Selected.Model = device_selected.getString("Model");
                    ac.Device.Selected.SDK = device_selected.getInt("SDK");

                    ac.Device.Selected.Battery.Charging = device_selected.getJSONObject("Battery").getBoolean("Charging");
                    ac.Device.Selected.Battery.Percent = device_selected.getJSONObject("Battery").getInt("Percent");

                    ac.Device.Selected.GPS.Enable = device_selected.getJSONObject("GPS").getBoolean("Enable");
                    ac.Device.Selected.GPS.Mode = device_selected.getJSONObject("GPS").getInt("Mode");

                    ac.Device.Selected.Wifi.Connected = device_selected.getJSONObject("Wifi").getBoolean("Connected");

                    try {
                        ac.Device.Selected.Wifi.Name = device_selected.getJSONObject("Wifi").getString("Name");
                    } catch (Exception ex) {
                        ac.Device.Selected.Wifi.Name = null;
                    }

                    ac.Device.Selected.SIM = device_selected.getJSONArray("SIM");

                    ac.Device.Selected.AirplaneMode = device_selected.getBoolean("AirplaneMode");

                    ac.Device.Selected.Time.Server.Active = device_selected.getJSONObject("Time").getJSONObject("Server").getLong("Active");

                    ac.Device.Selected.Configuration.SendFileOverMobileNetwork = device_selected.getJSONObject("Configuration").getBoolean("SendFileOverMobileNetwork");
                    ac.Device.Selected.Configuration.Listen.Location.UpdateInterval = device_selected.getJSONObject("Configuration").getJSONObject("Listen").getJSONObject("Location").getInt("UpdateInterval");
                    ac.Device.Selected.Configuration.Listen.Location.FastestInterval = device_selected.getJSONObject("Configuration").getJSONObject("Listen").getJSONObject("Location").getInt("FastestInterval");
                    ac.Device.Selected.Configuration.Listen.Location.SmallestDisplacement = device_selected.getJSONObject("Configuration").getJSONObject("Listen").getJSONObject("Location").getInt("SmallestDisplacement");

                    //
                    ac.Device.Selected.Statics.Call.Unread = device_selected.getJSONObject("Statics").getJSONObject("Call").getInt("Unread");
                    ac.Device.Selected.Statics.Messages.SMS.Unread = device_selected.getJSONObject("Statics").getJSONObject("Messages").getJSONObject("SMS").getInt("Unread");

                }

//                Device.Selected = rs.getJSONObject("Device").getJSONObject("Selected");
                return ac;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject Logout(HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("Account", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("..");

            return null;
        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject ForgotPassword(String email) {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Email", email);

            JSONObject rs = new JSONObject(API.Request.Make("Account", "ForgotPassword", parameters));

            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static class ForgotPassword {

        public static JSONObject Confirm(String password, String token, HttpServletResponse response) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("ForgotPassword");
                actions.add("Confirm");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("Password", password);
                parameters.put("Token", token);

                JSONObject rs = new JSONObject(API.Request.Make("Account", actions, parameters));

                if (rs.getInt("Status") == 0) {
                    Cookie cookie = new Cookie("Account", rs.getString("Token"));
                    cookie.setPath("/");
                    cookie.setMaxAge(-1);
                    response.addCookie(cookie);
                }

                return rs;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static JSONObject State(String token) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("ForgotPassword");
                actions.add("State");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("Token", token);

                JSONObject rs = new JSONObject(API.Request.Make("Account", actions, parameters));

                return rs;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }
}
