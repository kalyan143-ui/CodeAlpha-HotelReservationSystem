package hotel;

public class Room {
    private String number;
    private String category;
    private boolean isAvailable;

    public Room(String number, String category, boolean isAvailable) {
        this.number = number;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public String getNumber() { return number; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return number + "," + category + "," + isAvailable;
    }
}

