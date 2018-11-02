package vn.iotech.restaurant.Retrofit2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.iotech.restaurant.Models.FoodWrap;
import vn.iotech.restaurant.Models.LoginWrap;
import vn.iotech.restaurant.Models.ProfileWrap;

public interface RetrofitDataClient {

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<LoginWrap> getDataLogin(@Body Object login);

    @Headers("Content-Type: application/json")
    @GET("/user/profile")
    Call<ProfileWrap> getProfile(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @GET("/food/bycategory/{id}")
    Call<FoodWrap> getCategoryFood(@Path("id") String id);
}
