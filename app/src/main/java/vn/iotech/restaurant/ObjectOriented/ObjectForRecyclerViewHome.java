package vn.iotech.restaurant.ObjectOriented;

public class ObjectForRecyclerViewHome {
    private int ImageFoods;
    private String Detailed;
    private int Time;
    private int Person;
    private double Money;

    public ObjectForRecyclerViewHome(int imageFoods, String detialed, int time, int person, double money) {
        ImageFoods = imageFoods;
        Detailed = detialed;
        Time = time;
        Person = person;
        Money = money;
    }

    public int getImageFoods() {
        return ImageFoods;
    }

    public void setImageFoods(int imageFoods) {
        ImageFoods = imageFoods;
    }

    public String getDetailed() {
        return Detailed;
    }

    public void setDetailed(String detialed) {
        Detailed = detialed;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public int getPerson() {
        return Person;
    }

    public void setPerson(int person) {
        Person = person;
    }

    public double getMoney() {
        return Money;
    }

    public void setMoney(double money) {
        Money = money;
    }
}
