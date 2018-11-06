package vn.iotech.restaurant.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantWrap extends ObjectBase {

    @SerializedName("data")
    @Expose
    private List<Restaurant> data = null;

    public List<Restaurant> getData() {
        return data;
    }

    public void setData(List<Restaurant> data) {
        this.data = data;
    }
}
