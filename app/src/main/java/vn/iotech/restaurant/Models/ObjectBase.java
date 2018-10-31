package vn.iotech.restaurant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectBase {

    @SerializedName("documentKey")
    @Expose
    private String documentKey;
    @SerializedName("operationType")
    @Expose
    private String operationType;

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
