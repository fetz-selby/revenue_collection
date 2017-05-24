package com.libertycapital.marketapp.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 5/23/17.
 */

public class UserMDL extends RealmObject {
    @SerializedName("id")
    @PrimaryKey
    private String id;
    @SerializedName("_id")
    private String webId;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("surname")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("district")
    private String districtMDL;
    private String dateAdded;
    private String dateModified;

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateMddified) {
        this.dateModified = dateMddified;
    }


    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistrictMDL() {
        return districtMDL;
    }

    public void setDistrictMDL(String districtMDL) {
        this.districtMDL = districtMDL;
    }


}
