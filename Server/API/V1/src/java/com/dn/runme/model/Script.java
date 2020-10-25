/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.runme.model;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import static com.dn.runme.model.Account.Initialization;
import com.dn.runme.model.Crypt.RC4;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Duy
 */
public class Script {

    public static Map<String, com.dn.runme.dto.Script> List = new LinkedHashMap<>();

    public static JSONObject List(String token) {
        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            com.dn.runme.dto.Account account = Account.Authentication_Token(token);

            if (account == null) {
                rtn.put("Status", 2);
                rtn.put("Message", "Can't authentication account.");
                return rtn;
            }

            Session sess = Database.Connect();

            JSONObject list = new JSONObject();

            ResultSet rs = sess.execute("SELECT * FROM script WHERE account = '" + account.ID + "' ALLOW FILTERING");
            for (Row row : rs) {
                com.dn.runme.dto.Script script = Script.Initialization(row);
                list.put(script.ID, new JSONObject().put("ID", script.ID).put("Name", script.Name).put("Content", script.Content).put("DateModified", script.DateModified).put("DateCreated", script.DateCreated).put("Language", Script.getLanguage(script.Language)));
            }

            rtn.put("List", list);
            rtn.put("Status", 0);
            rtn.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static JSONObject Get(String id, String token) {
        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            com.dn.runme.dto.Account account = Account.Authentication_Token(token);

            if (account == null) {
                rtn.put("Status", 2);
                rtn.put("Message", "Can't authentication account.");
                return rtn;
            }

            Session sess = Database.Connect();

            com.dn.runme.dto.Script script = Script.Get_ID(id);
            if (script == null) {
                rtn.put("Status", 3);
                rtn.put("Message", "Script not exist.");
                return rtn;
            }

            if (!script.Account.equals(account.ID)) {
                rtn.put("Status", 4);
                rtn.put("Message", "Can't authentication account.");
                return rtn;
            }

            rtn.put("Name", script.Name);
            rtn.put("Language", script.Language);
            rtn.put("DateModified", script.DateModified);
            rtn.put("DateCreated", script.DateCreated);
            rtn.put("Content", script.Content);

            rtn.put("Status", 0);
            rtn.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static com.dn.runme.dto.Script Get_ID(String id) {
        try {

            Session sess = Database.Connect();

            JSONObject list = new JSONObject();

            ResultSet rs = sess.execute("SELECT * FROM script WHERE id = '" + id + "' ALLOW FILTERING");
            Row row = rs.one();

            if (row == null) {
                return null;
            }
            return Initialization(row);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static com.dn.runme.dto.Script Initialization(Row rs) {
        try {

            com.dn.runme.dto.Script script = List.get(rs.getString("id"));

            if (script == null) {
                script = new com.dn.runme.dto.Script();
                script.ID = rs.getString("id");
                List.put(script.ID, script);
            }

            script.ID = rs.getString("id");
            script.Name = rs.getString("name");
            script.Content = new RC4(RC4.Key()).Decrypt(rs.getString("content"));
            script.DateModified = rs.getLong("date_modified");
            script.DateCreated = rs.getLong("date_created");
            script.Language = rs.getInt("language");
            script.Account = rs.getString("account");

            return script;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getLanguage(int lang) {
        if (lang == 1) {
            return "Python";
        }
        return null;
    }

    public static JSONObject Save(String id, String name, String lang, String content, String token) {
        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            com.dn.runme.dto.Account account = Account.Authentication_Token(token);

            if (account == null) {
                rtn.put("Status", 2);
                rtn.put("Message", "Can't authentication account.");
                return rtn;
            }

            Session sess = Database.Connect();

            com.dn.runme.dto.Script script = Script.Get_ID(id);
            if (script == null) {
                rtn.put("Status", 3);
                rtn.put("Message", "Script not exist.");
                return rtn;
            }

            if (!script.Account.equals(account.ID)) {
                rtn.put("Status", 4);
                rtn.put("Message", "Can't authentication account.");
                return rtn;
            }

            if (name != null) {
                sess.execute("UPDATE script SET name = '" + name + "' WHERE id = '" + id + "' ");
            }

            if (lang != null) {
                sess.execute("UPDATE script SET language = " + lang + " WHERE id = '" + id + "' ");
            }

            if (content != null) {
                sess.execute("UPDATE script SET content = '" + new RC4(RC4.Key()).Encrypt(content) + "' WHERE id = '" + id + "' ");
            }

            sess.execute("UPDATE script SET date_modified = " + Time.Unix.Now() + " WHERE id = '" + id + "' ");

            rtn.put("Status", 0);
            rtn.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static JSONObject Add(String name, String lang, String token) {
        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            com.dn.runme.dto.Account account = Account.Authentication_Token(token);

            if (account == null) {
                rtn.put("Status", 2);
                rtn.put("Message", "Can't authentication account.");
                return rtn;
            }

            Session sess = Database.Connect();

            String id = ID.Gen("");

            sess.execute("INSERT INTO script (id, account, name, language,content,date_modified, date_created) VALUES('" + id + "','" + account.ID + "', '" + name + "', " + lang + ", '', " + Time.Unix.Now() + ", " + Time.Unix.Now() + ");");

            rtn.put("ID", id);
            rtn.put("Status", 0);
            rtn.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

    public static JSONObject Delete(String id, String token) {
        JSONObject rtn = new JSONObject();
        try {
            rtn.put("Status", 1);
            rtn.put("Message", "An unknown error.");

            com.dn.runme.dto.Account account = Account.Authentication_Token(token);

            if (account == null) {
                rtn.put("Status", 2);
                rtn.put("Message", "Can't authentication account.");
                return rtn;
            }
            
            com.dn.runme.dto.Script script = Script.Get_ID(id);
            if (script == null) {
                rtn.put("Status", 3);
                rtn.put("Message", "Script not exist.");
                return rtn;
            }

            if (!script.Account.equals(account.ID)) {
                rtn.put("Status", 4);
                rtn.put("Message", "Can't authentication account.");
                return rtn;
            }

            Session sess = Database.Connect();


            sess.execute("DELETE FROM script WHERE id='"+id+"' IF EXISTS;");

            rtn.put("Status", 0);
            rtn.remove("Message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtn;
    }

}
