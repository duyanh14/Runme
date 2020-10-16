/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

/**
 *
 * @author Duy
 */
public class HaversineAlgorithm {

    static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);

    public static void main(String[] args) {
        System.out.println(HaversineInKM("21.016233, 105.836494", "21.018679, 105.775880"));
    }

    public static int HaversineInM(String c1, String c2) {
        c1 = c1.replace(" ", "");
        c2 = c2.replace(" ", "");

        String[] sc1 = c1.split(",");
        String[] sc2 = c2.split(",");

        return (int) (1000D * HaversineInKM(Double.valueOf(sc1[0]), Double.valueOf(sc1[1]), Double.valueOf(sc2[0]), Double.valueOf(sc2[1])));
    }

    public static double HaversineInKM(String c1, String c2) {
        c1 = c1.replace(" ", "");
        c2 = c2.replace(" ", "");

        String[] sc1 = c1.split(",");
        String[] sc2 = c2.split(",");

        return HaversineInKM(Double.valueOf(sc1[0]), Double.valueOf(sc1[1]), Double.valueOf(sc2[0]), Double.valueOf(sc2[1]));
    }

    public static int HaversineInM(double lat1, double long1, double lat2, double long2) {
        return (int) (1000D * HaversineInKM(lat1, long1, lat2, long2));
    }

    public static double HaversineInKM(double lat1, double long1, double lat2, double long2) {
        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;

        return d;
    }

}
