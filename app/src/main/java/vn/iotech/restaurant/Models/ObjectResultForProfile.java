package vn.iotech.restaurant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectResultForProfile {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_restaurantId")
    @Expose
    private String restaurantId;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("avartar")
    @Expose
    private String avartar;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("_createTime")
    @Expose
    private String createTime;
    @SerializedName("_updateTime")
    @Expose
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
