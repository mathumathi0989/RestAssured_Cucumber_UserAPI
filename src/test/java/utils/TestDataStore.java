package utils;

public class TestDataStore {

	 private static String userId;
	    private static String userFirstName;

	    public static void setUserId(String id) {
	        userId = id;
	    }

	    public static String getUserId() {
	        return userId;
	    }

	    public static void setUserFirstName(String firstName) {
	        userFirstName = firstName;
	    }

	    public static String getUserFirstName() {
	        return userFirstName;
	    }
	    
}
