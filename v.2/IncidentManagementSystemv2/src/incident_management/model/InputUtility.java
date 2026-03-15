package incident_management.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtility {
    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static int getValidIntegerInput(int min, int max) {
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

    public static String getStatusInput() {
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

    public static String getPriorityInput() {
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

    public static LocalDate inputDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                if (prompt.contains("Start")) {
                    return LocalDate.now();
                } else {
                    return null;
                }
            }

            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
    }

    public static boolean askYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String response = scanner.nextLine().trim().toUpperCase();
            if (response.equals("Y") || response.equals("YES")) {
                return true;
            } else if (response.equals("N") || response.equals("NO")) {
                return false;
            } else {
                System.out.println("Please enter Y or N.");
            }
        }
    }

    public static String getLineInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static Scanner getScanner() {
        return scanner;
    }
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}