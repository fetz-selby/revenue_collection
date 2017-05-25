package com.libertycapital.marketapp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 5/24/17.
 */

public class ContactMDL extends RealmObject {
    @PrimaryKey
    private String id;
    private String mobileNetwork;
    private String PhoneNumber;
    private boolean isMobileMoney;

    public String getMobileNetwork() {
        return mobileNetwork;
    }

    public void setMobileNetwork(String mobileNetwork) {
        this.mobileNetwork = mobileNetwork;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public boolean isMobileMoney() {
        return isMobileMoney;
    }

    public void setMobileMoney(boolean mobileMoney) {
        isMobileMoney = mobileMoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
