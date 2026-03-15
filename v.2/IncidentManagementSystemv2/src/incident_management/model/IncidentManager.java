package incident_management.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class IncidentManager {
    private final Map<String, List<Incident>> userIncidents;
    private final String currentUser;
    
    public IncidentManager(Map<String, List<Incident>> userIncidents, String currentUser) {
        this.userIncidents = userIncidents;
        this.currentUser = currentUser;
    }
    
    public void createIncident() {
        System.out.println("\n===== Create New Incident =====");

        String title = InputUtility.getLineInput("Enter Incident Title: ");
        LocalDate startDate = InputUtility.inputDate("Enter Start Date (yyyy-MM-dd) or press Enter for today: ");
        LocalDate endDate = InputUtility.inputDate("Enter End Date (yyyy-MM-dd) or press Enter to skip: ");
        String description = InputUtility.getLineInput("Enter Incident Description: ");
        String status = InputUtility.getStatusInput();
        String priority = InputUtility.getPriorityInput();
        String incidentId = UUID.randomUUID().toString().substring(0, 8);

        Incident incident = new Incident(incidentId, currentUser, title, startDate, endDate,
                description, status, priority);

        if (InputUtility.askYesNo("Do you want to attach documents to this incident? (Y/N): ")) {
            attachDocumentsToIncident(incident);
        }

        userIncidents.get(currentUser).add(incident);
        FileManager.saveIncidentToFile(incident);

        System.out.println("Incident Created and Saved!");
    }
    
    public void viewIncidents() {
        List<Incident> incidents = userIncidents.get(currentUser);

        if (incidents.isEmpty()) {
            System.out.println("You have no incidents.");
            return;
        }

        System.out.println("\n===== Your Incidents =====");
        for (int i = 0; i < incidents.size(); i++) {
            Incident incident = incidents.get(i);
            System.out.println((i+1) + ". " + incident.getTitle() + " (ID: " + incident.getId() + ")");
            System.out.println("   Status: " + incident.getStatus() + ", Priority: " + incident.getPriority());
            System.out.println("   Start Date: " + incident.getStartDate());
            System.out.println("   End Date: " + (incident.getEndDate() != null ? incident.getEndDate() : "Not specified"));
        }

        System.out.print("\nEnter incident number to view details (or 0 to return): ");
        int choice = InputUtility.getValidIntegerInput(0, incidents.size());

        if (choice > 0) {
            System.out.println("\n" + incidents.get(choice-1));
        }
    }
    
    public void editIncident() {
        List<Incident> incidents = userIncidents.get(currentUser);

        if (incidents.isEmpty()) {
            System.out.println("You have no incidents to edit.");
            return;
        }

        System.out.println("\n===== Edit Incident =====");
        for (int i = 0; i < incidents.size(); i++) {
            Incident incident = incidents.get(i);
            System.out.println((i+1) + ". " + incident.getTitle() + " (ID: " + incident.getId() + ")");
        }

        System.out.print("Enter incident number to edit (or 0 to cancel): ");
        int choice = InputUtility.getValidIntegerInput(0, incidents.size());

        if (choice == 0) {
            return;
        }

        Incident selectedIncident = incidents.get(choice-1);

        boolean done = false;
        while (!done) {
            System.out.println("\n===== Edit Incident: " + selectedIncident.getTitle() + " =====");
            System.out.println("1. Edit Title");
            System.out.println("2. Edit Start Date");
            System.out.println("3. Edit End Date");
            System.out.println("4. Edit Description");
            System.out.println("5. Edit Status");
            System.out.println("6. Edit Priority");
            System.out.println("7. Manage Attachments");
            System.out.println("8. Save and Return");
            System.out.print("Select an option: ");

            int editChoice = InputUtility.getValidIntegerInput(1, 8);

            switch (editChoice) {
                case 1 -> {
                    String newTitle = InputUtility.getLineInput("Enter new title: ");
                    selectedIncident.setTitle(newTitle);
                }
                case 2 -> {
                    LocalDate newStartDate = InputUtility.inputDate("Enter new start date (yyyy-MM-dd): ");
                    selectedIncident.setStartDate(newStartDate);
                }
                case 3 -> {
                    LocalDate newEndDate = InputUtility.inputDate("Enter new end date (yyyy-MM-dd) or press Enter to remove: ");
                    selectedIncident.setEndDate(newEndDate);
                }
                case 4 -> {
                    String newDescription = InputUtility.getLineInput("Enter new description: ");
                    selectedIncident.setDescription(newDescription);
                }
                case 5 -> selectedIncident.setStatus(InputUtility.getStatusInput());
                case 6 -> selectedIncident.setPriority(InputUtility.getPriorityInput());
                case 7 -> manageAttachments(selectedIncident);
                case 8 -> done = true;
            }
        }

        FileManager.saveAllIncidents(userIncidents);
        System.out.println("Incident updated successfully!");
    }
    
    public void deleteIncident() {
        List<Incident> incidents = userIncidents.get(currentUser);

        if (incidents.isEmpty()) {
            System.out.println("You have no incidents to delete.");
            return;
        }

        System.out.println("\n===== Delete Incident =====");
        for (int i = 0; i < incidents.size(); i++) {
            Incident incident = incidents.get(i);
            System.out.println((i+1) + ". " + incident.getTitle() + " (ID: " + incident.getId() + ")");
        }

        System.out.print("Enter incident number to delete (or 0 to cancel): ");
        int choice = InputUtility.getValidIntegerInput(0, incidents.size());

        if (choice == 0) {
            return;
        }

        Incident selectedIncident = incidents.get(choice-1);

        if (InputUtility.askYesNo("Are you sure you want to delete this incident? (Y/N): ")) {
            FileManager.deleteIncidentAttachments(selectedIncident);
            incidents.remove(choice-1);
            FileManager.saveAllIncidents(userIncidents);
            System.out.println("Incident deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private void attachDocumentsToIncident(Incident incident) {
        System.out.println("\n===== Attach Documents =====");
        System.out.println("Enter the file path to attach (or press Enter to finish): ");

        while (true) {
            String filePath = InputUtility.getScanner().nextLine().trim();
            if (filePath.isEmpty()) {
                break;
            }

            String result = FileManager.attachDocument(incident, filePath);
            System.out.println(result);
            System.out.println("Enter another file path (or press Enter to finish): ");
        }
    }
    
    private void manageAttachments(Incident incident) {
        boolean done = false;
        while (!done) {
            System.out.println("\n===== Manage Attachments =====");
            List<String> attachments = incident.getAttachments();

            if (attachments.isEmpty()) {
                System.out.println("No attachments for this incident.");
            } else {
                System.out.println("Current attachments:");
                for (int i = 0; i < attachments.size(); i++) {
                    System.out.println((i+1) + ". " + new java.io.File(attachments.get(i)).getName());
                }
            }

            System.out.println("\n1. Add new attachment");
            System.out.println("2. Remove attachment");
            System.out.println("3. Return to edit menu");
            System.out.print("Select an option: ");

            int choice = InputUtility.getValidIntegerInput(1, 3);

            switch (choice) {
                case 1 -> {
                    String filePath = InputUtility.getLineInput("Enter file path to attach: ");
                    String result = FileManager.attachDocument(incident, filePath);
                    System.out.println(result);
                }
                case 2 -> {
                    if (attachments.isEmpty()) {
                        System.out.println("No attachments to remove.");
                        continue;
                    }

                    System.out.print("Enter number of attachment to remove (or 0 to cancel): ");
                    int removeChoice = InputUtility.getValidIntegerInput(0, attachments.size());

                    if (removeChoice > 0) {
                        String attachmentToRemove = attachments.get(removeChoice - 1);
                        if (FileManager.removeDocument(incident, attachmentToRemove)) {
                            System.out.println("Attachment removed successfully.");
                        }
                    }
                }
                case 3 -> done = true;
            }
        }
    }
}