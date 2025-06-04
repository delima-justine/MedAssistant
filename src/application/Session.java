package application;

public class Session {
	 private static String fullname;
	 private static String email;
	 private static Integer patientId;

    public static void setFullname(String name) {
        fullname = name;
    }

    public static String getFullname() {
        return fullname;
    }
    
    public static void setEmail(String userEmail) {
        email = userEmail;
    }

    public static String getEmail() {
        return email;
    }
    
    public static void setPatientId(Integer id) {
        patientId = id;
    }

    public static Integer getPatientId() {
        return patientId;
    }

    public static void clear() {
        fullname = null;
        email = null;
        patientId = null;
    }
}
