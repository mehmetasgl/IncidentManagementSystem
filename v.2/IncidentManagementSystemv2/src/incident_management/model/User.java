package incident_management.model;

public class User {
    private String userId;
    private String password;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public boolean authenticate(String inputId, String inputPassword) {
        return this.userId.equals(inputId) && this.password.equals(inputPassword);
    }

    public String getUserId() {
        return userId;
    }
}