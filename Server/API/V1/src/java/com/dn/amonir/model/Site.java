/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import static com.dn.amonir.model.Device.Initialization;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Site {

//    public static JSONObject Get() throws JSONException {
//        JSONObject he = new JSONObject();
//
//        he.put("Manage", new JSONObject().put("Page", Manage.Page()));
//
//        return he;
//    }
    public static class Manage {

        public static JSONObject Get(String Page_Active) throws JSONException {
            JSONObject he = new JSONObject();

            JSONObject page = new JSONObject();

            page.put("List", Manage.Page.List(Page_Active));


            JSONArray breadcrumb = new JSONArray();

            ////
            Connection conn = Database.Connect();
            PreparedStatement ps;
            ResultSet rs = null;
            String sql;
            try {
                sql = "SELECT * FROM [dbo].[site_manage_page] WHERE path = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, Page_Active);
                rs = ps.executeQuery();

                rs.next();

                if (rs.getInt("child") != 0) {

                    ArrayList<ResultSet> rsl = new ArrayList();

                    int child = rs.getInt("child");
                    while (true) {
                        sql = "SELECT * FROM [dbo].[site_manage_page] WHERE id = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1, child);
                        ResultSet rs1 = ps.executeQuery();

                        if (!rs1.next()) {
                            break;
                        }

                        rsl.add(rs1);

                        if (rs1.getInt("child") == 0) {
                            break;
                        }

                        child = rs1.getInt("child");
                    }

                    for (int i = rsl.size() - 1; i >= 0; i -= 1) {
                        ResultSet rslg = rsl.get(i);

                        String path = null;
                        if (rslg.getString("path") != null) {
                            if (!rslg.getString("path").isEmpty()) {
                                path = rslg.getString("path");
                            }
                        }

                        JSONObject breadcrumb_o = new JSONObject().put("Name", rslg.getString("name")).put("Icon", rslg.getString("icon")).put("Path", path).put("Home", rslg.getBoolean("home"));;

                        breadcrumb.put(breadcrumb_o);
                    }

                }

                String path = null;
                if (rs.getString("path") != null) {
                    if (!rs.getString("path").isEmpty()) {
                        path = rs.getString("path");
                    }
                }

                JSONObject breadcrumb_o = new JSONObject().put("Name", rs.getString("name")).put("Icon", rs.getString("icon")).put("Path", path).put("Home", rs.getBoolean("home"));;

                            page.put("Active", breadcrumb_o);

                breadcrumb.put(breadcrumb_o);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            ////

            page.put("Breadcrumb", breadcrumb);

            he.put("Page", page);

            return he;
        }

        public static class Page {

            public static JSONArray List(String Page_Active) {
                JSONArray he = new JSONArray();

                Connection conn = Database.Connect();
                PreparedStatement ps;
                ResultSet rs = null;
                String sql;

                try {
                    sql = "SELECT * FROM [dbo].[site_manage_page] WHERE child=0 AND show = 1 ORDER BY [order] ASC;";
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        String path = null;
                        if (rs.getString("path") != null) {
                            if (!rs.getString("path").isEmpty()) {
                                path = rs.getString("path");
                            }
                        }

                        JSONObject he1 = new JSONObject().put("Name", rs.getString("name")).put("Icon", rs.getString("icon")).put("Path", path).put("Home", rs.getBoolean("home")).put("Active", (rs.getString("path") == null) ? false : rs.getString("path").equals(Page_Active));
                        try {

                            String sql1 = "SELECT * FROM [dbo].[site_manage_page] WHERE child=? AND show = 1 ORDER BY [order] ASC;";
                            PreparedStatement ps1 = conn.prepareStatement(sql1);
                            ps1.setInt(1, rs.getInt("id"));
                            ResultSet rs1 = ps1.executeQuery();
                            boolean child = false;
                            JSONArray he2 = new JSONArray();
                            while (rs1.next()) {
                                if (child == false) {
                                    child = true;
                                    he1.put("Child", he2);
                                }
                                JSONObject he3 = new JSONObject().put("Name", rs1.getString("name")).put("Icon", rs1.getString("icon")).put("Path", rs1.getString("path")).put("Active", (rs1.getString("path") == null) ? false : rs1.getString("path").equals(Page_Active));
                                he2.put(he3);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        he.put(he1);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return he;
            }
        }

    }
}
