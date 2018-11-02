package vn.iotech.restaurant;

import android.content.SharedPreferences;

public class ConfigsStatic {
    public static String domainWSHome = "ws://13.67.35.142:8682/v1/api/food?resId=";
    public static String domainWSCategory = "ws://13.67.35.142:8682/v1/api/categories?resId=";
    public static String domainWSTable = "ws://13.67.35.142:8682/v1/api/table?resId=";
    public static String domainHttp = "http://13.67.35.142:8681/v1/api";
    public static final String domainImage = "http://13.67.35.142:8681/photo/";
    public static SharedPreferences sharedPreferences;
    public static String restaurantID;
    public static Boolean statusAuthenticate;
    public static int numberTable;
}
