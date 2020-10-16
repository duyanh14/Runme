/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

    public final static long ONE_SECOND = 1000;
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * 24;

    private TimeUtils() {
    }

    /**
     * converts time (in milliseconds) to human-readable format "<w> days, <x>
     * hours, <y> minutes and (z) seconds"
     */
//    public static String millisToLongDHMS(long time) {
//
//        long duration = (Unixtime.getNow() - time) * ONE_SECOND;
//
//        try {
//            StringBuffer res = new StringBuffer();
//            long temp = 0;
//            if (duration >= ONE_SECOND) {
//                long day = 0;
//
//                temp = duration / ONE_DAY;
//                if (temp > 0) {
//                    duration -= temp * ONE_DAY;
//                    res.append(temp).append(" ngày").append(temp > 1 ? "" : "")
//                            .append(duration >= ONE_MINUTE ? " " : "");
//                }
//
//                day = temp;
//
////    
//                temp = duration / ONE_HOUR;
//                if (temp > 0) {
//                    duration -= temp * ONE_HOUR;
//                    res.append(temp).append(" giờ").append(temp > 1 ? "" : "")
//                            .append(duration >= ONE_MINUTE ? " " : "");
//                }
//
//                if (day <= 0) {
//                    temp = duration / ONE_MINUTE;
//                    if (temp > 0) {
//                        duration -= temp * ONE_MINUTE;
//                        res.append(temp).append(" phút").append(temp > 1 ? "" : "");
//                    }
//                }
//
////     
//                if ("".equals(res.toString())) {
//                    return "Vài giây trước";
//                } else {
//                    return res.toString() + " trước";
//                }
//            } else {
//                return "0 giây trước";
//            }
//        } catch (Exception ex) {
//            return "Không thể xác định thời gian";
//
//        } finally {
//        return  Unixtime.toDate(time, "HH:mm dd/MM/yyyy");
//                }
//    }
    public static String millisToLongDHMS(long time) {

        if (String.valueOf(time).length() >= 13) {
            time = time/ 1000;
        }
        
        String _return = null;
        long duration = (Time.Unix.Now() - time) * ONE_SECOND;
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
//                _return = String.valueOf(date.getHours()) + ":" + String.valueOf(date.getMinutes());
                _return = new SimpleDateFormat("HH:mm").format(date);

            } else {
                _return = new SimpleDateFormat("HH:mm dd/MM").format(date);
//                _return = String.valueOf(date.getHours()) + ":" + String.valueOf(date.getMinutes()) + " " + dayx + "/" + monthx;
                if (currentDate.getYear() != date.getYear()) {
//                    _return += "/" + yearx;
                    _return = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(date);
                }
            }

//            }
        } catch (Exception ex) {
            _return = "Unknown";
        }
        return _return;
    }

}
