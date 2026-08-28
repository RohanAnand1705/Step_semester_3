package Practice_Problems;

public class SrmStudentStaticDemo {

    static String brokenName;
    static String brokenRegNo;
    static int brokenAttendance;

    String name;
    String regNo;
    int attendance;

    static String university = "SRM";
    static int admissionCount = 0;

    SrmStudentStaticDemo(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;

        admissionCount++;
        this.regNo = String.format("RA2311003010%02d", admissionCount);
    }

    static void brokenVersion() {
        brokenName = "Ravi";
        brokenRegNo = "RA101";
        brokenAttendance = 82;

        brokenName = "Meera";
        brokenRegNo = "RA102";
        brokenAttendance = 74;

        System.out.println(brokenName);
        System.out.println(brokenName);

        System.out.println("(Ravi's data was overwritten - both students now show Meera)");
    }

    void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }

    public static void main(String[] args) {
        System.out.println("F4: Instance vs Static");

        System.out.println("Broken version:");
        brokenVersion();

        System.out.println();
        System.out.println("Fixed version:");

        admissionCount = 0;

        SrmStudentStaticDemo ravi = new SrmStudentStaticDemo("Ravi", 82);

        SrmStudentStaticDemo meera = new SrmStudentStaticDemo("Meera", 74);

        ravi.printIdCard();
        meera.printIdCard();

        printTotalAdmissions();
    }
}