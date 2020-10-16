/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model.device;

import com.dn.amonir.model.API;
import com.dn.amonir.model.TimeUtils;
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
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Duy
 */
public class Media {

    public static JSONObject Get(int page, int limit, HttpServletRequest request) {
        try {

            ArrayList<String> actions = new ArrayList();
            actions.add("Media");
            actions.add("Get");

            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("DID", com.dn.amonir.model.Cookie.Get.By.Key(request, "Device_Selected").getValue());
            parameters.put("Page", page);
            parameters.put("Limit", limit);
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

    public static void View(int id, HttpServletRequest request, HttpServletResponse response) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Object execute = restTemplate.execute(
                    URI.create(API.Config.HOST + "/Device/Media/View/?ID=" + id + "&Token=" + com.dn.amonir.model.Cookie.Get.By.Key(request, "Account").getValue()),
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
}
