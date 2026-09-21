public class AccessRuleEngine2 {

    static String classifyAccess(String fieldModifier,
                                 String accessorContext) {

        // Same class
        if (accessorContext.equals("SAME_CLASS")) {

            if (fieldModifier.equals("private")
                    || fieldModifier.equals("default")
                    || fieldModifier.equals("protected")
                    || fieldModifier.equals("public")) {

                return "ALLOWED";
            }
        }

        // Same package
        if (accessorContext.equals("SAME_PACKAGE")) {

            if (fieldModifier.equals("default")
                    || fieldModifier.equals("protected")
                    || fieldModifier.equals("public")) {

                return "ALLOWED";
            }

            return "DENIED";
        }

        // Subclass in different package,
        // accessed through subclass type
        if (accessorContext.equals(
                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {

            if (fieldModifier.equals("protected")
                    || fieldModifier.equals("public")) {

                return "ALLOWED";
            }

            return "DENIED";
        }

        // Subclass in different package,
        // accessed through parent type
        if (accessorContext.equals(
                "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE")) {

            if (fieldModifier.equals("public")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        // Different package
        if (accessorContext.equals("DIFFERENT_PACKAGE")) {

            if (fieldModifier.equals("public")) {
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

            if (word.isEmpty()) {
                continue;
            }

            String formatted =
                    word.substring(0, 1).toUpperCase()
                    + word.substring(1).toLowerCase();

            if (result.length() > 0) {
                result.append(" ");
            }

            result.append(formatted);
        }

        return result.toString();
    }

    public static void main(String[] args) {

        System.out.println(
                classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
                )
        );

        System.out.println(
                classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                )
        );

        System.out.println(
                describeContext(
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                )
        );
    }
}