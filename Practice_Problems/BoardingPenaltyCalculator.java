public final class BoardingPenaltyCalculator {

    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(
            double minimumPenaltyPercent) {

        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(
            double ticketFare,
            int minutesLate) {

        if (ticketFare < 0) {

            throw new IllegalArgumentException(
                    "Ticket fare cannot be negative");
        }

        if (minutesLate < 0) {

            throw new IllegalArgumentException(
                    "Minutes late cannot be negative");
        }

        // On-time boarding means no penalty
        if (minutesLate == 0) {
            return 0.0;
        }

        double penalty = 0.0;

        // Minutes 1-5: 0.5%
        int firstTier = Math.min(minutesLate, 5);

        penalty += firstTier
                * ticketFare
                * 0.5
                / 100.0;

        // Minutes 6-15: 1%
        if (minutesLate > 5) {

            int secondTier = Math.min(
                    minutesLate - 5,
                    10);

            penalty += secondTier
                    * ticketFare
                    * 1.0
                    / 100.0;
        }

        // Minutes 16+: 2%
        if (minutesLate > 15) {

            int thirdTier = minutesLate - 15;

            penalty += thirdTier
                    * ticketFare
                    * 2.0
                    / 100.0;
        }

        // Minimum penalty floor
        double minimumPenalty = ticketFare
                * minimumPenaltyPercent
                / 100.0;

        return Math.max(
                penalty,
                minimumPenalty);
    }

    public static void main(String[] args) {

        BoardingPenaltyCalculator calculator = new BoardingPenaltyCalculator(1.0);

        System.out.println(
                "Rs "
                        + calculator.calculatePenalty(
                                1000,
                                0));

        System.out.println(
                "Rs "
                        + calculator.calculatePenalty(
                                1000,
                                1));

        System.out.println(
                "Rs "
                        + calculator.calculatePenalty(
                                1000,
                                16));
    }
}