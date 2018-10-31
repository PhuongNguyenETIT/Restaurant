package vn.iotech.restaurant.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryArray extends ObjectBase{

    @SerializedName("data")
    @Expose
    private List<Category> objectDataOfCategories = null;

    public List<Category> getData() {
        return objectDataOfCategories;
    }

    public void setData(List<Category> objectDataOfCategories) {
        this.objectDataOfCategories = objectDataOfCategories;
    }
}