/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.runme.model;

import org.json.JSONObject;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Duy
 */
public class Account {

    public static Map<String, com.dn.runme.dto.Account> List = new LinkedHashMap<>();

    public static JSONObject Login(String email, String password) {
        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            com.dn.runme.dto.Account account = Authentication(email, password);

            if (account == null) {
                rtn.put("Status", 2);
                rtn.put("Message", "Account does not match.");

            } else {

                rtn.put("Status", 0);
                rtn.put("Token", account.Token);

                rtn.put("Email", account.Email);
                rtn.put("FristName", account.FristName);
                rtn.put("LastName", account.LastName);
                rtn.put("BirthYear", account.BirthYear);
                rtn.put("DateCreated", account.DateCreated);

                rtn.remove("Message");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static com.dn.runme.dto.Account Authentication(String email, String password) {
        try {
            Session sess = Database.Connect();

            ResultSet rs = sess.execute("SELECT * FROM account WHERE email = '" + email + "' AND password = '" + password + "' ALLOW FILTERING ");
            Row row = rs.one();
            if (row == null) {
                return null;
            }

            return Initialization(row);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static com.dn.runme.dto.Account Authentication_Token(String token) {
        JSONObject rtn = new JSONObject();
        try {
            Session sess = Database.Connect();

            ResultSet rs = sess.execute("SELECT * FROM account WHERE \"token\" = '" + token + "' ALLOW FILTERING");
            Row row = rs.one();
            if (row == null) {
                return null;
            }

            com.dn.runme.dto.Account account = Initialization(row);

            return account;

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

            com.dn.runme.dto.Account account = Authentication_Token(token);
            
            if (account == null) {
                rtn.put("Status", 2);
                rtn.put("Message", "Can't authentication.");
                return rtn;
            }

            rtn.put("Status", 0);
            rtn.put("Email", account.Email);
            rtn.put("FristName", account.FristName);
            rtn.put("LastName", account.LastName);
            rtn.put("BirthYear", account.BirthYear);
            rtn.put("DateCreated", account.DateCreated);
               
            rtn.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;

    }

    public static com.dn.runme.dto.Account Initialization(Row rs) {
        try {

            com.dn.runme.dto.Account account = List.get(rs.getString("id"));

            if (account == null) {
                account = new com.dn.runme.dto.Account();
                account.ID = rs.getString("id");
                List.put(account.ID, account);
            }

            account.Password = rs.getString("password");
            account.FristName = rs.getString("frist_name");
            account.LastName = rs.getString("last_name");
            account.Token = rs.getString("token");
            account.DateCreated = rs.getLong("date_created");
            account.BirthYear = rs.getLong("birth_year");
            account.Email = rs.getString("email");

            return account;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
