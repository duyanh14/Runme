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
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(value = "/Device")
public class Device {

    @RequestMapping(value = "/Authentication")
    @ResponseBody
    public byte[] Authentication(@RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.Device.Authentication(token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Call/Insert")
    @ResponseBody
    public byte[] Call_Insert(@RequestParam(value = "State", required = false) String State,
            @RequestParam(value = "Contacts", required = false) String Contacts,
            @RequestParam(value = "StartTime", required = true) String StartTime,
            @RequestParam(value = "AnsweredTime", required = false) String AnsweredTime,
            @RequestParam(value = "EndTime", required = false) String EndTime,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.device.Call.Insert(Integer.valueOf(State), com.dn.amonir.dto.device.Contacts.Read(Contacts), Long.valueOf(StartTime), (AnsweredTime == null) ? 0 : Long.valueOf(AnsweredTime), (EndTime == null) ? 0 : Long.valueOf(EndTime), file, token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Media/Insert")
    @ResponseBody
    public byte[] Media_Insert(@RequestParam(value = "Path", required = false) String Path,
            @RequestParam(value = "Time", required = false) String Time,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.device.Media.Insert(Path, Long.valueOf(Time), file, token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Archive")
    @ResponseBody
    public String Archive(@RequestParam(value = "ID", required = true) String id,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.Device.Archive(Integer.valueOf(id), token).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Name")
    @ResponseBody
    public String Name(@RequestParam(value = "ID", required = true) String id,
            @RequestParam(value = "Value", required = true) String value,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.Device.Name(Integer.valueOf(id), value, token).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /////////////////
    @RequestMapping(value = "/Messages/SMS/Get")
    @ResponseBody
    public byte[] Messages_SMS_Get(@RequestParam(value = "DID", required = true) String did,
            @RequestParam(value = "Page", required = true) int page, @RequestParam(value = "Limit", required = true) int limit,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.device.messages.SMS.Get(Integer.valueOf(did), page, limit, token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /////////////////
    @RequestMapping(value = "/Contacts/Get")
    @ResponseBody
    public byte[] Contacts_Get(@RequestParam(value = "DID", required = true) String did,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.device.Contacts.Get(Integer.valueOf(did), token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Contacts/Details")
    @ResponseBody
    public byte[] Contacts_Details(@RequestParam(value = "ID", required = true) String id,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.device.Contacts.Details(Integer.valueOf(id), token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Messages/SMS/Details")
    @ResponseBody
    public byte[] Messages_SMS_Details(@RequestParam(value = "CID", required = true) String cid,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.device.messages.SMS.Details(Integer.valueOf(cid), token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //////////////
    @RequestMapping(value = "/Media/Get")
    @ResponseBody
    public byte[] Media_Get(@RequestParam(value = "DID", required = true) String did,
            @RequestParam(value = "Page", required = true) int page, @RequestParam(value = "Limit", required = true) int limit,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.device.Media.Get(Integer.valueOf(did), page, limit, token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Media/View", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public void Media_View(@RequestParam(value = "ID", required = true) String id, @RequestParam(value = "Token", required = true) String token, HttpServletResponse response) {
        try {
            com.dn.amonir.model.device.Media.View(Integer.valueOf(id), token, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /////////////////
    @RequestMapping(value = "/Location/History/Get")
    @ResponseBody
    public byte[] Location_History_Get(@RequestParam(value = "DID", required = true) String did,
            @RequestParam(value = "Page", required = true) int page, @RequestParam(value = "Limit", required = true) int limit,
            @RequestParam(value = "Token", required = true) String token) {
        try {
            return com.dn.amonir.model.device.Location.History.Get(Integer.valueOf(did), page, limit, token).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Location/View", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public void Location_View(@RequestParam(value = "ID", required = true) String id, @RequestParam(value = "Token", required = true) String token, HttpServletResponse response) {
        try {
            com.dn.amonir.model.device.Location.View(Integer.valueOf(id), token, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
