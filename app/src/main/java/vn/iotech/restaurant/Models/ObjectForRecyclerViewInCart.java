package vn.iotech.restaurant.Models;

public class ObjectForRecyclerViewInCart extends ObjectForRecyclerViewHome {
    int Amount;
    String UnitPrice;

    public ObjectForRecyclerViewInCart(int imageFoods, String detialed, int time,
                                       int person, double money, int amount, String unitPrice) {
        super(imageFoods, detialed, time, person, money);
        Amount = amount;
        UnitPrice = unitPrice;
    }

    public int getAmout() {
        return Amount;

    }

    public void setAmout(int amout) {
        Amount = amout;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }
}
