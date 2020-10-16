package com.dn.amonir.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class API {

    public static class Config {

        public static String HOST = "http://127.0.0.1/amonir/api";
    }

    public static class File {

        private java.io.File file;
        private boolean del = false;

        public File(java.io.File file) {
            this.file = file;
        }

        public File(java.io.File file, boolean del) {
            this.file = file;
            this.del = del;
        }

        public java.io.File File() {
            return file;
        }

        public boolean Delete() {
            return del;
        }
    }

    public static class Request {

        public static String Make(Proxy proxy, String function, ArrayList<String> actions, Map<String, Object> parameters, File file) {
            try {

                if (parameters == null) {
                    parameters = new LinkedHashMap<String, Object>();
                }

//                if (Account.Token != null) {
//                    parameters.put("Token", Account.Token);
//                }
//
//                if (Device.ID != 0) {
//                    parameters.put("DID", Device.ID);
//                }
                String surl_para = "";

                Set<String> keys = parameters.keySet();
                for (String key : keys) {
                    surl_para += key + "=" + URLEncoder.encode(String.valueOf(parameters.get(key)), "UTF-8") + "&";
                }

                StringBuffer sbaction = new StringBuffer();
                for (int i = 0; i < actions.size(); i++) {
                    sbaction.append(actions.get(i) + "/");
                }

                String surl = API.Config.HOST + "/" + function + "/" + sbaction.toString() + "?" + surl_para;

                System.out.println(surl);

                ///////////////////////////////////////////////
                HttpURLConnection connection = null;
                DataOutputStream outputStream = null;
                InputStream inputStream = null;

                String twoHyphens = "--";
                String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
                String lineEnd = "\r\n";

                String result = "";

                ///////// Upload file
                FileInputStream fileInputStream = null;

                URL url = new URL(surl);
                if (proxy == null) {
                    connection = (HttpURLConnection) url.openConnection();
                } else {
                    connection = (HttpURLConnection) url.openConnection(proxy);
                }

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "multipart/form-data; application/x-www-form-urlencoded; boundary=" + boundary);

                if (file != null) {
                    java.io.File ffile = file.File();
                    if (ffile.exists()) {

                        fileInputStream = new FileInputStream(ffile);

                        int bytesRead, bytesAvailable, bufferSize;
                        byte[] buffer;
                        int maxBufferSize = 1 * 1024 * 1024;

                        String[] q = ffile.getPath().split("/");
                        int idx = q.length - 1;

                        outputStream = new DataOutputStream(connection.getOutputStream());

                        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                        outputStream.writeBytes("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
                        outputStream.writeBytes("Content-Type: multipart/form-data; application/x-www-form-urlencoded");
                        outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

                        outputStream.writeBytes(lineEnd);

                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        while (bytesRead > 0) {
                            outputStream.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        }

                        outputStream.writeBytes(lineEnd);

                        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                        if (file.Delete()) {
                            ffile.delete();
                        }

                    }

                }
                //////////////////

                inputStream = connection.getInputStream();

                // convertStreamToString
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                StringBuilder sb = new StringBuilder();

                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                result = sb.toString();
                ////

                if (fileInputStream != null) {
                    fileInputStream.close();
                }

                inputStream.close();
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }

                
                return result;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static String Make(String function, String action) {
            ArrayList<String> aa = new ArrayList<>();
            aa.add(action);
            return Make(null, function, aa, null, null);
        }

        public static String Make(String function, ArrayList<String> action) {
            return Make(null, function, action, null, null);
        }

        public static String Make(String function, String action, Map<String, Object> parameters) {
            ArrayList<String> aa = new ArrayList<>();
            aa.add(action);
            return Make(null, function, aa, parameters, null);
        }

        public static String Make(String function, String action, Map<String, Object> parameters, File file) {
            ArrayList<String> aa = new ArrayList<>();
            aa.add(action);
            return Make(null, function, aa, parameters, file);
        }

        public static String Make(String function, ArrayList<String> action, Map<String, Object> parameters) {
            return Make(null, function, action, parameters, null);
        }

        public static String Make(Proxy proxy, String function, String action) {
            ArrayList<String> aa = new ArrayList<>();
            aa.add(action);
            return Make(proxy, function, aa, null, null);
        }

        public static class Thread {

            public static Map<Long, java.lang.Thread> Thread = new LinkedHashMap<>();

            public static void Make(final Proxy proxy, final String function, final ArrayList<String> action, final Map<String, Object> parameters) {
                java.lang.Thread thread = new java.lang.Thread(new Runnable() {
                    @Override
                    public void run() {
                        long threadID = java.lang.Thread.currentThread().getId();
                        try {
                            Request.Make(proxy, function, action, parameters, null);
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

            public static void Make(String function, ArrayList<String> actions, Map<String, Object> parameters) {

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
