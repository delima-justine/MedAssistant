package application;

public class Session {
	 private static String fullname;

    public static void setFullname(String name) {
        fullname = name;
    }

    public static String getFullname() {
        return fullname;
    }

    public static void clear() {
        fullname = null;
    }
}
