package vn.iotech.restaurant.Model;

public class ObjectForRecyclerViewInCart extends ObjectForRecyclerViewHome {
    int Amout;

    public ObjectForRecyclerViewInCart(int imageFoods, String detialed, int time, int person, double money, int amout) {
        super(imageFoods, detialed, time, person, money);
        Amout = amout;
    }

    public int getAmout() {
        return Amout;
    }

    public void setAmout(int amout) {
        Amout = amout;
    }
}
