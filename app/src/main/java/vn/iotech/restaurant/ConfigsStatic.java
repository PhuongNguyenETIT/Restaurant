package vn.iotech.restaurant;

import android.content.SharedPreferences;

public class ConfigsStatic {
    public static final String domainWSHome = "ws://13.67.35.142:8682/v1/api/food?resId=";
    public static final String domainWSCategory = "ws://13.67.35.142:8682/v1/api/categories?resId=";
    public static final String domainWSTable = "ws://13.67.35.142:8682/v1/api/table?resId=";
    public static final String domainHttp = "http://13.67.35.142:8681";
    public static final String domainImage = "http://13.67.35.142:8681/photo/";
    public static final String version_domain = "/v1/api";
    public static String token;
    public static SharedPreferences sharedPreferences;
    public static String restaurantID;
    public static Boolean statusAuthenticate;
    public static String idTable;
}
