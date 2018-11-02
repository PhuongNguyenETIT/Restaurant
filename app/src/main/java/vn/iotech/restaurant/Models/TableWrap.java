package vn.iotech.restaurant.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableWrap extends ObjectBase{

    @SerializedName("data")
    @Expose
    private List<Table> data = null;

    public List<Table> getData() {
        return data;
    }

    public void setData(List<Table> data) {
        this.data = data;
    }

}