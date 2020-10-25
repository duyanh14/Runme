/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.runme.controller;

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

    @RequestMapping(value = "/Login", method=RequestMethod.POST)
    @ResponseBody
    public String Login(@RequestParam(value = "Email", required = true) String email, @RequestParam(value = "Password", required = true) String password) {
        try {
            return com.dn.runme.model.Account.Login(email, password).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
     @RequestMapping(value = "/Authentication", method=RequestMethod.POST)
    @ResponseBody
    public byte[] Authentication(@RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.runme.model.Account.Authentication(token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
