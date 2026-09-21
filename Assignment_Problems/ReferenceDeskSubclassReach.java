class AccessChecker {

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
                    accessorContext.equals("SAME_PACKAGE") ||
                    accessorContext.equals(
                            "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {

                return "ALLOWED";
            }

            return "DENIED";
        }

        return "DENIED";
    }

    static String describeContext(String accessorContext) {

        String[] words = accessorContext.split("_");

        StringBuilder result = new StringBuilder();

        for (String word : words) {

            if (word.length() == 0) {
                continue;
            }

            result.append(
                    Character.toUpperCase(
                            word.charAt(0)));

            if (word.length() > 1) {

                result.append(
                        word.substring(1).toLowerCase());
            }

            result.append(" ");
        }

        return result.toString().trim();
    }
}

public class ReferenceDeskSubclassReach {

    public static void main(String[] args) {

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));

        System.out.println(
                AccessChecker.describeContext(
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
    }
}