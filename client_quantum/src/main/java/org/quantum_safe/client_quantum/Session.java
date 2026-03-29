package org.quantum_safe.client_quantum;

public class Session
{
    private static String userId;

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        Session.userEmail = userEmail;
    }

    private static String userEmail;
    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Session.userName = userName;
    }

    private static String userName;
    public static void setUserId(String id) {
        userId = id;
    }

    public static String getUserId() {
        return userId;
    }

    public static void clear()
    {
        setUserId(null);
        setUserName(null);
    }
}
