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
public class Paymment {

    public static class Game {

        public static JSONObject PriceDetect(String content) {
            try {
                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("Content", content);

                ArrayList<String> actions = new ArrayList<>();
                actions.add("Game");
                actions.add("PriceDetect");

                JSONObject rs = new JSONObject(API.Request.Make("Payment", actions, parameters));
                return rs;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static JSONObject History(int page, int limit, String token) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("Game");
                actions.add("History");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("Page", page);
                parameters.put("Limit", limit);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static JSONObject Request(String message_content, String token) {
            try {

                Thread.sleep(2000);

                ArrayList<String> actions = new ArrayList<>();
                actions.add("Game");
                actions.add("Request");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("MessageContent", message_content);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }

    public static class PrepaidMobile {

        public static JSONObject Add(String phone_number, int carrier, int price, String token) {
            try {

                Thread.sleep(2000);

                ArrayList<String> actions = new ArrayList<>();
                actions.add("PrepaidMobile");
                actions.add("Add");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("PhoneNumber", phone_number);
                parameters.put("Carrier", carrier);
                parameters.put("Price", price);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static JSONObject Run(int id, String token) {
            try {

//                Thread.sleep(2000);
                ArrayList<String> actions = new ArrayList<>();
                actions.add("PrepaidMobile");
                actions.add("Run");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("ID", id);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static JSONObject History(int page, int limit, String token) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("PrepaidMobile");
                actions.add("History");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("Page", page);
                parameters.put("Limit", limit);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static JSONObject Note(int id, String value, String token) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("PrepaidMobile");
                actions.add("Note");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("ID", id);
                parameters.put("Value", value);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }

    public static class PostpaidMobile {

        public static JSONObject Request(String phone_number, int carrier, int price, String token) {
            try {

                Thread.sleep(2000);

                ArrayList<String> actions = new ArrayList<>();
                actions.add("PostpaidMobile");
                actions.add("Request");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("PhoneNumber", phone_number);
                parameters.put("Carrier", carrier);
                parameters.put("Price", price);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static JSONObject History(int page, int limit, String token) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("PostpaidMobile");
                actions.add("History");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("Page", page);
                parameters.put("Limit", limit);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static JSONObject Note(int id, String value, String token) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("PostpaidMobile");
                actions.add("Note");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("ID", id);
                parameters.put("Value", value);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static JSONObject AwaitingWapproval(int page, int limit, String token) {
            try {

                ArrayList<String> actions = new ArrayList<>();
                actions.add("PostpaidMobile");
                actions.add("AwaitingWapproval");

                Map<String, Object> parameters = new LinkedHashMap<>();
                parameters.put("Page", page);
                parameters.put("Limit", limit);
                parameters.put("Token", token);

                return new JSONObject(API.Request.Make("Payment", actions, parameters));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
