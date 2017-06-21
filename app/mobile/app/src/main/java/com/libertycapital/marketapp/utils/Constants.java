package com.libertycapital.marketapp.utils;

/**
 * Created by SOVAVY on 4/25/2017.
 */

public class Constants {


    public static final String EMPTY_STRING = "";
    public static final String VOLLEY_GET_TAG = "VOLLEY_GET_TAG";
    public static final String url ="https://revenue-api.herokuapp.com";
    public static final String SELLERS_URL =  url + "/eghana/revenue/api/sellers";
    public static final String PAYMENT_URL = url + "/eghana/revenue/api/payments";
    public static final String LOCAL_PROPERTY_URL = url+ "/eghana/esurvey/api/property";
    public static final String LOGIN_URL = "https://esurvey-api.herokuapp.com/eghana/esurvey/api/auth/agents/login";
    public static final String DISTRICT_URL = "https://esurvey-api.herokuapp.com/eghana/esurvey/api/districts";
    public static final String REGION_URL = "https://esurvey-api.herokuapp.com/eghana/esurvey/api/regions";
    public static final String PROPERTIES_URL = "https://esurvey-api.herokuapp.com/eghana/esurvey/api/property";
    public static final String PREFS = "prefs";
    public static final String prefStringDefaultValue = "";
    public static final int prefIntDefualtValue = 0;
    public static final boolean prefBooleanDefault = false;
    public static String loadingDialogMsg;
    public static String serverAddress;
    public static int noNetworkMsg;
    public static String prefsLogin= "prefslogin";
}