import java.util.ArrayList;

public class Hotel {

    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    private int reservationCounter = 1001;

    public Hotel() {

        rooms = new ArrayList<>();
        reservations = new ArrayList<>();

        // Add hotel rooms
        rooms.add(new Room(101, "Single", 1500));
        rooms.add(new Room(102, "Double", 2500));
        rooms.add(new Room(103, "Deluxe", 4000));
        rooms.add(new Room(104, "Suite", 6000));
        rooms.add(new Room(105, "Suite", 6000));
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    // Create reservation
    public Reservation makeReservation(
            String guestName,
            Room room,
            int nights) {

        if (room == null || !room.isAvailable()) {
            return null;
        }

        String reservationId =
                "RES" + reservationCounter++;

        Reservation reservation =
                new Reservation(
                        reservationId,
                        guestName,
                        room,
                        nights
                );

        reservations.add(reservation);

        room.setAvailable(false);

        return reservation;
    }

    // Find reservation
    public Reservation findReservation(String id) {

        for (Reservation reservation : reservations) {

            if (reservation.getReservationId()
                    .equalsIgnoreCase(id)) {

                return reservation;
            }
        }

        return null;
    }

    // Cancel reservation
    public boolean cancelReservation(String id) {

        Reservation reservation =
                findReservation(id);

        if (reservation != null) {

            reservation.getRoom().setAvailable(true);

            reservations.remove(reservation);

            return true;
        }

        return false;
    }
}