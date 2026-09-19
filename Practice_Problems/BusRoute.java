public class BusRoute {

    private String routeCode;
    private String routeName;
    private int priority;

    public BusRoute(
            String routeCode,
            String routeName,
            int priority) {

        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(
            String routeCode,
            String routeName) {

        this(routeCode, routeName, 3);
    }

    public int compareTo(BusRoute other) {

        // Higher priority comes first
        if (this.priority != other.priority) {

            return Integer.compare(
                    other.priority,
                    this.priority);
        }

        // Case-insensitive route code
        int result = this.routeCode.compareToIgnoreCase(
                other.routeCode);

        if (result != 0) {
            return result;
        }

        // Shorter route name first
        return Integer.compare(
                this.routeName.length(),
                other.routeName.length());
    }

    public static BusRoute[] rankRoutes(
            BusRoute[] routes) {

        /*
         * Insertion sort is stable.
         * If compareTo() returns 0,
         * the original order is preserved.
         */

        for (int i = 1; i < routes.length; i++) {

            BusRoute current = routes[i];

            int j = i - 1;

            while (j >= 0 &&
                    current.compareTo(routes[j]) < 0) {

                routes[j + 1] = routes[j];

                j--;
            }

            routes[j + 1] = current;
        }

        return routes;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public static void main(String[] args) {

        BusRoute[] routes = {

                new BusRoute(
                        "RT205L",
                        "Airport Express",
                        3),

                new BusRoute(
                        "rt201j",
                        "City Central",
                        4),

                new BusRoute(
                        "RT299T",
                        "Night Service")
        };

        BusRoute[] result = rankRoutes(routes);

        for (BusRoute route : result) {

            System.out.println(
                    route.getRouteCode());
        }
    }
}