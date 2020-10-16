package com.dn.amonir.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public String formatTimeAgo(long millis) {
        String[] ids = new String[]{"second","minute","hour","day","month","year"};

        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = months / 12;

        ArrayList<Long> times = new ArrayList<>(Arrays.asList(years, months, days, hours, minutes, seconds));

        for(int i = 0; i < times.size(); i++) {
            if(times.get(i) != 0) {
                long value = times.get(i).intValue();

                return value + " " + ids[ids.length - 1 - i] + (value == 1 ? "" : "s") + " ago";
            }
        }

        return "0 seconds ago";
    }
}
