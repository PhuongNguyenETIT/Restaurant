package vn.iotech.restaurant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreferencesCart {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("unitPrice")
    @Expose
    private String unitPrice;
    @SerializedName("during")
    @Expose
    private Integer during;
    @SerializedName("people")
    @Expose
    private Integer people;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

}