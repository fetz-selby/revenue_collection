package com.libertycapital.marketapp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 6/8/17.
 */

public class PaymentMDL extends RealmObject {


    @PrimaryKey
    private String id;
    private String paymentType;
    private float amount;
    private SellerMDL sellerMDL;
    private String dateCreated;
    private String dateModified;
    private UserMDL registeredBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public UserMDL getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(UserMDL registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public SellerMDL getSellerMDL() {
        return sellerMDL;
    }

    public void setSellerMDL(SellerMDL sellerMDL) {
        this.sellerMDL = sellerMDL;
    }


}
