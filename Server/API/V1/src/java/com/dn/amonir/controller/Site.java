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
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(value = "/Site")
public class Site {
    
//    @RequestMapping(value = "/Get")
//    @ResponseBody
//    public byte[] Get() {
//        try {
//            return com.dn.amonir.model.Site.Get().toString().getBytes("UTF-8");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
    
    @RequestMapping(value = "/Manage/Get")
    @ResponseBody
    public byte[] Manage_Get(@RequestParam(value = "Page_Active", required = true) String page_active) {
        try {
            return com.dn.amonir.model.Site.Manage.Get(page_active).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
