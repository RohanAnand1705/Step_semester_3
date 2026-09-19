public class DeliveryAccount {

    protected String studentId;
    protected double orderValue;

    private static double baseSurgePercent;

    // Static block
    static {
        baseSurgePercent = 1.0;
    }

    public DeliveryAccount(
            String studentId,
            double orderValue) {

        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(
            int delayMinutes) {

        if (orderValue < 0) {
            throw new IllegalArgumentException(
                    "Order value cannot be negative");
        }

        if (delayMinutes < 0) {
            throw new IllegalArgumentException(
                    "Delay minutes cannot be negative");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        return orderValue *
                baseSurgePercent /
                100.0;
    }

    public double getSettlementFee(
            int delayMinutes) {

        return calculateSurgeFee(delayMinutes);
    }

    public void processAccount(
            DeliveryAccount account,
            double amount,
            int delayMinutes) {

        if (account == null) {
            return;
        }

        try {

            account.orderValue = amount;

            double fee = account.getSettlementFee(
                    delayMinutes);

            System.out.println(
                    account.studentId
                            + " | Amount: "
                            + amount
                            + " | Surge Fee: "
                            + fee);

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid account data for "
                            + account.studentId);
        }
    }

    public static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {

        if (accounts == null ||
                amounts == null ||
                delayMinutesArray == null) {

            System.out.println(
                    "Invalid batch.");

            return;
        }

        // Use only matching indexes
        int n = Math.min(
                accounts.length,
                Math.min(
                        amounts.length,
                        delayMinutesArray.length));

        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;

        double grandTotal = 0.0;

        for (int i = 0; i < n; i++) {

            DeliveryAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            if (account instanceof Premium) {
                premiumCount++;
            } else if (account instanceof DeliveryAccount) {
                regularCount++;
            }

            try {

                account.orderValue = amounts[i];

                double fee = account.getSettlementFee(
                        delayMinutesArray[i]);

                grandTotal += fee;
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
                        + premiumCount
                        + " premium | "
                        + regularCount
                        + " regular | "
                        + "grand total surge fees = "
                        + grandTotal);
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {

                new Premium(
                        "STU001",
                        500),

                null,

                new DeliveryAccount(
                        "STU002",
                        300)
        };

        double[] amounts = {
                500,
                400,
                300
        };

        int[] delayMinutesArray = {
                10,
                5,
                0
        };

        processBatch(
                accounts,
                amounts,
                delayMinutesArray);
    }
}

class Premium extends DeliveryAccount {

    public Premium(
            String studentId,
            double orderValue) {

        super(studentId, orderValue);
    }

    public Premium(String studentId) {
        super(studentId);
    }

    @Override
    public double getSettlementFee(
            int delayMinutes) {

        // Premium gets 50% of the normal surge fee
        return calculateSurgeFee(delayMinutes) * 0.5;
    }
}
