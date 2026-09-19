import java.util.HashSet;

public class BusTicket {

    private String passengerName;
    private String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {

        if (passengerName == null ||
                passengerName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Invalid passenger name");
        }

        if (!passengerName.trim().matches("[A-Za-z ]+")) {

            throw new IllegalArgumentException(
                    "Passenger name must contain letters only");
        }

        if (destination == null ||
                destination.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Invalid destination");
        }

        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.checkedIn = false;
    }

    public void markCheckedIn() {

        if (!checkedIn) {
            checkedIn = true;
            System.out.println("Passenger checked in.");
        } else {
            System.out.println(
                    "Passenger was already checked in.");
        }
    }

    public static void processBatch(String[][] rawBookings) {

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        HashSet<String> acceptedBookings = new HashSet<>();

        for (String[] booking : rawBookings) {

            try {

                if (booking == null ||
                        booking.length < 2) {

                    throw new IllegalArgumentException();
                }

                BusTicket ticket = new BusTicket(
                        booking[0],
                        booking[1]);

                String key = ticket.passengerName.toLowerCase()
                        + "|"
                        + ticket.destination.toLowerCase();

                if (acceptedBookings.contains(key)) {

                    duplicates++;

                } else {

                    acceptedBookings.add(key);
                    valid++;
                }

            } catch (IllegalArgumentException e) {

                rejected++;
            }
        }

        System.out.println(
                "Valid: " + valid
                        + " | Rejected: " + rejected
                        + " | Duplicates skipped: "
                        + duplicates);
    }

    public static void main(String[] args) {

        String[][] bookings = {
                { "Divya", "Chennai" },
                { "", "Bangalore" },
                { "Ravi123", "Pune" },
                { "Divya", "Chennai" },
                { " ", " " }
        };

        processBatch(bookings);

        BusTicket ticket = new BusTicket(
                "Rohan",
                "Chennai");

        ticket.markCheckedIn();
        ticket.markCheckedIn();
    }
}