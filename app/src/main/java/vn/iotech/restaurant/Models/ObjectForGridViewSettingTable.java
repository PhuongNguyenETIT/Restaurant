package vn.iotech.restaurant.Models;

public class ObjectForGridViewSettingTable {
    private String NameTable;
    private int BackgroundTable;
    private int ImageBooking;

    public ObjectForGridViewSettingTable(String nameTable, int backgroundTable, int imageBooking) {
        NameTable = nameTable;
        BackgroundTable = backgroundTable;
        ImageBooking = imageBooking;
    }

    public String getNameTable() {
        return NameTable;
    }

    public void setNameTable(String nameTable) {
        NameTable = nameTable;
    }

    public int getBackgroundTable() {
        return BackgroundTable;
    }

    public void setBackgroundTable(int iconTable) {
        BackgroundTable = iconTable;
    }

    public int getImageBooking() {
        return ImageBooking;
    }

    public void setImageBooking(int imageBooking) {
        ImageBooking = imageBooking;
    }
}
