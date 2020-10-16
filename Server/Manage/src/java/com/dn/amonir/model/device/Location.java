/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.API;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Duy
 */
public class Location {

    public static void View(int id, HttpServletRequest request, HttpServletResponse response) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Object execute = restTemplate.execute(
                    URI.create(API.Config.HOST + "/Device/Location/View/?ID=" + id + "&Token=" + com.dn.amonir.model.Cookie.Get.By.Key(request, "Account").getValue()),
                    HttpMethod.GET,
                    (ClientHttpRequest requestx) -> {
                    },
                    responseExtractor -> {
                        response.setContentType(responseExtractor.getHeaders().getContentType().toString());
                        IOUtils.copy(responseExtractor.getBody(), response.getOutputStream());
                        return null;
                    }
            );
        } catch (java.lang.NullPointerException ee) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class History {

        public static LinkedHashMap<String, Object> Get(int id, HttpServletRequest request) {
            LinkedHashMap<String, Object> rtn = new LinkedHashMap<>();

            ArrayList<com.dn.amonir.dto.device.Location.History> list = new ArrayList();

            try {
                ArrayList<String> actions = new ArrayList();
                actions.add("Location");
                actions.add("History");
                actions.add("Get");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("DID", id);
                parameters.put("Page", 1);
                parameters.put("Limit", 1000);
                parameters.put("Token", com.dn.amonir.model.Cookie.Get.By.Key(request, "Account").getValue());

                JSONObject rs = new JSONObject(API.Request.Make("Device", actions, parameters));

                JSONArray rs_list = rs.getJSONArray("List");

                for (int i = 0; i < rs_list.length(); i++) {
                    JSONObject rs_list_o = rs_list.getJSONObject(i);

                    com.dn.amonir.dto.device.Location.History cc = new com.dn.amonir.dto.device.Location.History();
                    cc.ID = rs_list_o.getInt("ID");
                    cc.Title = rs_list_o.getString("Title");
                    cc.Coordinates = rs_list_o.getString("Coordinates");
                    cc.Time = rs_list_o.getLong("Time");
                    cc.Read = rs_list_o.getBoolean("Read");

                    list.add(cc);
                }
//
                rtn.put("TotalOfLocation", rs.getInt("TotalOfLocation"));
//                rtn.put("TotalOfSMSContent", rs.getInt("TotalOfSMSContent"));
//                rtn.put("TotalOfSMSContentSent", rs.getInt("TotalOfSMSContentSent"));
//                rtn.put("TotalOfSMSContentReceived", rs.getInt("TotalOfSMSContentReceived"));

                rtn.put("List", list);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return rtn;
        }

    }
}
