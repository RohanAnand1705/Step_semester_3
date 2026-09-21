public class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    private static boolean processorReady;

    // Static block
    static {

        processorReady = true;
    }

    public LoanReceipt(String memberId,
            String[] bookIds) {

        if (bookIds == null) {

            throw new IllegalArgumentException(
                    "Book IDs cannot be null");
        }

        for (String bookId : bookIds) {

            if (!isValidBookId(bookId)) {

                throw new IllegalArgumentException(
                        "Invalid book ID");
            }
        }

        this.memberId = memberId;

        // Defensive copy
        this.bookIds = bookIds.clone();
    }

    private static boolean isValidBookId(
            String bookId) {

        if (bookId == null) {
            return false;
        }

        return bookId.matches("BK-\\d{3}");
    }

    public String getMemberId() {

        return memberId;
    }

    public String[] getBookIds() {

        // Defensive copy
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        if (index < 0 ||
                index >= bookIds.length) {

            throw new IndexOutOfBoundsException(
                    "Invalid book ID index");
        }

        if (!isValidBookId(newId)) {

            throw new IllegalArgumentException(
                    "Invalid book ID");
        }

        String[] corrected = bookIds.clone();

        corrected[index] = newId;

        return new LoanReceipt(
                memberId,
                corrected);
    }

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (!processorReady) {

            return "0 processed | "
                    + "0 null skipped | "
                    + "0 reference-only | "
                    + "0 regular";
        }

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {

                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {

                referenceOnly++;

            } else {

                regular++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        // Invalid book ID
        try {

            LoanReceipt r1 = new LoanReceipt(
                    "LIB-8841",
                    new String[] {
                            "BK-100",
                            "bad"
                    });

            System.out.println(
                    "Construction succeeded");

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "construction rejected");
        }

        // Defensive copy test
        LoanReceipt r2 = new LoanReceipt(
                "LIB-8841",
                new String[] {
                        "BK-100",
                        "BK-101"
                });

        String[] ids = r2.getBookIds();

        ids[0] = "HACKED";

        System.out.println(
                r2.getBookIds()[0]);

        // With-style correction
        LoanReceipt r3 = r2.withCorrectedBookId(
                0,
                "BK-999");

        System.out.println(
                r3.getBookIds()[0]);

        // Nightly circulation
        LoanReceipt[] receipts = {

                new ReferenceOnlyLoanReceipt(
                        "LIB-001",
                        new String[] {
                                "BK-200"
                        },
                        "Reading Room 3"),

                null,

                new LoanReceipt(
                        "LIB-002",
                        new String[] {
                                "BK-201"
                        })
        };

        System.out.println(
                LoanReceipt.processNightlyCirculation(
                        receipts));
    }
}

class ReferenceOnlyLoanReceipt
        extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);

        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {

        return roomNumber;
    }
}