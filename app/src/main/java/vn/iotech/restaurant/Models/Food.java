package vn.iotech.restaurant.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("people")
    @Expose
    private Integer people;
    @SerializedName("isHome")
    @Expose
    private Boolean isHome;
    @SerializedName("isTrash")
    @Expose
    private Boolean isTrash;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_restaurantId")
    @Expose
    private String restaurantId;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("unitPrice")
    @Expose
    private String unitPrice;
    @SerializedName("during")
    @Expose
    private Integer during;
    @SerializedName("_createTime")
    @Expose
    private String createTime;
    @SerializedName("_updateTime")
    @Expose
    private String updateTime;

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public Boolean getIsHome() {
        return isHome;
    }

    public void setIsHome(Boolean isHome) {
        this.isHome = isHome;
    }

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

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getDuring() {
        return during;
    }

    public void setDuring(Integer during) {
        this.during = during;
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
