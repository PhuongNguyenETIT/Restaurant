package vn.iotech.restaurant.Retrofit2;

import vn.iotech.restaurant.ConfigsStatic;

public class APIRetrofitUtils {

    public static RetrofitDataClient getData(){
        return RetrofitClient.getClient(ConfigsStatic.domainHttp).create(RetrofitDataClient.class);
    }
}
