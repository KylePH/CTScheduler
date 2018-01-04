package ctscheduler;

import java.util.List;

public class Employee {

    String firstName;
    String lastName;
    List<Role> role;
    List<Shift> availability;
    List<String> daysOff;
    boolean active;
    String startDate;
    String endDate;

    public Employee(String firstName, String lastName, List<Role> role, List<Shift> availability, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.availability = availability;
        this.active = active;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String startDate) {
        this.endDate = endDate;
    }

    public void setDaysOff(List<String> daysOff) {
        this.daysOff = daysOff;
    }

    public void save() {
        // TODO
    }
}
