
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class RailwayReservationGUI {

    static ArrayList<Train> trains = new ArrayList<>();
    static ArrayList<Ticket> tickets = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize trains
        trains.add(new Train(12345, "Shatabdi Express", "Bangalore", "Chennai", 50, 450.0));
        trains.add(new Train(12346, "Rajdhani Express", "Delhi", "Mumbai", 60, 1200.0));
        trains.add(new Train(12347, "Duronto Express", "Kolkata", "Delhi", 55, 950.0));
        trains.add(new Train(12348, "Garib Rath", "Bangalore", "Delhi", 70, 650.0));
        trains.add(new Train(12349, "Jan Shatabdi", "Mumbai", "Pune", 40, 250.0));

        JFrame mainFrame = new JFrame("Railway Ticket Reservation System");
        mainFrame.setSize(500, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnBook = new JButton("Book Ticket");
        JButton btnCancel = new JButton("Cancel Ticket");
        JButton btnStatus = new JButton("Check PNR Status");
        JButton btnViewTrains = new JButton("View Available Trains");
        JButton btnAdmin = new JButton("Admin Login");
        JButton btnExit = new JButton("Exit");

        mainFrame.add(btnBook);
        mainFrame.add(btnCancel);
        mainFrame.add(btnStatus);
        mainFrame.add(btnViewTrains);
        mainFrame.add(btnAdmin);
        mainFrame.add(btnExit);

        btnBook.addActionListener(e -> bookTicket());
        btnCancel.addActionListener(e -> cancelTicket());
        btnStatus.addActionListener(e -> checkStatus());
        btnViewTrains.addActionListener(e -> viewTrains());
        btnAdmin.addActionListener(e -> adminLogin());
        btnExit.addActionListener(e -> System.exit(0));

        mainFrame.setVisible(true);
    }

    static void bookTicket() {
        JFrame frame = new JFrame("Book Ticket");
        frame.setSize(450, 400);
        frame.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel lblName = new JLabel("Passenger Name:");
        JTextField txtName = new JTextField();

        JLabel lblAge = new JLabel("Age:");
        JTextField txtAge = new JTextField();

        JLabel lblGender = new JLabel("Gender:");
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> cmbGender = new JComboBox<>(genders);

        JLabel lblTrain = new JLabel("Select Train:");
        String[] trainOptions = new String[trains.size()];
        for (int i = 0; i < trains.size(); i++) {
            trainOptions[i] = trains.get(i).getTrainNumber() + " - " + trains.get(i).getTrainName();
        }
        JComboBox<String> cmbTrain = new JComboBox<>(trainOptions);

        JButton btnSubmit = new JButton("Book");
        JButton btnBack = new JButton("Back");

        frame.add(lblName);
        frame.add(txtName);
        frame.add(lblAge);
        frame.add(txtAge);
        frame.add(lblGender);
        frame.add(cmbGender);
        frame.add(lblTrain);
        frame.add(cmbTrain);
        frame.add(new JLabel());
        frame.add(btnSubmit);
        frame.add(new JLabel());
        frame.add(btnBack);

        btnSubmit.addActionListener(e -> {
            String name = txtName.getText();
            String ageStr = txtAge.getText();
            String gender = (String) cmbGender.getSelectedItem();
            int trainIndex = cmbTrain.getSelectedIndex();

            if (name.isEmpty() || ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter all details!");
                return;
            }

            try {
                int age = Integer.parseInt(ageStr);
                if (age <= 0 || age > 120) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid age!");
                    return;
                }

                Train selectedTrain = trains.get(trainIndex);

                if (selectedTrain.bookSeat()) {
                    Ticket ticket = new Ticket(name, age, gender, selectedTrain);
                    tickets.add(ticket);
                    JOptionPane.showMessageDialog(frame,
                            "Ticket Booked Successfully!\n\n" + ticket.toString(),
                            "Booking Confirmed",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Sorry! No seats available on this train.",
                            "Booking Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid age!");
            }
        });

        btnBack.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    static void cancelTicket() {
        String pnrStr = JOptionPane.showInputDialog("Enter PNR Number:");
        if (pnrStr == null || pnrStr.isEmpty()) {
            return;
        }

        try {
            int pnr = Integer.parseInt(pnrStr);
            for (Ticket ticket : tickets) {
                if (ticket.getPnr() == pnr) {
                    if (ticket.getStatus().equals("Cancelled")) {
                        JOptionPane.showMessageDialog(null,
                                "This ticket is already cancelled!",
                                "Cancellation Failed",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to cancel this ticket?\n\n" + ticket.toString(),
                            "Confirm Cancellation",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        ticket.setStatus("Cancelled");
                        ticket.getTrain().cancelSeat();
                        JOptionPane.showMessageDialog(null,
                                "Ticket cancelled successfully!\nRefund will be processed within 7 days.",
                                "Cancellation Successful",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null,
                    "PNR not found!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid PNR number!");
        }
    }

    static void checkStatus() {
        String pnrStr = JOptionPane.showInputDialog("Enter PNR Number:");
        if (pnrStr == null || pnrStr.isEmpty()) {
            return;
        }

        try {
            int pnr = Integer.parseInt(pnrStr);
            for (Ticket ticket : tickets) {
                if (ticket.getPnr() == pnr) {
                    JOptionPane.showMessageDialog(null,
                            ticket.toString(),
                            "PNR Status",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null,
                    "PNR not found!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid PNR number!");
        }
    }

    static void viewTrains() {
        JFrame frame = new JFrame("Available Trains");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        StringBuilder sb = new StringBuilder();
        sb.append("========== AVAILABLE TRAINS ==========\n\n");
        for (Train train : trains) {
            sb.append(train.toString()).append("\n\n");
        }

        textArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> frame.dispose());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(btnClose, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    static void adminLogin() {
        String pass = JOptionPane.showInputDialog("Enter Admin Password:");
        if (pass == null || pass.isEmpty()) {
            return;
        }

        if (!pass.equals("admin123")) {
            JOptionPane.showMessageDialog(null,
                    "Incorrect Password!",
                    "Authentication Failed",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame adminFrame = new JFrame("Admin Panel");
        adminFrame.setSize(500, 300);
        adminFrame.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnViewAll = new JButton("View All Bookings");
        JButton btnViewTrains = new JButton("View Train Details");
        JButton btnBack = new JButton("Back");

        adminFrame.add(btnViewAll);
        adminFrame.add(btnViewTrains);
        adminFrame.add(btnBack);

        btnViewAll.addActionListener(e -> {
            JFrame bookingFrame = new JFrame("All Bookings");
            bookingFrame.setSize(600, 500);
            bookingFrame.setLayout(new BorderLayout());

            JTextArea area = new JTextArea();
            area.setEditable(false);
            area.setFont(new Font("Monospaced", Font.PLAIN, 12));

            StringBuilder sb = new StringBuilder();
            sb.append("========== ALL BOOKINGS ==========\n\n");
            if (tickets.isEmpty()) {
                sb.append("No bookings yet!");
            } else {
                for (Ticket ticket : tickets) {
                    sb.append(ticket.toString()).append("\n-------------------\n");
                }
            }

            area.setText(sb.toString());
            JScrollPane scrollPane = new JScrollPane(area);

            JButton btnClose = new JButton("Close");
            btnClose.addActionListener(ev -> bookingFrame.dispose());

            bookingFrame.add(scrollPane, BorderLayout.CENTER);
            bookingFrame.add(btnClose, BorderLayout.SOUTH);
            bookingFrame.setVisible(true);
        });

        btnViewTrains.addActionListener(e -> viewTrains());
        btnBack.addActionListener(e -> adminFrame.dispose());

        adminFrame.setVisible(true);
    }
}
