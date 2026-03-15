package v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IncidentManagementSystem {
    private final List<User> users = new ArrayList<>();
    private final Map<String, List<Incident>> userIncidents = new HashMap<>();
    private String currentUser;
    private IncidentManager incidentManager;

    public IncidentManagementSystem() {
        initializeUsers();
    }

    private void initializeUsers() {
        users.add(new User("oyku", "1234"));
        users.add(new User("eray", "1234"));
        users.add(new User("berkay", "1234"));
        users.add(new User("mehmet", "1234"));

        for (User user : users) {
            userIncidents.put(user.getUserId(), new ArrayList<>());
        }

        FileManager.loadIncidents(users, userIncidents);
    }

    public void authenticateUser() {
        Scanner scanner = InputUtility.getScanner();
        while (true) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            if (isValidUser(userId, password)) {
                System.out.println("Login Successful!");
                currentUser = userId;
                incidentManager = new IncidentManager(userIncidents, currentUser);
                return;
            } else {
                System.out.println("Invalid Credentials! Please try again.");
            }
        }
    }

    private boolean isValidUser(String userId, String password) {
        return users.stream().anyMatch(user -> user.authenticate(userId, password));
    }

    private void showMainMenu() {
        System.out.println("\n===== v3.Incident Management System =====");
        System.out.println("1. Create New v3.Incident");
        System.out.println("2. View My Incidents");
        System.out.println("3. Edit v3.Incident");
        System.out.println("4. Delete v3.Incident");
        System.out.println("5. List Incidents By Date");
        System.out.println("6. View All Previous Incidents");
        System.out.println("7. Exit");
        System.out.print("Select an option: ");
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            showMainMenu();
            int choice = InputUtility.getValidIntegerInput(1, 7);

            switch (choice) {
                case 1 -> incidentManager.createIncident();
                case 2 -> incidentManager.viewIncidents();
                case 3 -> incidentManager.editIncident();
                case 4 -> incidentManager.deleteIncident();
                case 5 -> incidentManager.listIncidentsByDate();
                case 6 -> incidentManager.viewPreviousIncidents();
                case 7 -> exit = true;
            }
        }

        System.out.println("Thank you for using the v3.Incident Management System!");
    }
}