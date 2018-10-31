package vn.iotech.restaurant.ObjectOriented;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectForLogin {

    @SerializedName("auth")
    @Expose
    private Boolean auth;
    @SerializedName("token")
    @Expose
    private String token;

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
