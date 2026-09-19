public class Canteen {

    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(
            String canteenCode,
            String canteenName,
            int trustScore) {

        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public int compareTo(Canteen other) {

        // Higher trust score comes first
        if (this.trustScore != other.trustScore) {
            return Integer.compare(
                    other.trustScore,
                    this.trustScore);
        }

        // Compare code without considering case
        int result = this.canteenCode.compareToIgnoreCase(
                other.canteenCode);

        if (result != 0) {
            return result;
        }

        // Name length as final tie-breaker
        return Integer.compare(
                this.canteenName.length(),
                other.canteenName.length());
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {

        // Selection sort
        for (int i = 0; i < canteens.length - 1; i++) {

            int best = i;

            for (int j = i + 1; j < canteens.length; j++) {

                if (canteens[j].compareTo(canteens[best]) < 0) {
                    best = j;
                }
            }

            Canteen temp = canteens[i];
            canteens[i] = canteens[best];
            canteens[best] = temp;
        }

        return canteens;
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    public static void main(String[] args) {

        Canteen[] canteens = {

                new Canteen(
                        "HB3-C",
                        "Spice Junction",
                        3),

                new Canteen(
                        "hb1-c",
                        "Grand Mess",
                        5),

                new Canteen(
                        "HB2-C",
                        "Southern Treats")
        };

        Canteen[] result = rankCanteens(canteens);

        for (Canteen canteen : result) {
            System.out.println(
                    canteen.getCanteenCode());
        }
    }
}