/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.API;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Contacts {

    public static LinkedHashMap<String, Object> Get(int id, HttpServletRequest request) {
        LinkedHashMap<String, Object> rtn = new LinkedHashMap<>();

        ArrayList<com.dn.amonir.dto.device.Contacts> list = new ArrayList();

        try {
            ArrayList<String> actions = new ArrayList();
            actions.add("Contacts");
            actions.add("Get");

            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("DID", id);
            parameters.put("Token", com.dn.amonir.model.Cookie.Get.By.Key(request, "Account").getValue());

            JSONObject rs = new JSONObject(API.Request.Make("Device", actions, parameters));

            JSONArray rs_list = rs.getJSONArray("List");

            for (int i = 0; i < rs_list.length(); i++) {
                JSONObject rs_list_o = rs_list.getJSONObject(i);

                com.dn.amonir.dto.device.Contacts cc = new com.dn.amonir.dto.device.Contacts();
                cc.ID = rs_list_o.getInt("ID");
                cc.Name = rs_list_o.getString("Name");
                list.add(cc);
            }

            rtn.put("TotalOfContacts", rs.getInt("TotalOfContacts"));
            rtn.put("TotalOfContactsNumber", rs.getInt("TotalOfContactsNumber"));
            rtn.put("TotalOfContactsEmail", rs.getInt("TotalOfContactsEmail"));

            rtn.put("List", list);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static JSONObject Details(int id, HttpServletRequest request) {
        try {

            ArrayList<String> actions = new ArrayList();
            actions.add("Contacts");
            actions.add("Details");

            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("ID", id);
            parameters.put("Token", com.dn.amonir.model.Cookie.Get.By.Key(request, "Account").getValue());

            JSONObject rs = new JSONObject(API.Request.Make("Device", actions, parameters));

            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
