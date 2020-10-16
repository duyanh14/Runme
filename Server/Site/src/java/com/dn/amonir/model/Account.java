/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
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

    public static JSONObject Register(String name, String email, String password, String phone, String token, HttpServletResponse response) {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Name", name);
            parameters.put("Email", email);
            parameters.put("Password", password);
            parameters.put("Phone", phone);
            parameters.put("Token", token);

            JSONObject rs = new JSONObject(API.Request.Make("Account", "Register", parameters));

//            if (rs.getInt("Status") == 0) {
//                Cookie cookie = new Cookie("Account", rs.getString("Token"));
//                cookie.setPath("/");
//                cookie.setMaxAge(-1);
//                response.addCookie(cookie);
//            }

            return rs;
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

    public static JSONObject ChangePassword(String current_password, String new_password, String token) {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("CurrentPassword", current_password);
            parameters.put("NewPassword", new_password);
            parameters.put("Token", token);
            return new JSONObject(API.Request.Make("Account", "ChangePassword", parameters));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject List(int page, int limit, String token) {
        try {

            ArrayList<String> actions = new ArrayList<>();
            actions.add("List");

            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Page", page);
            parameters.put("Limit", limit);
            parameters.put("Token", token);

            return new JSONObject(API.Request.Make("Account", actions, parameters));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static class Token {

        public static com.dn.amonir.dao.Account Auth = null;

        public static com.dn.amonir.dao.Account Auth(String token) {
            try {
                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("Token", token);

                ArrayList<String> actions = new ArrayList<>();
                actions.add("Token");
                actions.add("Auth");

                JSONObject rs = new JSONObject(API.Request.Make("Account", actions, parameters));
                if (rs.getInt("Status") == 0) {
                    JSONObject rs_account = rs.getJSONObject("Account");

                    if (Token.Auth == null) {
                        Token.Auth = new com.dn.amonir.dao.Account();
                    }

                    Token.Auth.ID = rs_account.getInt("ID");
                    Token.Auth.Name = rs_account.getString("Name");
                    Token.Auth.Email = rs_account.getString("Email");
                    Token.Auth.PhoneNumber = rs_account.getString("PhoneNumber");
                    Token.Auth.Balance = rs_account.getInt("Balance");
                    Token.Auth.BalanceFrozen = rs_account.getInt("BalanceFrozen");
                    Token.Auth.Disable = rs_account.getInt("Disable");
                    Token.Auth.Permission = rs_account.getInt("Permission");
                    Token.Auth.CreateTime = rs_account.getLong("CreateTime");

                    return Token.Auth;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Token.Auth = null;
            return Token.Auth;
        }

    }

    public static class Transaction {

        public static JSONObject History(int aid, int page, int limit, String token) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("Transaction");
                actions.add("History");

                Map<String, Object> parameters = new LinkedHashMap<>();
                if (aid != 0) {
                    parameters.put("AID", aid);
                }
                parameters.put("Page", page);
                parameters.put("Limit", limit);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Account", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
