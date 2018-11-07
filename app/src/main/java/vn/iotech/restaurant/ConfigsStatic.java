package vn.iotech.restaurant;

import android.content.SharedPreferences;

public class ConfigsStatic {
    public static final String baseDomainWS = "ws://13.67.35.142:8682";
    public static final String version_domain = "/v1/api";
    public static final String domainWSHome = baseDomainWS + version_domain + "/food?resId=";
    public static final String domainWSCategory = baseDomainWS + version_domain + "/categories?resId=";
    public static final String domainWSTable = baseDomainWS + version_domain + "/table?resId=";
    public static final String domainWSRestaurant = baseDomainWS + version_domain + "/restaurant?resId=";
    public static final String domainHttp = "http://13.67.35.142:8681";
    public static final String domainImage = "http://13.67.35.142:8681/photo/";
    public static String token;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences preferencesCart;
    public static String restaurantID;
    public static Boolean statusAuthenticate;
    public static String idTable;
    public static String nameTableConfig;
}
