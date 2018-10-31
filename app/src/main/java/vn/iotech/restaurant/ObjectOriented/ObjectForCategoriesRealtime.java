package vn.iotech.restaurant.ObjectOriented;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectForCategoriesRealtime {

    @SerializedName("documentKey")
    @Expose
    private String documentKey;
    @SerializedName("operationType")
    @Expose
    private String operationType;
    @SerializedName("data")
    @Expose
    private List<ObjectDataOfCategories> objectDataOfCategories = null;

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

    public List<ObjectDataOfCategories> getData() {
        return objectDataOfCategories;
    }

    public void setData(List<ObjectDataOfCategories> objectDataOfCategories) {
        this.objectDataOfCategories = objectDataOfCategories;
    }
}