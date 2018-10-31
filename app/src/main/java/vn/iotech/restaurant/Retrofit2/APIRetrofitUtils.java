package vn.iotech.restaurant.Retrofit2;

public class APIRetrofitUtils {

    public static final String BaseURL = "http://13.67.35.142:8681";

    public static RetrofitDataClient getData(){
        return RetrofitClient.getClient(BaseURL).create(RetrofitDataClient.class);
    }
}
