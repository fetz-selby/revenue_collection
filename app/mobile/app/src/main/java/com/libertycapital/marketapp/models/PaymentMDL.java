package com.libertycapital.marketapp.models;

import io.realm.RealmObject;

/**
 * Created by root on 6/8/17.
 */

public class PaymentMDL extends RealmObject {
    private double tax;
    private String dateCreated;
    private String dateModified;
    private UserMDL registeredBy;

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
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


}
