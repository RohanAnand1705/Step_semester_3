package Assignment_Problems;

class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }

    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }
}

class LibraryMember {
    private String name;
    private String memberId;
    private int booksIssued;

    static String libraryName = "Central Library";
    static int memberCount = 1000;

    LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        this.memberId = "LM-" + memberCount;
    }

    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    static void printTotalMembers() {
        System.out.println("Total members: " + (memberCount - 1000));
    }
}

public class F4LibraryMember {
    public static void main(String[] args) {
        System.out.println("Broken version:");

        BrokenLibraryMember member1 = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember member2 = new BrokenLibraryMember("Rohan", "LM-1002", 3);

        member1.printMemberCard();
        member2.printMemberCard();

        System.out.println();

        System.out.println("Fixed version:");

        LibraryMember fixedMember1 = new LibraryMember("Aditi", 2);
        LibraryMember fixedMember2 = new LibraryMember("Rohan", 3);

        fixedMember1.printMemberCard();
        fixedMember2.printMemberCard();

        LibraryMember.printTotalMembers();
    }
}