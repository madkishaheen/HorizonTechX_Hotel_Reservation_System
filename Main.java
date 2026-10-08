import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private Hotel hotel;

    private JTextArea displayArea;

    public Main() {

        hotel = new Hotel();

        // Window settings
        setTitle("Grand Horizon Hotel - Reservation System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
    }

    private void createGUI() {

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // =========================
        // HEADER
        // =========================

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(30, 60, 90));
        header.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel hotelName =
                new JLabel("GRAND HORIZON HOTEL");

        hotelName.setFont(
                new Font("Arial", Font.BOLD, 30)
        );

        hotelName.setForeground(Color.WHITE);
        hotelName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle =
                new JLabel("Hotel Reservation Management System");

        subtitle.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(hotelName);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(new GridLayout(2, 3, 15, 15));

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        buttonPanel.setBackground(
                new Color(245, 247, 250)
        );

        JButton roomsButton =
                createButton("View Available Rooms");

        JButton bookButton =
                createButton("Make Reservation");

        JButton reservationsButton =
                createButton("View Reservations");

        JButton searchButton =
                createButton("Search Reservation");

        JButton cancelButton =
                createButton("Cancel Reservation");

        JButton exitButton =
                createButton("Exit");

        buttonPanel.add(roomsButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(reservationsButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(exitButton);

        // =========================
        // DISPLAY AREA
        // =========================

        displayArea = new JTextArea();

        displayArea.setEditable(false);
        displayArea.setFont(
                new Font("Monospaced", Font.PLAIN, 15)
        );

        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);

        displayArea.setText(
                "\n\n" +
                "        Welcome to Grand Horizon Hotel!\n\n" +
                "        Please select an option above.\n"
        );

        JScrollPane scrollPane =
                new JScrollPane(displayArea);

        // =========================
        // STATUS BAR
        // =========================

        JLabel status =
                new JLabel(
                        "  Status: Ready"
                );

        status.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        status.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 8, 8, 8
                )
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        roomsButton.addActionListener(e ->
                showAvailableRooms()
        );

        bookButton.addActionListener(e ->
                makeReservation()
        );

        reservationsButton.addActionListener(e ->
                showReservations()
        );

        searchButton.addActionListener(e ->
                searchReservation()
        );

        cancelButton.addActionListener(e ->
                cancelReservation()
        );

        exitButton.addActionListener(e -> {

            int answer =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to exit?",
                            "Exit",
                            JOptionPane.YES_NO_OPTION
                    );

            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // =========================
        // ADD COMPONENTS
        // =========================

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.SOUTH
        );

        // Better size for display area
        scrollPane.setPreferredSize(
                new Dimension(800, 220)
        );

        add(mainPanel);

        add(
                status,
                BorderLayout.SOUTH
        );
    }

    // =========================
    // CREATE BUTTON
    // =========================

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        button.setFocusPainted(false);

        return button;
    }

    // =========================
    // VIEW AVAILABLE ROOMS
    // =========================

    private void showAvailableRooms() {

        StringBuilder result =
                new StringBuilder();

        result.append(
                "================ AVAILABLE ROOMS ================\n\n"
        );

        boolean found = false;

        for (Room room : hotel.getRooms()) {

            if (room.isAvailable()) {

                result.append(
                        "Room "
                        + room.getRoomNumber()
                        + " | "
                        + room.getRoomType()
                        + " | ₹"
                        + room.getPrice()
                        + " per night\n"
                );

                found = true;
            }
        }

        if (!found) {

            result.append(
                    "No rooms are currently available."
            );
        }

        displayArea.setText(
                result.toString()
        );
    }

    // =========================
    // MAKE RESERVATION
    // =========================

    private void makeReservation() {

        JTextField nameField =
                new JTextField();

        JTextField nightsField =
                new JTextField();

        JComboBox<Room> roomBox =
                new JComboBox<>();

        for (Room room : hotel.getRooms()) {

            if (room.isAvailable()) {

                roomBox.addItem(room);
            }
        }

        if (roomBox.getItemCount() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No rooms are currently available.",
                    "No Rooms",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JPanel panel = new JPanel(
                new GridLayout(0, 2, 10, 10)
        );

        panel.add(
                new JLabel("Guest Name:")
        );

        panel.add(nameField);

        panel.add(
                new JLabel("Select Room:")
        );

        panel.add(roomBox);

        panel.add(
                new JLabel("Number of Nights:")
        );

        panel.add(nightsField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Make Reservation",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String guestName =
                nameField.getText().trim();

        if (guestName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter guest name."
            );

            return;
        }

        int nights;

        try {

            nights = Integer.parseInt(
                    nightsField.getText().trim()
            );

            if (nights <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number of nights."
            );

            return;
        }

        Room selectedRoom =
                (Room) roomBox.getSelectedItem();

        Reservation reservation =
                hotel.makeReservation(
                        guestName,
                        selectedRoom,
                        nights
                );

        if (reservation != null) {

            displayArea.setText(
                    "================ RESERVATION CONFIRMED ================\n\n"
                    + reservation.getDetails()
                    + "\n\n=========================================================="
                    + "\nThank you for choosing Grand Horizon Hotel!"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation created successfully!\n"
                    + "Reservation ID: "
                    + reservation.getReservationId(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =========================
    // VIEW RESERVATIONS
    // =========================

    private void showReservations() {

        StringBuilder result =
                new StringBuilder();

        result.append(
                "================ ALL RESERVATIONS ================\n\n"
        );

        if (hotel.getReservations().isEmpty()) {

            result.append(
                    "No reservations found."
            );

        } else {

            for (Reservation reservation :
                    hotel.getReservations()) {

                result.append(
                        reservation.getDetails()
                );

                result.append(
                        "\n--------------------------------------------------\n"
                );
            }
        }

        displayArea.setText(
                result.toString()
        );
    }

    // =========================
    // SEARCH RESERVATION
    // =========================

    private void searchReservation() {

        String id =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Reservation ID:"
                );

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        Reservation reservation =
                hotel.findReservation(
                        id.trim()
                );

        if (reservation != null) {

            displayArea.setText(
                    "================ RESERVATION FOUND ================\n\n"
                    + reservation.getDetails()
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation not found.",
                    "Search Result",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // =========================
    // CANCEL RESERVATION
    // =========================

    private void cancelReservation() {

        String id =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Reservation ID to cancel:"
                );

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        Reservation reservation =
                hotel.findReservation(
                        id.trim()
                );

        if (reservation == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Cancel reservation "
                        + reservation.getReservationId()
                        + " for "
                        + reservation.getGuestName()
                        + "?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm == JOptionPane.YES_OPTION) {

            boolean cancelled =
                    hotel.cancelReservation(
                            id.trim()
                    );

            if (cancelled) {

                displayArea.setText(
                        "================ RESERVATION CANCELLED ================\n\n"
                        + "Reservation ID: "
                        + id.toUpperCase()
                        + "\nGuest: "
                        + reservation.getGuestName()
                        + "\n\nThe room is now available again."
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Reservation cancelled successfully!"
                );
            }
        }
    }

    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Main app = new Main();

            app.setVisible(true);
        });
    }
}