import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PatientProfile {

    private String patientId;
    private String name;
    private boolean discharged;

    private String lockerPinHash;

    // No-argument constructor
    public PatientProfile() {

        this(null, null);
    }

    // Name-only constructor
    public PatientProfile(String name) {

        this(null, name);
    }

    // ID + name constructor
    public PatientProfile(String patientId,
            String name) {

        this.patientId = patientId;
        this.name = name;
        this.discharged = false;
    }

    // Patient ID getter
    public String getPatientId() {

        return patientId;
    }

    // Patient ID setter - write once
    public void setPatientId(String id) {

        if (this.patientId == null) {
            this.patientId = id;
        }
    }

    // Name getter
    public String getName() {

        return name;
    }

    // Name setter
    public void setName(String name) {

        this.name = name;
    }

    // Boolean getter
    public boolean isDischarged() {

        return discharged;
    }

    // Boolean setter
    public void setDischarged(boolean discharged) {

        this.discharged = discharged;
    }

    // Write-only locker PIN
    public void setLockerPin(String pin) {

        if (pin == null ||
                !pin.matches("\\d{4,6}")) {

            throw new IllegalArgumentException(
                    "PIN must contain 4 to 6 digits");
        }

        lockerPinHash = hashPin(pin);
    }

    private String hashPin(String pin) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(pin.getBytes());

            StringBuilder result = new StringBuilder();

            for (byte b : hash) {

                result.append(
                        String.format("%02x", b));
            }

            return result.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        PatientProfile p1 = new PatientProfile("Arjun Iyer");

        System.out.println(
                p1.getPatientId());

        PatientProfile p2 = new PatientProfile(
                "MT2026-0142",
                "Arjun Iyer");

        System.out.println(
                p2.getPatientId());

        PatientProfile p3 = new PatientProfile();

        p3.setPatientId("MT2026-0142");

        p3.setPatientId("HACKED-0000");

        System.out.println(
                p3.getPatientId());

        p3.setLockerPin("1234");

        System.out.println(
                "PIN set successfully");
    }
}