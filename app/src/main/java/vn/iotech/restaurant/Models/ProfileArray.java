package vn.iotech.restaurant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileArray extends Authenticate {

    @SerializedName("data")
    @Expose
    private Profile objectDataOfProfile;

    public Profile getObjectDataOfProfile() {
        return objectDataOfProfile;
    }

    public void setObjectDataOfProfile(Profile objectDataOfProfile) {
        this.objectDataOfProfile = objectDataOfProfile;
    }
}
