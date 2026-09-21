class AccessChecker {

    static String classifyAccess(String fieldModifier,
            String accessorContext) {

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        if (fieldModifier.equals("private")) {

            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        if (fieldModifier.equals("default")) {

            if (accessorContext.equals("SAME_CLASS") ||
                    accessorContext.equals("SAME_PACKAGE")) {

                return "ALLOWED";
            }

            return "DENIED";
        }

        if (fieldModifier.equals("protected")) {

            if (accessorContext.equals("SAME_CLASS") ||
                    accessorContext.equals("SAME_PACKAGE")) {

                return "ALLOWED";
            }

            return "DENIED";
        }

        return "DENIED";
    }

    static String summarizeByModifier(String[][] attempts) {

        int privateAllowed = 0;
        int privateDenied = 0;

        int defaultAllowed = 0;
        int defaultDenied = 0;

        int protectedAllowed = 0;
        int protectedDenied = 0;

        int publicAllowed = 0;
        int publicDenied = 0;

        for (String[] attempt : attempts) {

            String modifier = attempt[0];
            String context = attempt[1];

            String result = classifyAccess(modifier, context);

            if (modifier.equals("private")) {

                if (result.equals("ALLOWED")) {
                    privateAllowed++;
                } else {
                    privateDenied++;
                }

            } else if (modifier.equals("default")) {

                if (result.equals("ALLOWED")) {
                    defaultAllowed++;
                } else {
                    defaultDenied++;
                }

            } else if (modifier.equals("protected")) {

                if (result.equals("ALLOWED")) {
                    protectedAllowed++;
                } else {
                    protectedDenied++;
                }

            } else if (modifier.equals("public")) {

                if (result.equals("ALLOWED")) {
                    publicAllowed++;
                } else {
                    publicDenied++;
                }
            }
        }

        return "private: " + privateAllowed
                + " allowed / " + privateDenied + " denied | "
                + "default: " + defaultAllowed
                + " allowed / " + defaultDenied + " denied | "
                + "protected: " + protectedAllowed
                + " allowed / " + protectedDenied + " denied | "
                + "public: " + publicAllowed
                + " allowed / " + publicDenied + " denied";
    }
}

class LibraryMember {

    private String membershipId;

    String branchCode;

    protected double finesOwed;

    public String displayName;

    public LibraryMember(String membershipId,
            String branchCode,
            double finesOwed,
            String displayName) {

        String id = membershipId.trim();

        if (id.length() < 4) {
            throw new IllegalArgumentException(
                    "Invalid membership ID");
        }

        this.membershipId = id;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }
}

public class MembershipFieldReachChecker {

    public static void main(String[] args) {

        System.out.println(
                AccessChecker.classifyAccess(
                        "private",
                        "SAME_CLASS"));

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "DIFFERENT_PACKAGE"));

        String[][] attempts = {
                { "private", "SAME_CLASS" },
                { "private", "SAME_PACKAGE" },
                { "default", "SAME_PACKAGE" },
                { "default", "DIFFERENT_PACKAGE" },
                { "protected", "SAME_PACKAGE" },
                { "protected", "SAME_CLASS" },
                { "public", "DIFFERENT_PACKAGE" }
        };

        System.out.println(
                AccessChecker.summarizeByModifier(attempts));

        try {

            LibraryMember member = new LibraryMember(
                    "LB9",
                    "BR1",
                    0,
                    "Priya Nair");

            System.out.println(
                    "Member created");

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "construction rejected");
        }

        LibraryMember member = new LibraryMember(
                "LB94",
                "BR1",
                0,
                "Priya Nair");

        System.out.println(
                "Valid member created");
    }
}