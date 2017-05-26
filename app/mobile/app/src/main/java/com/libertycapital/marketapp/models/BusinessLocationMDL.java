package com.libertycapital.marketapp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 5/24/17.
 */

public class BusinessLocationMDL  extends RealmObject{

    @PrimaryKey
    private String id;
    private String Section;
    private String lane;
    private String landmark;
    private String longitude;
    private String latidude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatidude() {
        return latidude;
    }

    public void setLatidude(String latidude) {
        this.latidude = latidude;
    }
}
