package com.libertycapital.marketapp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 5/24/17.
 */


public class BusinessMDL extends RealmObject {
    @PrimaryKey
    private String id;
    private String businessType;
    private String bussinessCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBussinessCategory() {
        return bussinessCategory;
    }

    public void setBussinessCategory(String bussinessCategory) {
        this.bussinessCategory = bussinessCategory;
    }


}
