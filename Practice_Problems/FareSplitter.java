import java.math.BigDecimal;
import java.math.RoundingMode;

public class FareSplitter {

    private String tripId;
    private double totalFare;
    private int passengerCount;

    public FareSplitter(
            String tripId,
            double totalFare,
            int passengerCount) {

        if (totalFare < 0) {
            throw new IllegalArgumentException(
                    "Fare cannot be negative");
        }

        if (passengerCount <= 0) {
            throw new IllegalArgumentException(
                    "Passenger count must be positive");
        }

        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(
            String tripId,
            double totalFare) {

        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId) {

        this(tripId, 0.0, 2);
    }

    public double[] fareBreakdown() {

        double[] result = new double[passengerCount];

        BigDecimal fare = BigDecimal.valueOf(totalFare);

        BigDecimal count = BigDecimal.valueOf(passengerCount);

        // Round each normal share DOWN to 2 decimals
        BigDecimal share = fare.divide(
                count,
                2,
                RoundingMode.DOWN);

        BigDecimal assigned = BigDecimal.ZERO;

        for (int i = 0; i < passengerCount - 1; i++) {

            result[i] = share.doubleValue();

            assigned = assigned.add(share);
        }

        // Last passenger gets the remainder
        BigDecimal last = fare.subtract(assigned)
                .setScale(
                        2,
                        RoundingMode.HALF_UP);

        result[passengerCount - 1] = last.doubleValue();

        return result;
    }

    public boolean isConfirmationOverdue(
            int confirmed,
            int expected) {

        return confirmed < expected;
    }

    public static void main(String[] args) {

        FareSplitter splitter = new FareSplitter(
                "TRIP001",
                100000,
                3);

        double[] breakdown = splitter.fareBreakdown();

        for (double amount : breakdown) {
            System.out.printf("%.2f%n", amount);
        }

        System.out.println();

        FareSplitter provisional = new FareSplitter("TRIP003");

        double[] zeroBreakdown = provisional.fareBreakdown();

        for (double amount : zeroBreakdown) {
            System.out.printf("%.1f%n", amount);
        }

        System.out.println();

        System.out.println(
                "Overdue: "
                        + splitter.isConfirmationOverdue(2, 3));
    }
}