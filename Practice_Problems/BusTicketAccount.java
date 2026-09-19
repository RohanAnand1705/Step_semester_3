public class BusTicketAccount {

    protected String bookingId;
    protected double ticketFare;

    private static double penaltyPercent;

    // One-time class-level setup
    static {
        penaltyPercent = 1.0;
    }

    public BusTicketAccount(
            String bookingId,
            double ticketFare) {

        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {

        this(bookingId, 0.0);
    }

    public final double calculatePenalty(
            int minutesLate) {

        if (ticketFare < 0) {

            throw new IllegalArgumentException(
                    "Ticket fare cannot be negative");
        }

        if (minutesLate < 0) {

            throw new IllegalArgumentException(
                    "Minutes late cannot be negative");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        // Simple flat-rate version
        return ticketFare
                * penaltyPercent
                / 100.0;
    }

    public double getSettlementPenalty(
            int minutesLate) {

        return calculatePenalty(minutesLate);
    }

    public void processAccount(
            BusTicketAccount account,
            double amount,
            int minutesLate) {

        if (account == null) {
            return;
        }

        try {

            account.ticketFare = amount;

            double penalty = account.getSettlementPenalty(
                    minutesLate);

            System.out.println(
                    account.bookingId
                            + " | Fare: "
                            + amount
                            + " | Penalty: "
                            + penalty);

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid account: "
                            + account.bookingId);
        }
    }

    public static void processBatch(
            BusTicketAccount[] accounts,
            double[] amounts,
            int[] minutesLateArray) {

        if (accounts == null ||
                amounts == null ||
                minutesLateArray == null) {

            System.out.println(
                    "Invalid batch.");

            return;
        }

        /*
         * If arrays have different lengths,
         * process only their common length.
         * This prevents mismatching fare and
         * delay data with another account.
         */
        int n = Math.min(
                accounts.length,
                Math.min(
                        amounts.length,
                        minutesLateArray.length));

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;

        double grandTotal = 0.0;

        for (int i = 0; i < n; i++) {

            BusTicketAccount account = accounts[i];

            if (account == null) {

                nullSkipped++;
                continue;
            }

            if (account instanceof Sleeper) {

                sleeperCount++;

            } else if (account instanceof BusTicketAccount) {

                regularCount++;
            }

            try {

                account.ticketFare = amounts[i];

                double penalty = account.getSettlementPenalty(
                        minutesLateArray[i]);

                grandTotal += penalty;
                processed++;

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid data at index "
                                + i);
            }
        }

        System.out.println(
                processed
                        + " processed | "
                        + nullSkipped
                        + " null skipped | "
                        + sleeperCount
                        + " sleeper | "
                        + regularCount
                        + " regular | "
                        + "grand total penalties = "
                        + grandTotal);
    }

    public static void main(String[] args) {

        BusTicketAccount[] accounts = {

                new Sleeper(
                        "BK001",
                        2000),

                null,

                new BusTicketAccount(
                        "BK002",
                        1200)
        };

        double[] amounts = {
                1200,
                900,
                700
        };

        int[] minutesLateArray = {
                10,
                5,
                0
        };

        processBatch(
                accounts,
                amounts,
                minutesLateArray);
    }
}

class Sleeper extends BusTicketAccount {

    public Sleeper(
            String bookingId,
            double ticketFare) {

        super(
                bookingId,
                ticketFare);
    }

    public Sleeper(String bookingId) {

        super(bookingId);
    }

    @Override
    public double getSettlementPenalty(
            int minutesLate) {

        // Sleeper gets 50% reduction
        return calculatePenalty(minutesLate)
                * 0.5;
    }
}