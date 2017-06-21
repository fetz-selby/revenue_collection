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
    private String surname;
    private String otherName;
    private String photo;
    private String photoUri;
    private String businessType;
    private String landmark;
    private UserMDL createdBy;
    private String createdDate;
    private String modifiedDate;
    private String status;
    private String market;
    private String shopType;
    private String type;
    private String sellerCategory;
    private boolean iSellingOnTheFloor;
    private boolean isSellingOntheTable;
    private String mobileNetwork;
    private String phone;
    private boolean isMobileMoney;
    private boolean hasSynced;

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

    private double longitude;
    private double latitude;
    private LocationMDL locationMDL;

    public SellerMDL() {

    }


    public SellerMDL(String id, String firstname, String surname, String otherName, String photo, UserMDL createdBy, String createdDate) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.otherName = otherName;
        this.photo = photo;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public boolean isHasSynced() {
        return hasSynced;
    }

    public void setHasSynced(boolean hasSynced) {
        this.hasSynced = hasSynced;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean iSellingOnTheFloor() {
        return iSellingOnTheFloor;
    }

    public void setiSellingOnTheFloor(boolean iSellingOnTheFloor) {
        this.iSellingOnTheFloor = iSellingOnTheFloor;
    }

    public boolean isSellingOntheTable() {
        return isSellingOntheTable;
    }

    public void setSellingOntheTable(boolean sellingOntheTable) {
        isSellingOntheTable = sellingOntheTable;
    }

    public String getSellerCategory() {
        return sellerCategory;
    }

    public void setSellerCategory(String sellerCategory) {
        this.sellerCategory = sellerCategory;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getSellerType() {
        return type;
    }

    public void setSellerType(String type) {
        this.type = type;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public LocationMDL getLocationMDL() {
        return locationMDL;
    }

    public void setLocationMDL(LocationMDL locationMDL) {
        this.locationMDL = locationMDL;
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

    public String getSection() {
        return landmark;
    }

    public void setSection(String landmark) {
        this.landmark = landmark;
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

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getMobileNetwork() {
        return mobileNetwork;
    }

    public void setMobileNetwork(String mobileNetwork) {
        this.mobileNetwork = mobileNetwork;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isMobileMoney() {
        return isMobileMoney;
    }

    public void setMobileMoney(boolean mobileMoney) {
        isMobileMoney = mobileMoney;
    }


}
