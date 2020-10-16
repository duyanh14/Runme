/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pc
 */
public class Cookie {

    public static class Get {

        public static class By {

            public static javax.servlet.http.Cookie Key (HttpServletRequest request, String name) {
                javax.servlet.http.Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (javax.servlet.http.Cookie cookie : cookies) {
                        if (cookie.getName().equals(name)) {
                            return cookie;
                        }
                    }
                }
                return null;
            }
        }
    }

}
