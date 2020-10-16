/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.API;
import com.dn.amonir.model.TimeUtils;
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
public class Messages {

    public static class SMS {

        public static LinkedHashMap<String, Object> Get(int id, HttpServletRequest request) {
            LinkedHashMap<String, Object> rtn = new LinkedHashMap<>();

            ArrayList<com.dn.amonir.dto.device.Messages.SMS> list = new ArrayList();

            try {
                ArrayList<String> actions = new ArrayList();
                actions.add("Messages");
                actions.add("SMS");
                actions.add("Get");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("DID", id);
                parameters.put("Page", 1);
                parameters.put("Limit", 100);
                parameters.put("Token", com.dn.amonir.model.Cookie.Get.By.Key(request, "Account").getValue());

                JSONObject rs = new JSONObject(API.Request.Make("Device", actions, parameters));

                JSONArray rs_list = rs.getJSONArray("List");

                for (int i = 0; i < rs_list.length(); i++) {
                    JSONObject rs_list_o = rs_list.getJSONObject(i);

                    com.dn.amonir.dto.device.Messages.SMS cc = new com.dn.amonir.dto.device.Messages.SMS();
                    cc.ID = rs_list_o.getInt("ID");
                    cc.Read = rs_list_o.getBoolean("Read");
                    cc.State = rs_list_o.getInt("State");
                    cc.Content = rs_list_o.getString("Content");
                    cc.Time = rs_list_o.getLong("Time");

                    cc.Contacts.ID = rs_list_o.getJSONObject("Contacts").getInt("ID");
                    cc.Contacts.Name = rs_list_o.getJSONObject("Contacts").getString("Name");
                    cc.Contacts.By.Value = rs_list_o.getJSONObject("Contacts").getJSONObject("By").getString("Value");

                    list.add(cc);
                }
//
                rtn.put("TotalOfSMS", rs.getInt("TotalOfSMS"));
                rtn.put("TotalOfSMSContent", rs.getInt("TotalOfSMSContent"));
                rtn.put("TotalOfSMSContentSent", rs.getInt("TotalOfSMSContentSent"));
                rtn.put("TotalOfSMSContentReceived", rs.getInt("TotalOfSMSContentReceived"));

                rtn.put("List", list);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return rtn;
        }

        public static JSONObject Details(int cid, HttpServletRequest request) {
            try {

                ArrayList<String> actions = new ArrayList();
                actions.add("Messages");
                actions.add("SMS");
                actions.add("Details");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("CID", cid);
                parameters.put("Token", com.dn.amonir.model.Cookie.Get.By.Key(request, "Account").getValue());

                JSONObject rs = new JSONObject(API.Request.Make("Device", actions, parameters));

                JSONArray list = rs.getJSONArray("List");

                for (int i = 0; i < list.length(); i++) {
                    JSONObject x = list.getJSONObject(i);
                    x.put("Time", TimeUtils.millisToLongDHMS(x.getLong("Time")));
                    list.put(i, x);
                }

                rs.put("List", list);

                return rs;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
