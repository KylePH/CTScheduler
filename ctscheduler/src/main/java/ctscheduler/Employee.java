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
    int rating;

    public Employee(String firstName, String lastName, List<Role> role, List<Shift> availability, int rating, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.availability = availability;
        this.rating = rating;
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

    public void save(FileManager fileManager) {
        fileManager.saveEmployee(this);
    }

    @Override
    public String toString() {
        String s = "";
        s += firstName + "; ";
        s += lastName + "; ";

        // add all roles separated by commas
        String roles = "";
        for(Role roll : role) {
            roles += roll.toString() + ", ";
        }
        roles.trim();
        roles = roles.substring(0, roles.length() - 1);
        s += roles + "; ";

        // add all available shifts separated by commas
        String avail = "";
        for(Shift shift : availability) {
            avail += shift.toString() + ", ";
        }
        avail.trim();
        avail = avail.substring(0, avail.length() - 1);
        s += avail + "; ";

        s += rating + "; ";

        s += active + "; ";


        return s;
    }
}
