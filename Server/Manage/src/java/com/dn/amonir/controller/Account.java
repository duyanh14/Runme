/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.controller;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(value = "/Account")
public class Account {

    @RequestMapping(value = "/Login")
    @ResponseBody
    public String Login(@RequestParam(value = "Email", required = true) String email, @RequestParam(value = "Password", required = true) String password, HttpServletResponse response) {
        try {
            return com.dn.amonir.model.Account.Login(email, password, response).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Register")
    @ResponseBody
    public String Register(@RequestParam(value = "Name", required = true) String name,
            @RequestParam(value = "Email", required = true) String email,
            @RequestParam(value = "PhoneNumber", required = true) String phone_number,
            @RequestParam(value = "Password", required = true) String password,
            HttpServletResponse response) {
        try {
            return com.dn.amonir.model.Account.Register(name, email, phone_number, password, response).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Logout")
    @ResponseBody
    public String Logout(HttpServletResponse response) {
        try {
            return com.dn.amonir.model.Account.Logout(response).toString();
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/ForgotPassword")
    @ResponseBody
    public String ForgotPassword(@RequestParam(value = "Email", required = true) String email) {
        try {
            return com.dn.amonir.model.Account.ForgotPassword(email).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/ForgotPassword/Confirm")
    @ResponseBody
    public String ForgotPassword_Confirm(@RequestParam(value = "Password", required = true) String password,
            @RequestParam(value = "Token", required = true) String token,
            HttpServletResponse response) {
        try {
            return com.dn.amonir.model.Account.ForgotPassword.Confirm(password, token, response).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/ForgotPassword/State")
    @ResponseBody
    public String ForgotPassword_State(@RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.Account.ForgotPassword.State(token).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
