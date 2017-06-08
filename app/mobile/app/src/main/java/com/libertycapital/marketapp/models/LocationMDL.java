package com.libertycapital.marketapp.models;

import io.realm.RealmObject;

/**
 * Created by root on 6/7/17.
 */

class LocationMDL extends RealmObject{
    private double longitude;
    private double latitude;
    private String dateCreated;

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
