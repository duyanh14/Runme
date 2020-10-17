package com.dn.runme;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.dn.runme.model.API;

import java.util.LinkedHashMap;
import java.util.Map;

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }


        @JavascriptInterface
        public  void Account_Login(final String email, final String password) {
                    Map<String, Object> parameters = new LinkedHashMap<>();
                    parameters.put("Email",email);
                    parameters.put("Password",password);
                    String request = API.Request.Make("Account", "Login", parameters);
                    Log.d("Request",request);
                    MainActivity.webView.post(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.webView.loadUrl("javascript:account_login_native();");
                        }
                    });
        }

}
