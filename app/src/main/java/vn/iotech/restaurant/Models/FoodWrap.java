package vn.iotech.restaurant.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodWrap extends ObjectBase{

    @SerializedName("data")
    @Expose
    private List<Food> data = null;

    public List<Food> getData() {
        return data;
    }

    public void setData(List<Food> data) {
        this.data = data;
    }
}
