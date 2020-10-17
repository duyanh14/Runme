package com.dn.runme.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Script {

    public static String getLanguage(int lang) {
        if (lang == 1) {
            return "Python";
        }
        return null;
    }


}
