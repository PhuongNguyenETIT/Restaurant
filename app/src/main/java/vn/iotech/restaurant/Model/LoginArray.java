package vn.iotech.restaurant.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginArray extends Authenticate {

    @SerializedName("data")
    @Expose
    private Login data;

    public Login getData() {
        return data;
    }

    public void setData(Login data) {
        this.data = data;
    }
}
