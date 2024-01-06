package application.util;

public class UserSession {
	 private static int userId;

	    public static int getUserId() {
	        return userId;
	    }

	    public static void setUserId(int newUserId) {
	        userId = newUserId;
	    }
}
