package vn.iotech.restaurant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailFoodWrap extends Authenticate {

    @SerializedName("data")
    @Expose
    private DetailFood data;

    public DetailFood getData() {
        return data;
    }

    public void setData(DetailFood data) {
        this.data = data;
    }
}
