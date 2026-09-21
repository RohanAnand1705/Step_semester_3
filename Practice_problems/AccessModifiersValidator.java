class AccessRuleEngine {

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

    static String summarizeBatch(String[][] attempts) {

        int allowed = 0;
        int denied = 0;

        for (String[] attempt : attempts) {

            String result = classifyAccess(attempt[0], attempt[1]);

            if (result.equals("ALLOWED")) {
                allowed++;
            } else {
                denied++;
            }
        }

        return "Allowed: " + allowed +
                " | Denied: " + denied;
    }
}

class PatientRecord {

    private String patientId;
    String wardCode;
    protected double vitalsScore;
    public String facilityName;

    public PatientRecord(String patientId,
            String wardCode,
            double vitalsScore,
            String facilityName) {

        String id = patientId.trim();

        if (id.length() < 4) {
            throw new IllegalArgumentException(
                    "Invalid patient ID");
        }

        this.patientId = id;
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }
}

public class AccessModifiersValidator {

    public static void main(String[] args) {

        System.out.println(
                AccessRuleEngine.classifyAccess(
                        "private",
                        "SAME_CLASS"));

        System.out.println(
                AccessRuleEngine.classifyAccess(
                        "default",
                        "DIFFERENT_PACKAGE"));

        String[][] attempts = {
                { "protected", "SAME_PACKAGE" },
                { "protected", "DIFFERENT_PACKAGE" },
                { "public", "DIFFERENT_PACKAGE" }
        };

        System.out.println(
                AccessRuleEngine.summarizeBatch(attempts));

        try {

            PatientRecord p = new PatientRecord(
                    "MT9",
                    "W3",
                    98.2,
                    "MediTrack Central");

            System.out.println("Patient created");

        } catch (IllegalArgumentException e) {

            System.out.println("construction rejected");
        }

        PatientRecord p = new PatientRecord(
                "MT94",
                "W3",
                98.2,
                "MediTrack Central");

        System.out.println("Valid patient created");
    }
}