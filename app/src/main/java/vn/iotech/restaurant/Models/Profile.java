package vn.iotech.restaurant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("result")
    @Expose
    private ObjectResultForProfile result;
    @SerializedName("role")
    @Expose
    private String role;

    public ObjectResultForProfile getResult() {
        return result;
    }

    public void setResult(ObjectResultForProfile result) {
        this.result = result;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
