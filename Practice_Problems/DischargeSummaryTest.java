import java.util.Arrays;

class DischargeSummary {

    private static final String SYSTEM_NAME;

    static {
        SYSTEM_NAME = "MediTrack Nightly Ledger";
    }

    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(
            String patientId,
            String[] medicationCodes) {

        if (medicationCodes == null) {
            throw new IllegalArgumentException(
                    "Medication codes cannot be null"
            );
        }

        for (String code : medicationCodes) {

            if (code == null ||
                    !code.matches("MED-[A-Z]")) {

                throw new IllegalArgumentException(
                        "Invalid medication code"
                );
            }
        }

        this.patientId = patientId;

        // Defensive copy on the way in
        this.medicationCodes =
                Arrays.copyOf(
                        medicationCodes,
                        medicationCodes.length
                );
    }

    public String[] getMedicationCodes() {

        // Defensive copy on the way out
        return Arrays.copyOf(
                medicationCodes,
                medicationCodes.length
        );
    }

    public DischargeSummary withCorrectedMedication(
            int index,
            String newCode) {

        if (index < 0 ||
                index >= medicationCodes.length) {

            throw new IndexOutOfBoundsException(
                    "Invalid medication index"
            );
        }

        if (newCode == null ||
                !newCode.matches("MED-[A-Z]")) {

            throw new IllegalArgumentException(
                    "Invalid medication code"
            );
        }

        String[] corrected =
                getMedicationCodes();

        corrected[index] = newCode;

        return new DischargeSummary(
                patientId,
                corrected
        );
    }

    public static String processNightlyBatch(
            DischargeSummary[] summaries) {

        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries == null) {
            return "0 processed | 0 null skipped | "
                    + "0 critical-care | 0 routine";
        }

        for (DischargeSummary summary : summaries) {

            if (summary == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (summary instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + criticalCare + " critical-care | "
                + routine + " routine";
    }
}


class CriticalCareDischargeSummary
        extends DischargeSummary {

    private final int icuDays;

    public CriticalCareDischargeSummary(
            String patientId,
            String[] medicationCodes,
            int icuDays) {

        super(patientId, medicationCodes);

        this.icuDays = icuDays;
    }
}


public class DischargeSummaryTest {

    public static void main(String[] args) {

        try {

            new DischargeSummary(
                    "MT2026-0142",
                    new String[]{"MED-A", "bad"}
            );

            System.out.println(
                    "construction succeeded"
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "construction rejected"
            );
        }

        DischargeSummary d =
                new DischargeSummary(
                        "MT2026-0142",
                        new String[]{"MED-A", "MED-B"}
                );

        String[] codes = d.getMedicationCodes();

        codes[0] = "TAMPERED";

        System.out.println(
                d.getMedicationCodes()[0]
        );

        DischargeSummary corrected =
                d.withCorrectedMedication(
                        0,
                        "MED-C"
                );

        System.out.println(
                corrected.getMedicationCodes()[0]
        );

        DischargeSummary[] summaries = {

                new CriticalCareDischargeSummary(
                        "MT001",
                        new String[]{"MED-X"},
                        4
                ),

                null,

                new DischargeSummary(
                        "MT002",
                        new String[]{"MED-Y"}
                )
        };

        System.out.println(
                DischargeSummary.processNightlyBatch(
                        summaries
                )
        );
    }
}