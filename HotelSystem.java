package hotel;

import java.io.*;
import java.util.*;

public class HotelSystem {
    static List<Room> rooms = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadRooms();
        menu();
    }

    static void menu() {
        while (true) {
            System.out.println("\n--- Hotel Booking System ---");
            System.out.println("1. Book Room");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Bookings");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    bookRoom();
                    break;
                case "2":
                    cancelBooking();
                    break;
                case "3":
                    viewBookings();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void loadRooms() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/rooms.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Room room = new Room(parts[0], parts[1], Boolean.parseBoolean(parts[2]));
                rooms.add(room);
            }
        } catch (IOException e) {
            System.out.println("Error loading rooms.");
        }
    }

    static void saveRooms() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/rooms.txt"))) {
            for (Room room : rooms) {
                writer.write(room.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving rooms.");
        }
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter room category (Standard/Deluxe/Suite): ");
        String category = scanner.nextLine();

        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                room.setAvailable(false);
                String bookingId = "BKG" + new Random().nextInt(1000, 9999);
                Booking booking = new Booking(bookingId, name, room);
                saveBooking(booking);
                saveRooms();
                System.out.println("✅ Booking Successful! Booking ID: " + bookingId);
                simulatePayment();
                return;
            }
        }
        System.out.println("❌ No available room in this category.");
    }

    static void simulatePayment() {
        System.out.println("💳 Simulating payment...");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("✅ Payment successful!");
    }

    static void saveBooking(Booking booking) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/bookings.txt", true))) {
            writer.write(booking.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking.");
        }
    }

    static void cancelBooking() {
        System.out.print("Enter booking ID to cancel: ");
        String bookingId = scanner.nextLine();
        List<String> updatedBookings = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("data/bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(bookingId + ",")) {
                    String[] parts = line.split(",");
                    String roomNum = parts[2];
                    for (Room room : rooms) {
                        if (room.getNumber().equals(roomNum)) {
                            room.setAvailable(true);
                            break;
                        }
                    }
                    found = true;
                } else {
                    updatedBookings.add(line);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/bookings.txt"))) {
                for (String updated : updatedBookings) {
                    writer.write(updated);
                    writer.newLine();
                }
            }

            saveRooms();

            if (found) {
                System.out.println("✅ Booking cancelled.");
            } else {
                System.out.println("❌ Booking ID not found.");
            }

        } catch (IOException e) {
            System.out.println("Error canceling booking.");
        }
    }

    static void viewBookings() {
        System.out.println("\n📖 Booking Details:");
        try (BufferedReader reader = new BufferedReader(new FileReader("data/bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No bookings found.");
        }
    }
}

