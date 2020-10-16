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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(value = "/Device")
public class Device {

    @RequestMapping(value = "/Selected")
    @ResponseBody
    public String Selected(@RequestParam(value = "ID", required = true) String id, HttpServletResponse response) {
        try {
            return com.dn.amonir.model.Device.Selected(Integer.valueOf(id), response).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Archive")
    @ResponseBody
    public String Archive(@RequestParam(value = "ID", required = true) String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            return com.dn.amonir.model.Device.Archive(Integer.valueOf(id), request, response).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Contacts/Details")
    @ResponseBody
    public byte[] Contacts_Details(@RequestParam(value = "ID", required = true) String id, HttpServletRequest request) {
        try {
            return com.dn.amonir.model.device.Contacts.Details(Integer.valueOf(id), request).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Name")
    @ResponseBody
    public String Name(@RequestParam(value = "ID", required = true) String id,
            @RequestParam(value = "Value", required = true) String value,
            HttpServletRequest request) {
        try {
            return com.dn.amonir.model.Device.Name(Integer.valueOf(id), value, request).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Messages/SMS/Details")
    @ResponseBody
    public byte[] Messages_SMS_Details(@RequestParam(value = "CID", required = true) String cid, HttpServletRequest request) {
        try {
            return com.dn.amonir.model.device.Messages.SMS.Details(Integer.valueOf(cid), request).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Media/Get")
    @ResponseBody
    public byte[] Media_Get(@RequestParam(value = "Page", required = true) String page,
            @RequestParam(value = "Limit", required = true) String limit,
            HttpServletRequest request) {
        try {
            return com.dn.amonir.model.device.Media.Get(Integer.valueOf(page), Integer.valueOf(limit), request).toString().getBytes("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Media/View", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public void Media_View(@RequestParam(value = "ID", required = true) String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            com.dn.amonir.model.device.Media.View(Integer.valueOf(id), request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/Location/View", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public void Location_View(@RequestParam(value = "ID", required = true) String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            com.dn.amonir.model.device.Location.View(Integer.valueOf(id), request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
