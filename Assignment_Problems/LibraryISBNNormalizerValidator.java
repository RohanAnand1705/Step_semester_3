package Assignment_Problems;

import java.util.Scanner;

public class LibraryISBNNormalizerValidator {

    static String normalizeCode(String raw) {
        String code = raw.trim();

        if (code.length() < 3) {
            return code;
        }

        String publisher = code.substring(0, 3).toUpperCase();
        String rest = code.substring(3);

        return publisher + rest;
    }

    static String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: wrong length";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }

        String year = code.substring(3, 7);
        String catalog = code.substring(7);

        StringBuilder result = new StringBuilder();
        result.append("[");
        result.append(code.substring(0, 3));
        result.append("] YEAR: ");
        result.append(year);
        result.append(" | CATALOG: ");
        result.append(catalog);

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ISBN code: ");
        String raw = sc.nextLine();

        String normalized = normalizeCode(raw);
        System.out.println(validateAndFormat(normalized));
        sc.close();
    }
}