package vn.iotech.restaurant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestWrap extends Authenticate {

    @SerializedName("data")
    @Expose
    private BaseRequest data;

    public BaseRequest getData() {
        return data;
    }

    public void setData(BaseRequest data) {
        this.data = data;
    }
}
