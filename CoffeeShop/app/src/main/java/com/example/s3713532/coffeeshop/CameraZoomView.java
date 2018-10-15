package com.example.s3713532.coffeeshop;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s3713532 on 4/21/18.
 */

public class CameraZoomView {

    private GoogleMap mMap;
    private LatLngBounds currBounds;
    //private double prevX, prevY;

    public CameraZoomView (GoogleMap mMap) {
        this.mMap = mMap;
        //this.prevBounds = null;
        this.currBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
//        this.prevX = 0;
//        this.prevY = 0;
    }

//    public LatLngBounds getCurrBounds() {
//        return currBounds;
//    }
//
//    public void update() {
//        prevX = currBounds.southwest.latitude;
//        prevY = currBounds.northeast.longitude;
//        currBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
//    }

    // Find shops within the current camera zoom level
    public List<Shop> findShopsWithinZoomLevel (List<Shop> nearbyShopList) {

        double x1 = currBounds.southwest.latitude;
        double x2 = currBounds.northeast.latitude;
        double y1 = currBounds.northeast.longitude;
        double y2 = currBounds.southwest.longitude;

        List<Shop> shopsWithinZoomLevel = new ArrayList<>();

        for (Shop shop : nearbyShopList) {
            double shopLat = shop.getLat();
            double shopLon = shop.getLon();
            if (shopLat >= x1 && shopLat <= x2 && shopLon <= y1 && shopLon >= y2) {
                shopsWithinZoomLevel.add(shop);
            }
        }
        return shopsWithinZoomLevel;
    }
//
//    // Check if the camera view has changed
//    public boolean isCameraViewChange () {
//        double currX = currBounds.southwest.latitude;
//        double currY = currBounds.northeast.longitude;
//
//        if (currX != prevX || currY != prevY) {
//            prevX = currX;
//            prevY = currY;
//            return true;
//        }
//        return false;
//    }


    // Calculate the distance between two locations
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
