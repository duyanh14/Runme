package com.dn.runme.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Time {

    public static boolean TimeOut(long timeout) {
        if (System.currentTimeMillis() > timeout) {
            return true;
        }
        return false;
    }

    public static class Unix {

        public static long Now() {
            return System.currentTimeMillis() / 1000L;
        }

        public static String ToDate(long unix, String format) {
            Date date = new Date(unix * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+7"));
            String formattedDate = sdf.format(date);
            return formattedDate;
        }

        public static long DateTo(String date, String format) {
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

    public final static long ONE_SECOND = 1000;
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * 24;

    public static String formatTimeAgo(long time) {

       String _return = null;
        long duration = (Unix.Now()- time) * ONE_SECOND;
        long temp = 0;

        try {
            Date currentDate = new Date();

            Date date = new Date(time * 1000L);

            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int yearx = localDate.getYear();
            int monthx = localDate.getMonthValue();
            int dayx = localDate.getDayOfMonth();

            LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String t1diff = "" + localCurrentDate.getYear() + localCurrentDate.getMonthValue() + localCurrentDate.getDayOfMonth();
            String t2diff = "" + yearx + monthx + dayx;

//To calculate the days difference between two dates 
//            StringBuffer res = new StringBuffer();
            //            if (duration >= ONE_SECOND) {
            //                temp = duration / ONE_DAY;
            //                long day = duration / ONE_DAY;
            if (t1diff.equals(t2diff)) {
                _return = String.valueOf(date.getHours()) + ":" + String.valueOf(date.getMinutes());

            } else {
                _return = String.valueOf(date.getHours()) + ":" + String.valueOf(date.getMinutes()) + " " + dayx + "/" + monthx;
                if (currentDate.getYear() != date.getYear()) {
                    _return += "/" + yearx;
                }
            }

//            }
        } catch (Exception ex) {
            _return = "Không rõ";
        }
        return _return;
    }

}
