package ctscheduler;

import java.util.List;

public class Employee {

    String firstName;
    String lastName;
    Role role;
    List<Shift> availability;
    boolean active;

    public Employee(String firstName, String lastName, Role role, List<Shift> availability, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.availability = availability;
        this.active = active;
    }
}
