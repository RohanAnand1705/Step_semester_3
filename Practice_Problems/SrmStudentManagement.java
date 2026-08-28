package Practice_Problems;

class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;

            if (amountPaid > totalFee) {
                amountPaid = totalFee;
            }
        }
    }

    double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }
}

class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot() {
        if (occupied < beds) {
            occupied++;
        }
    }
}

class SrmStudent {
    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;

    static int totalStudents = 0;

    SrmStudent(String name, String regNo, HostelFeeAccount feeAccount) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;
        totalStudents++;
    }

    String fullStatus() {
        String roomNumber;

        if (room == null) {
            roomNumber = "unallotted";
        } else {
            roomNumber = room.roomNo;
        }

        return name + " | Due: Rs " + feeAccount.getDue()
                + " | Room: " + roomNumber;
    }
}

public class SrmStudentManagement {

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }

    static void safeAllot(HostelRoom[] rooms, SrmStudent student) {
        HostelRoom room = findAvailableRoom(rooms);

        if (room != null) {
            room.allot();
            student.room = room;
        }
    }

    public static void main(String[] args) {
        System.out.println("F5: Fee + Hostel Management System");

        HostelRoom[] rooms = {
                new HostelRoom("C-214", 1, 0),
                new HostelRoom("C-507", 1, 0)
        };

        SrmStudent ravi = new SrmStudent(
                "Ravi",
                "RA101",
                new HostelFeeAccount("RA101", 200000, 0));

        SrmStudent anitha = new SrmStudent(
                "Anitha",
                "RA102",
                new HostelFeeAccount("RA102", 200000, 0));

        SrmStudent karthik = new SrmStudent(
                "Karthik",
                "RA103",
                new HostelFeeAccount("RA103", 200000, 0));

        ravi.feeAccount.pay(60000);
        anitha.feeAccount.pay(20000);
        karthik.feeAccount.pay(-5000);

        safeAllot(rooms, ravi);
        safeAllot(rooms, anitha);

        SrmStudent[] students = { ravi, anitha, karthik };

        for (SrmStudent student : students) {
            System.out.println(student.fullStatus());
        }

        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}