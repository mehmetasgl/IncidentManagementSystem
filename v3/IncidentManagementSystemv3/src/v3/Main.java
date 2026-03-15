package v3;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("triggerError")) {
            throw new RuntimeException("Triggered failure");
        }
        else if (args.length > 0 && args[0].equals("testMode")) {
            System.out.println("Test mode: normal exit");
            return;
        }

        FileManager.createDocumentsDirectory();

        IncidentManagementSystem ims = new IncidentManagementSystem();
        
        ims.authenticateUser();
        
        ims.run();
    }
}