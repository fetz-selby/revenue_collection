package com.libertycapital.marketapp.models;

import io.realm.RealmObject;

/**
 * Created by root on 6/7/17.
 */

public class LocationMDL extends RealmObject{
    private double longitude;
    private double latitude;
    private String dateCreated;

    public LocationMDL() {

    }

    public LocationMDL(double longitude, double latitude, String dateCreated) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dateCreated = dateCreated;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
