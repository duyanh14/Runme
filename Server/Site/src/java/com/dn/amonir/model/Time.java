package com.dn.amonir.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static boolean TimeOut(long timeout) {
        if (System.currentTimeMillis() > timeout) {
            return true;
        }
        return false;
    }
    public static class Unix {
        public static long getNow() {
            return System.currentTimeMillis() / 1000L;
        }

        public static String toDate(long unix, String format) {
            Date date = new Date(unix * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+7"));
            String formattedDate = sdf.format(date);
            return formattedDate;
        }

        public static long dateTo(String date, String format) {
            long unixTime = 0;
            try {
                String dateString = date;
                DateFormat dateFormat = new SimpleDateFormat(format);
                unixTime = (long) dateFormat.parse(dateString).getTime() / 1000;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            return unixTime;
        }
    }

}
