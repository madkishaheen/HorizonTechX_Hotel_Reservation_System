public class Reservation {

    private String reservationId;
    private String guestName;
    private Room room;
    private int numberOfNights;

    public Reservation(
            String reservationId,
            String guestName,
            Room room,
            int numberOfNights) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.room = room;
        this.numberOfNights = numberOfNights;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getTotalAmount() {
        return room.getPrice() * numberOfNights;
    }

    public String getDetails() {

        return "Reservation ID : " + reservationId +
                "\nGuest Name     : " + guestName +
                "\nRoom Number    : " + room.getRoomNumber() +
                "\nRoom Type      : " + room.getRoomType() +
                "\nNights         : " + numberOfNights +
                "\nPrice/Night    : ₹" + room.getPrice() +
                "\nTotal Amount   : ₹" + getTotalAmount();
    }
}