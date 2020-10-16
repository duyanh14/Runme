/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Buy {

    public static JSONObject ScratchCard(int carrier, int price, String token) {
        try {

            Thread.sleep(2000);

            ArrayList<String> actions = new ArrayList<>();
            actions.add("ScratchCard");

            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Carrier", carrier);
            parameters.put("Price", price);
            parameters.put("Token", token);

            JSONObject rs = new JSONObject(API.Request.Make("Buy", actions, parameters));
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static class ScratchCard {

        public static JSONObject History(int page, int limit, String token) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("ScratchCard");
                actions.add("History");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("Page", page);
                parameters.put("Limit", limit);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Buy", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }

}
