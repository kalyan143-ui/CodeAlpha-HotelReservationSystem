package hotel;

public class Booking {
    private String id;
    private String customerName;
    private Room room;

    public Booking(String id, String customerName, Room room) {
        this.id = id;
        this.customerName = customerName;
        this.room = room;
    }

    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public Room getRoom() { return room; }

    @Override
    public String toString() {
        return id + "," + customerName + "," + room.getNumber() + "," + room.getCategory();
    }
}

