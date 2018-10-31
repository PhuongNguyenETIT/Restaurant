package vn.iotech.restaurant.Models;

public class ObjectForRecyclerViewInVoice {
    private int NumberNo;
    private String NameRestaurant;
    private int Quality;
    private double Price;

    public ObjectForRecyclerViewInVoice(int numberNo, String nameRestaurant, int quality, double price) {
        NumberNo = numberNo;
        NameRestaurant = nameRestaurant;
        Quality = quality;
        Price = price;
    }

    public int getNumberNo() {
        return NumberNo;
    }

    public void setNumberNo(int numberNo) {
        NumberNo = numberNo;
    }

    public String getNameRestaurant() {
        return NameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        NameRestaurant = nameRestaurant;
    }

    public int getQuality() {
        return Quality;
    }

    public void setQuality(int quality) {
        Quality = quality;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
