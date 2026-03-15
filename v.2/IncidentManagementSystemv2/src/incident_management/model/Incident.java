package incident_management.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Incident {
    private String id;
    private String user;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String status;
    private String priority;
    private List<String> attachedDocuments;

    public Incident(String id, String user, String title, LocalDate startDate, LocalDate endDate,
                    String description, String status, String priority) {
        if (title == null) {
            throw new NullPointerException("Title cannot be null");
        }
        this.id=id;
        this.title=title;
        this.user = user;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.attachedDocuments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {

        if (description == null) {
            throw new NullPointerException("Description cannot be null");
        }

        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        if (description.trim().length() < 5) {
            throw new IllegalArgumentException("Description must be at least 5 characters long");
        }

        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public void addAttachment(String filePath) {
        attachedDocuments.add(filePath);
    }

    public List<String> getAttachments() {
        return attachedDocuments;
    }

    public void removeAttachment(String filePath) {
        attachedDocuments.remove(filePath);
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append("==========================\n");
        sb.append("Incident ID: ").append(id).append("\n");
        sb.append("User: ").append(user).append("\n");
        sb.append("Title: ").append(title).append("\n");
        sb.append("Start Date: ").append(startDate.format(formatter)).append("\n");
        sb.append("End Date: ").append(endDate != null ? endDate.format(formatter) : "Not specified").append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Priority: ").append(priority).append("\n");

        if (!attachedDocuments.isEmpty()) {
            sb.append("Attached Documents:\n");
            for (String doc : attachedDocuments) {
                sb.append("- ").append(doc).append("\n");
            }
        }

        sb.append("==========================\n\n");
        return sb.toString();
    }
}
