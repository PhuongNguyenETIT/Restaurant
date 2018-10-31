package vn.iotech.restaurant.Retrofit2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.iotech.restaurant.ObjectOriented.ObjectDataOfLogin;

public interface RetrofitDataClient {

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<ObjectDataOfLogin> getDataLogin(@Body Object login);
}
