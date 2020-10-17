package com.dn.runme;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.dn.runme.model.API;
import com.dn.runme.model.Account;
import com.dn.runme.model.MD5;
import com.dn.runme.model.Script;
import com.dn.runme.model.Time;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class WebAppInterface {
    Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void Account_Login(final String email, final String password) {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Email", email);
            parameters.put("Password", MD5.hash(password));
            JSONObject request = new JSONObject(API.Request.Make("Account", "Login", parameters));
            if (request.getInt("Status") == 0) {
                Account.Init(request);
                MainActivity.webView.post(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.webView.loadUrl("javascript:account_login_callback(0);");
                    }
                });
                return;
            }
            MainActivity.webView.post(new Runnable() {
                @Override
                public void run() {
                    MainActivity.webView.loadUrl("javascript:account_login_callback(1);");
                }
            });
            showToast("Account does not exist or incorrect login information.");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainActivity.webView.post(new Runnable() {
            @Override
            public void run() {
                MainActivity.webView.loadUrl("javascript:account_login_callback(2);");
            }
        });
        showToast("Unknown error.");
    }

    @JavascriptInterface
    public void Account_Logout() {
        Account.Logout();
    }

    @JavascriptInterface
    public void Account_Script_List() {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Token", Account.Token);
            JSONObject request = new JSONObject(API.Request.Make("Script", "List", parameters));
            if (request.getInt("Status") == 0) {
                JSONObject list = request.getJSONObject("List");

                Iterator<String> keys = list.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    final JSONObject obj = list.getJSONObject(key);
                    MainActivity.webView.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MainActivity.webView.loadUrl("javascript:account_script_list_put('" + obj.getString("ID") + "','" + obj.getString("Name") + "','" + obj.getString("Language") + "')");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @JavascriptInterface
    public void Account_Script_Details(final String id) {
        try {
            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("ID", id);
            parameters.put("Token", Account.Token);
            final JSONObject request = new JSONObject(API.Request.Make("Script", "Get", parameters));
            if (request.getInt("Status") == 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MainActivity.webView.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    MainActivity.webView.loadUrl("javascript:account_script_details_callback('" + id + "','" + request.getString("Name") + "'," + request.getInt("Language") + ",'" + Time.formatTimeAgo(request.getLong("DateModified") )+ "','" + Time.formatTimeAgo(request.getLong("DateCreated") )+ "')");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        showToast("Can't get script details.");
        MainActivity.webView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    MainActivity.webView.loadUrl("javascript:showPageView('dashboard','script')");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @JavascriptInterface
    public void Account_Script_Details_Save(final String id, final String name, final String langs) {
        try {
            final int lang = Integer.valueOf(langs);

            Map<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("Token", Account.Token);
            parameters.put("ID", id);
            parameters.put("Name", name);
            parameters.put("Language", lang);

            JSONObject request = new JSONObject(API.Request.Make("Script", "Save", parameters));

            if (request.getInt("Status") == 0) {
                showToast("Saved.");
                MainActivity.webView.post(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.webView.loadUrl("javascript:account_script_list_set('" + id + "','" + name + "','" + Script.getLanguage(lang) + "')");
                    }
                });


            } else {
                showToast("Save failed.");
            }

        } catch (Exception ex) {
            showToast("Save failed.");
            ex.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainActivity.webView.post(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.webView.loadUrl("javascript:account_script_details_save_callback()");
                    }
                });
            }
        }).start();
    }

}
