package Assignment_Problems;

class Employee {
    private int empId;
    private String empName;
    private double salary;

    Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    InternEmployee(int empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    void allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
        }
    }
}

class CompanyEmployeeRecord {
    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    String fullProfile() {
        double pay;

        if (employee instanceof ManagerEmployee) {
            pay = ((ManagerEmployee) employee).effectiveSalary();
        } else if (employee instanceof InternEmployee) {
            pay = ((InternEmployee) employee).effectiveSalary();
        } else {
            pay = employee.getSalary();
        }

        if (slot == null) {
            return name + " | Pay: Rs " + pay + " | Slot: no parking assigned";
        }

        return name + " | Pay: Rs " + pay + " | Slot: " + slot.slotNo;
    }
}

public class F5HRParkingSystem {
    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }

        return null;
    }

    static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);

        if (slot != null) {
            slot.allot(vehicleNo);
        } else {
            System.out.println("No slots available for " + vehicleNo);
        }
    }

    public static void main(String[] args) {
        ParkingSlot[] parkingSlots = {
                new ParkingSlot("A1", 1, 0),
                new ParkingSlot("A2", 1, 0)
        };

        Employee manager = new ManagerEmployee(101, "Divya", 70000, 8000);
        Employee employee = new Employee(102, "Karan", 40000);
        Employee intern = new InternEmployee(103, "Meera", 12000, 10000);

        ParkingSlot slot1 = findAvailableSlot(parkingSlots);
        if (slot1 != null) {
            slot1.allot("DIVYA123");
        }

        ParkingSlot slot2 = findAvailableSlot(parkingSlots);
        if (slot2 != null) {
            slot2.allot("KARAN123");
        }

        CompanyEmployeeRecord record1 = new CompanyEmployeeRecord("Divya", "E101", manager, slot1);

        CompanyEmployeeRecord record2 = new CompanyEmployeeRecord("Karan", "E102", employee, slot2);

        CompanyEmployeeRecord record3 = new CompanyEmployeeRecord("Meera", "E103", intern, null);

        System.out.println(record1.fullProfile());
        System.out.println(record2.fullProfile());
        System.out.println(record3.fullProfile());

        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}