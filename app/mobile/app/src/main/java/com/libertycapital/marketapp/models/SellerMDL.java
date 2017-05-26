package com.libertycapital.marketapp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 5/24/17.
 */

public class SellerMDL extends RealmObject {

    @PrimaryKey
    private String id;
    private String firstname;
    private String lastname;
    private String otherName;
    private String photo;
    private String businessType;
    private String businessCategory;
    private String section;
    private String lane;
    private ContactMDL contactMDL;
    private UserMDL createdBy;
    private String createdDate;
    private String modifiedData;
    private String status;

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public UserMDL getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMDL createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedData() {
        return modifiedData;
    }

    public void setModifiedData(String modifiedData) {
        this.modifiedData = modifiedData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public ContactMDL getContactMDL() {
        return contactMDL;
    }

    public void setContactMDL(ContactMDL contactMDL) {
        this.contactMDL = contactMDL;
    }


    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBussinessCategory() {
        return businessCategory;
    }

    public void setBussinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


}
