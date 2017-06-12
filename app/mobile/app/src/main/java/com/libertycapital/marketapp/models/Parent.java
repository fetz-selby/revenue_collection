package com.libertycapital.marketapp.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by root on 6/11/17.
 */

public class Parent extends RealmObject {
    @SuppressWarnings("unused")
    private RealmList<Counter> counterList;

    public RealmList<Counter> getCounterList() {
        return counterList;
    }
}