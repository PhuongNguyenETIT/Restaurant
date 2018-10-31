package vn.iotech.restaurant.ObjectOriented;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectDataOfCategories {

    @SerializedName("isTrash")
    @Expose
    private Boolean isTrash;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_restaurantId")
    @Expose
    private String restaurantId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("orderby")
    @Expose
    private Integer orderby;
    @SerializedName("_createTime")
    @Expose
    private String createTime;
    @SerializedName("_updateTime")
    @Expose
    private String updateTime;

    public Boolean getIsTrash() {
        return isTrash;
    }

    public void setIsTrash(Boolean isTrash) {
        this.isTrash = isTrash;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
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
