package com.dn.runme.model;

import android.content.SharedPreferences;
import android.util.Log;

import com.dn.runme.Runme;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Map;

public class Account {

    public static String Token;
    public static String Email;
    public static  String FristName;
    public static String LastName;
    public static long BirthYear;
    public static long DateCreated;

    public static boolean Authentication() {
        try {
            String token = getToken();
            if (token == null) {
                return false;
            }
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Token", getToken());
            JSONObject request = new JSONObject(API.Request.Make("Account", "Authentication", parameters));
            if (request.getInt("Status") == 0) {
                Account.Token = token;
                Account.Init(request);
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setToken(null);
        return false;
    }

    public static void Init(JSONObject account) {
        try {
            Account.Email = account.getString("Email");
            Account.FristName = account.getString("FristName");
            Account.LastName = account.getString("LastName");
            Account.BirthYear = account.getLong("BirthYear");
            Account.DateCreated = account.getLong("DateCreated");

            if (!account.isNull("Token")) {
                setToken(account.getString("Token"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getToken() {
        if (Account.Token == null) {
            return Runme.sharedPref.getString("Token", null);
        }
        return Account.Token;
    }

    public static void setToken(String token) {
        Account.Token = token;
        SharedPreferences.Editor edit = Runme.sharedPref.edit();
        edit.putString("Token",Account.Token);
        edit.apply();
    }

    public static void Logout() {
        setToken(null);
        Account.Token = null;
    }
}
