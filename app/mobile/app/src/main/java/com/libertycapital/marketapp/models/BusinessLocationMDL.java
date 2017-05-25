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
}
