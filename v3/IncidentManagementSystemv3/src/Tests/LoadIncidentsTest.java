package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import v3.FileManager;
import v3.User;
import v3.Incident;

import java.util.*;

public class LoadIncidentsTest {

    @Test
    public void testLoadIncidents_Positive_FileExists() {
        List<User> users = new ArrayList<>();
        users.add(new User("eray", "1234"));

        Map<String, List<Incident>> incidentMap = new HashMap<>();
        incidentMap.put("eray", new ArrayList<>());

        FileManager.loadIncidents(users, incidentMap);

        assertNotNull(incidentMap.get("eray"));
    }

    @Test
    public void testLoadIncidents_Negative_FileDoesNotExist() {
        List<User> users = new ArrayList<>();
        users.add(new User("ghost", "nopass"));

        Map<String, List<Incident>> map = new HashMap<>();
        map.put("ghost", new ArrayList<>());

        FileManager.loadIncidents(users, map);

        assertTrue("Should remain empty if no file exists", map.get("ghost").isEmpty());
    }
}