import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;

    private String securityAnswerHash;

    // No-argument constructor
    public LibraryMember() {

        this(null, null);
    }

    // Name-only constructor
    public LibraryMember(String name) {

        this(null, name);
    }

    // Membership ID + name constructor
    public LibraryMember(String membershipId,
            String name) {

        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }

    // Membership ID getter
    public String getMembershipId() {

        return membershipId;
    }

    // Membership ID setter - write once
    public void setMembershipId(String id) {

        if (this.membershipId == null) {
            this.membershipId = id;
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

    // Boolean JavaBean getter
    public boolean isPremiumMember() {

        return premiumMember;
    }

    // Boolean setter
    public void setPremiumMember(boolean premium) {

        this.premiumMember = premium;
    }

    // Write-only security answer
    public void setSecurityAnswer(String answer) {

        if (answer == null) {
            throw new IllegalArgumentException(
                    "Security answer cannot be null");
        }

        securityAnswerHash = hashAnswer(answer);
    }

    private String hashAnswer(String answer) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(answer.getBytes());

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

        LibraryMember m1 = new LibraryMember("Priya Nair");

        System.out.println(
                m1.getMembershipId());

        LibraryMember m2 = new LibraryMember(
                "LIB-8841",
                "Priya Nair");

        System.out.println(
                m2.getMembershipId());

        LibraryMember m3 = new LibraryMember();

        m3.setMembershipId(
                "LIB-8841");

        m3.setMembershipId(
                "FAKE-0000");

        System.out.println(
                m3.getMembershipId());

        m3.setPremiumMember(true);

        System.out.println(
                m3.isPremiumMember());

        m3.setSecurityAnswer(
                "blue");

        System.out.println(
                "Security answer set successfully");
    }
}