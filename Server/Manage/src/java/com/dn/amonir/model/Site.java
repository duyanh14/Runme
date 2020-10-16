/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Site {

    public static class Manage {
        public static JSONObject Page(String page) throws JSONException {

            ArrayList<String> actions = new ArrayList<>();
            actions.add("Manage");
            actions.add("Get");

            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Page_Active", page);

            return new JSONObject(API.Request.Make("Site", actions, parameters));
        }
    }

}
