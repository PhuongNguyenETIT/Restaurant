package vn.iotech.restaurant.Retrofit2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.iotech.restaurant.Models.LoginArray;
import vn.iotech.restaurant.Models.ProfileArray;

public interface RetrofitDataClient {

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<LoginArray> getDataLogin(@Body Object login);

    @Headers("Content-Type: application/json")
    @GET("/api/user/profile")
    Call<ProfileArray> getProfile(@Header("Authorization") String token);
}
