/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class SIM {

    public static JSONObject Report() {
          try {
            JSONObject rs = new JSONObject(API.Request.Make("SIM", "Report"));
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
