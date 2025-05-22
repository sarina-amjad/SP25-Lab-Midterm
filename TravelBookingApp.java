import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TravelBookingApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TravelBookingApp().start();
        });
    }

    private TravelService travelService;
    private JFrame frame;
    private JTextField destinationField;
    private JTextField travelerNameField;
    private JTextArea outputArea;

    public TravelBookingApp() {
        TravelRepository repository = new TravelRepository();
        travelService = new TravelService(repository);
    }

    public void start() {
        frame = new JFrame("Travel Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLayout(new BorderLayout(10, 10));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255));
        JLabel titleLabel = new JLabel("Book Your Travel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(240, 248, 255));

        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        destinationField = new JTextField();
        destinationField.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel travelerNameLabel = new JLabel("Traveler Name:");
        travelerNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        travelerNameField = new JTextField();
        travelerNameField.setFont(new Font("Arial", Font.PLAIN, 16));

        inputPanel.add(destinationLabel);
        inputPanel.add(destinationField);
        inputPanel.add(travelerNameLabel);
        inputPanel.add(travelerNameField);
        frame.add(inputPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        JButton bookButton = new JButton("Book Travel");
        bookButton.setFont(new Font("Arial", Font.BOLD, 16));
        bookButton.setBackground(new Color(30, 144, 255));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTravel();
            }
        });
        buttonPanel.add(bookButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Output area
        outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Booking Details"));
        frame.add(scrollPane, BorderLayout.EAST);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void bookTravel() {
        String destination = destinationField.getText().trim();
        String travelerName = travelerNameField.getText().trim();

        if (destination.isEmpty() || travelerName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both destination and traveler name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        travelService.bookTravel(destination, travelerName, outputArea);
        destinationField.setText("");
        travelerNameField.setText("");
    }

    static class TravelRepository {
        private Map<String, String> bookings = new HashMap<>();

        public String saveBooking(String destination, String travelerName) {
            String bookingId = UUID.randomUUID().toString();
            bookings.put(bookingId, "Destination: " + destination + ", Traveler: " + travelerName);
            return bookingId;
        }

        public String getBooking(String bookingId) {
            return bookings.get(bookingId);
        }
    }

    static class TravelService {
        private TravelRepository repository;

        public TravelService(TravelRepository repository) {
            this.repository = repository;
        }

        public void bookTravel(String destination, String travelerName, JTextArea outputArea) {
            String bookingId = repository.saveBooking(destination, travelerName);
            String message = "Booking successful! ID: " + bookingId + "\nDestination: " + destination + "\nTraveler: " + travelerName;
            outputArea.append(message + "\n\n");
        }
    }
}
