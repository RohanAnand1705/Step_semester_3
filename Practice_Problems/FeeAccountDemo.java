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

class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;

    ScholarshipFeeAccount(String regNo, double totalFee, double amountPaid,
            double scholarshipPercent) {
        super(regNo, totalFee, amountPaid);
        this.scholarshipPercent = scholarshipPercent;
    }

    double effectiveDue() {
        return getDue() - (getDue() * scholarshipPercent / 100);
    }
}

public class FeeAccountDemo {

    public static void main(String[] args) {
        System.out.println("F2: Fee Account System");

        FeeAccount plain = new FeeAccount("RA101", 150000, 0);
        HostelFeeAccount hostel = new HostelFeeAccount("RA102", 200000, 0);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA103", 180000, 0, 20);

        plain.pay(150000);
        hostel.pay(60000);

        FeeAccount[] accounts = { plain, hostel, scholarship };

        for (FeeAccount account : accounts) {
            if (account instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount scholarshipAccount = (ScholarshipFeeAccount) account;

                System.out.println("Scholarship account effective due: Rs "
                        + scholarshipAccount.effectiveDue());
            } else if (account instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs "
                        + account.getDue());
            } else {
                System.out.println("Plain account due: Rs "
                        + account.getDue());
            }
        }
    }
}