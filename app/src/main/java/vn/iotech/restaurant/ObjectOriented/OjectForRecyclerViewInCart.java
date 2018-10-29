package vn.iotech.restaurant.ObjectOriented;

public class OjectForRecyclerViewInCart extends OjectForRecyclerViewHome {
    int Amout;

    public OjectForRecyclerViewInCart(int imageFoods, String detialed, int time, int person, double money, int amout) {
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
