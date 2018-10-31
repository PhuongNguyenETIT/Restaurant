package vn.iotech.restaurant.ObjectOriented;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataGetFromServer {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}