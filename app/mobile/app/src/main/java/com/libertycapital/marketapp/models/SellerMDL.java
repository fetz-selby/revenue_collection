package com.libertycapital.marketapp.models;

import io.realm.RealmList;
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
    private String landmark;
    private String lane;
    private UserMDL createdBy;
    private String createdDate;
    private String modifiedDate;
    private String status;
    private String market;
    private String shopType;
    private String sellerType;
    private RealmList<ContactMDL> contactMDLs;
    private RealmList<PaymentMDL> paymentMDLs;
    private LocationMDL locationMDL;

    public SellerMDL() {

    }

    public SellerMDL(String id, String firstname, String lastname, String otherName, String photo, UserMDL createdBy, String createdDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.otherName = otherName;
        this.photo = photo;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }



    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public RealmList<ContactMDL> getContactMDLs() {
        return contactMDLs;
    }

    public void setContactMDLs(RealmList<ContactMDL> contactMDLs) {
        this.contactMDLs = contactMDLs;
    }

    public LocationMDL getLocationMDL() {
        return locationMDL;
    }

    public void setLocationMDL(LocationMDL locationMDL) {
        this.locationMDL = locationMDL;
    }

    public RealmList<PaymentMDL> getPaymentMDLs() {
        return paymentMDLs;
    }

    public void setPaymentMDLs(RealmList<PaymentMDL> paymentMDLs) {
        this.paymentMDLs = paymentMDLs;
    }

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
        return landmark;
    }

    public void setSection(String landmark) {
        this.landmark = landmark;
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
        return modifiedDate;
    }

    public void setModifiedData(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
