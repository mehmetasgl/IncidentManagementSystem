import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

class User {
    private String userId;
    private String password;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public boolean authenticate(String inputId, String inputPassword) {
        return this.userId.equals(inputId) && this.password.equals(inputPassword);
    }
}

class IncidentManagementSystem {
    private static final List<User> users = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static String currentUser;

    public static void main(String[] args) {
        initializeUsers();
        authenticateUser();
        createIncident();
    }

    private static void initializeUsers() {
        users.add(new User("oyku", "1234"));
        users.add(new User("eray", "1234"));
        users.add(new User("berkay", "1234"));
        users.add(new User("mehmet", "1234"));
    }

    private static void authenticateUser() {
        while (true) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            if (isValidUser(userId, password)) {
                System.out.println("Login Successful!");
                currentUser = userId;
                return;
            } else {
                System.out.println("Invalid Credentials! Please try again.");
            }
        }
    }

    private static boolean isValidUser(String userId, String password) {
        return users.stream().anyMatch(user -> user.authenticate(userId, password));
    }

    private static void createIncident() {
        System.out.print("Enter Incident Title: ");
        String title = scanner.nextLine().trim();

        String date = LocalDate.now().toString(); // 📌 Sistemin tarihini otomatik al

        System.out.print("Enter Incident Description: ");
        String description = scanner.nextLine().trim();

        String status = getStatusInput();
        String priority = getPriorityInput();

        String incidentData = "==========================\n" +
                "User: " + currentUser + "\nTitle: " + title + "\nDate: " + date +
                "\nDescription: " + description + "\nStatus: " + status +
                "\nPriority: " + priority + "\n==========================\n\n";

        saveIncidentToFile(incidentData);

        System.out.println("Incident Created and Saved!");
    }

    private static String getStatusInput() {
        System.out.println("Select Status: 1. Started 2. In Progress 3. Done 4. Delayed");
        int choice = getValidIntegerInput(1, 4);
        return switch (choice) {
            case 1 -> "Started";
            case 2 -> "In Progress";
            case 3 -> "Done";
            case 4 -> "Delayed";
            default -> "Unknown";
        };
    }

    private static String getPriorityInput() {
        System.out.println("Select Priority: 1. Low 2. Medium 3. Important 4. Urgent");
        int choice = getValidIntegerInput(1, 4);
        return switch (choice) {
            case 1 -> "Low";
            case 2 -> "Medium";
            case 3 -> "Important";
            case 4 -> "Urgent";
            default -> "Unknown";
        };
    }

    private static int getValidIntegerInput(int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Invalid choice! Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static void saveIncidentToFile(String incidentData) {
        String fileName = currentUser + "_incident.txt";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(incidentData);
        } catch (IOException e) {
            System.out.println("Error saving incident: " + e.getMessage());
        }
    }
}
