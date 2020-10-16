/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Request {

    HttpServletRequest request;

    public com.dn.amonir.dto.Account Account = null;

    public JSONObject Site_Manage_Page = null;

    public static ArrayList<String> Page_Device_Require = new ArrayList();

    static {
        Page_Device_Require.add("contacts");
        Page_Device_Require.add("messages-sms");
        Page_Device_Require.add("call");
        Page_Device_Require.add("location-latest");
        Page_Device_Require.add("location-history");
        Page_Device_Require.add("media");
        Page_Device_Require.add("clipboard");
    }

    public Request(HttpServletRequest request, String page) {
        this.request = request;

        this.Account = com.dn.amonir.model.Account.Authentication(request);

        try {
            Site_Manage_Page = Site.Manage.Page(page);
        } catch (JSONException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
