package vn.iotech.restaurant.Retrofit2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.iotech.restaurant.ConfigsStatic;
import vn.iotech.restaurant.Models.FoodWrap;
import vn.iotech.restaurant.Models.LoginWrap;
import vn.iotech.restaurant.Models.ProfileWrap;
import vn.iotech.restaurant.Models.RequestWrap;

public interface RetrofitDataClient {

    @Headers("Content-Type: application/json")
    @POST(ConfigsStatic.version_domain + "/login")
    Call<LoginWrap> getDataLogin(@Body Object login);

    @Headers("Content-Type: application/json")
    @GET(ConfigsStatic.version_domain + "/user/profile")
    Call<ProfileWrap> getProfile(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @GET(ConfigsStatic.version_domain + "/food/bycategory/{id}")
    Call<FoodWrap> getCategoryFood(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @PUT(ConfigsStatic.version_domain + "/table/setting/update")
    Call<RequestWrap> settingTable(@Header("Authorization") String idRes, @Body Object data);
}
