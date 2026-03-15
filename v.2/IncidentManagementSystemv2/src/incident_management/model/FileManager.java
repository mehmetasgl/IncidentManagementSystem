package incident_management.model;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileManager {
    private static final String DOCUMENTS_DIR = "incident_documents/";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void createDocumentsDirectory() {
        File dir = new File(DOCUMENTS_DIR);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Documents directory created successfully");
            } else {
                System.out.println("Failed to create documents directory");
            }
        }
    }

    public static void saveIncidentToFile(Incident incident) {
        String fileName = incident.getUser() + "_incident.txt";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(incident.toString());
        } catch (IOException e) {
            System.out.println("Error saving incident: " + e.getMessage());
        }
    }

    public static void saveAllIncidents(Map<String, List<Incident>> userIncidents) {
        for (String userId : userIncidents.keySet()) {
            List<Incident> incidents = userIncidents.get(userId);
            String fileName = userId + "_incident.txt";

            try (FileWriter writer = new FileWriter(fileName, false)) {
                for (Incident incident : incidents) {
                    writer.write(incident.toString());
                }
            } catch (IOException e) {
                System.out.println("Error saving incidents: " + e.getMessage());
            }
        }
    }

    public static void loadIncidents(List<User> users, Map<String, List<Incident>> userIncidents) {
        for (User user : users) {
            String userId = user.getUserId();
            String fileName = userId + "_incident.txt";
            File file = new File(fileName);

            if (!file.exists()) {
                continue;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                boolean inIncident = false;

                while ((line = reader.readLine()) != null) {
                    if (line.equals("==========================")) {
                        if (!inIncident) {
                            content = new StringBuilder();
                            content.append(line).append("\n");
                            inIncident = true;
                        } else {
                            content.append(line).append("\n");
                            parseIncident(content.toString(), userId, userIncidents);
                            inIncident = false;
                        }
                    } else if (inIncident) {
                        content.append(line).append("\n");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading incidents: " + e.getMessage());
            }
        }
    }

    private static void parseIncident(String incidentStr, String userId, Map<String, List<Incident>> userIncidents) {
        try {
            String[] lines = incidentStr.split("\n");
            String id = "";
            String title = "";
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = null;
            String description = "";
            String status = "";
            String priority = "";
            List<String> attachments = new ArrayList<>();

            for (String line : lines) {
                if (line.startsWith("Incident ID:")) {
                    id = line.substring("Incident ID:".length()).trim();
                } else if (line.startsWith("Title:")) {
                    title = line.substring("Title:".length()).trim();
                } else if (line.startsWith("Start Date:")) {
                    String dateStr = line.substring("Start Date:".length()).trim();
                    startDate = LocalDate.parse(dateStr, dateFormatter);
                } else if (line.startsWith("End Date:")) {
                    String dateStr = line.substring("End Date:".length()).trim();
                    if (!dateStr.equals("Not specified")) {
                        endDate = LocalDate.parse(dateStr, dateFormatter);
                    }
                } else if (line.startsWith("Description:")) {
                    description = line.substring("Description:".length()).trim();
                } else if (line.startsWith("Status:")) {
                    status = line.substring("Status:".length()).trim();
                } else if (line.startsWith("Priority:")) {
                    priority = line.substring("Priority:".length()).trim();
                } else if (line.startsWith("- ")) {
                    String attachment = line.substring("- ".length()).trim();
                    attachments.add(attachment);
                }
            }

            if (id.isEmpty()) {
                id = UUID.randomUUID().toString().substring(0, 8);
            }

            Incident incident = new Incident(id, userId, title, startDate, endDate, description, status, priority);

            for (String attachment : attachments) {
                incident.addAttachment(attachment);
            }

            userIncidents.get(userId).add(incident);
        } catch (Exception e) {
            System.out.println("Error parsing incident: " + e.getMessage());
        }
    }

    public static String attachDocument(Incident incident, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "File does not exist. Please enter a valid file path.";
        }

        try {
            String fileName = file.getName();
            String targetPath = DOCUMENTS_DIR + incident.getId() + "_" + fileName;
            Files.copy(file.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            incident.addAttachment(targetPath);
            return "Document attached successfully: " + fileName;
        } catch (IOException e) {
            return "Error attaching document: " + e.getMessage();
        }
    }

    public static boolean removeDocument(Incident incident, String attachmentPath) {
        try {
            boolean deleted = Files.deleteIfExists(Paths.get(attachmentPath));
            if (deleted) {
                incident.removeAttachment(attachmentPath);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error removing attachment: " + e.getMessage());
            return false;
        }
    }


    public static void deleteIncidentAttachments(Incident incident) {
        for (String attachment : incident.getAttachments()) {
            try {
                Files.deleteIfExists(Paths.get(attachment));
            } catch (IOException e) {
                System.out.println("Error deleting attachment: " + e.getMessage());
            }
        }
    }
}
