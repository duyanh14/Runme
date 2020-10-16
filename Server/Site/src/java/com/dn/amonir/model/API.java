/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Nguyen
 */
public class API {

    public static class Config {
        public static String URL = "http://127.0.0.1:1288/Backend/";
    }

    public static class Request {

        public static String Make(Proxy proxy, String function, ArrayList<String> action, Map<String, Object> parameters) {
            try {

                if (parameters == null) {
                    parameters = new LinkedHashMap<String, Object>();
                }

                String surl_para = "";

                Set<String> keys = parameters.keySet();
                for (String key : keys) {
                    surl_para += key + "=" + URLEncoder.encode(String.valueOf(parameters.get(key)), "UTF-8") + "&";
                }

                StringBuffer sbaction = new StringBuffer();
                for (int i = 0; i < action.size(); i++) {
                    sbaction.append(action.get(i) + "/");
                }

                String surl = API.Config.URL + "/" + function + "/" + sbaction.toString() + "/" + "?" + surl_para;

                System.out.println(surl);

                URL obj = new URL(surl);

                HttpURLConnection con;

                if (proxy == null) {
                    con = (HttpURLConnection) obj.openConnection();
                } else {
                    con = (HttpURLConnection) obj.openConnection(proxy);
                }

                con.setRequestMethod("POST");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream(),"UTF-8"));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static String Make(String function, String action) {
            ArrayList<String> aa = new ArrayList<>();
            aa.add(action);
            return Make(null, function, aa, null);
        }

        public static String Make(String function, ArrayList<String> action) {
            return Make(null, function, action, null);
        }

        public static String Make(String function, String action, Map<String, Object> parameters) {
            ArrayList<String> aa = new ArrayList<>();
            aa.add(action);
            return Make(null, function, aa, parameters);
        }

        public static String Make(String function, ArrayList<String> action, Map<String, Object> parameters) {
            return Make(null, function, action, parameters);
        }

        public static String Make(Proxy proxy, String function, String action) {
            ArrayList<String> aa = new ArrayList<>();
            aa.add(action);
            return Make(proxy, function, aa, null);
        }

        public static class Thread {
            
            public static Map<Long, java.lang.Thread> Thread = new LinkedHashMap<>();

            public static void Make(final Proxy proxy, final String function, final ArrayList<String> action, final Map<String, Object> parameters) {
                java.lang.Thread thread = new java.lang.Thread(new Runnable() {
                    @Override
                    public void run() {
                        long threadID = java.lang.Thread.currentThread().getId();
                        try {
                            Request.Make(proxy, function, action, parameters);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        Thread.remove(threadID);
                    }
                });
                Thread.put(thread.getId(), thread);
                thread.start();
            }

            public static void Make(String function, String action, Map<String, Object> parameters) {
                ArrayList<String> aa = new ArrayList<>();
                aa.add(action);
                Make(null, function, aa, parameters);
            }
            
              public static void Make(String function,  ArrayList<String> actions, Map<String, Object> parameters) {
       
                Make(null, function, actions, parameters);
            }

            public static boolean isWait() {
                for (Long key : Thread.keySet()) {
                    if (Thread.get(key).isAlive()) {
                        return true;
                    }
                }
                return false;
            }
        }

    }

}
