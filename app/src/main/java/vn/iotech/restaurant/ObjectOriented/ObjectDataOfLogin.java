package vn.iotech.restaurant.ObjectOriented;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ObjectDataOfLogin extends DataGetFromServer {

    @SerializedName("data")
    @Expose
    private List<ObjectForLogin> objectForLogin = null;

    public List<ObjectForLogin> getData() {
        return objectForLogin;
    }

    public void setData(List<ObjectForLogin> objectForLogin) {
        this.objectForLogin = objectForLogin;
    }
}
