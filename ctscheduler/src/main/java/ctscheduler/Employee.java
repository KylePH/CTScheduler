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
    int preferredWeeklyHours;
    float hourlyRate;

    public Employee(String firstName, String lastName, List<Role> role, List<Shift> availability, int rating, float hourlyRate, int preferredWeeklyHours, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.availability = availability;
        this.rating = rating;
        this.hourlyRate = hourlyRate;
        this.preferredWeeklyHours = preferredWeeklyHours;
        this.active = active;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDaysOff(List<String> daysOff) {
        this.daysOff = daysOff;
    }

    public void save(FileManager fileManager) {
        fileManager.saveEmployee(this);
    }

    /**
     * Returns a string containing all information regarding a single employee. Each field of information is
     * separated by a semicolon (;) followed by one space. If a field of information contains more than one item,
     * those items are separated by a comma (,) followed by one space.
     *
     * The order the information is stored is as follows:
     * first name; last name; role(s); availability (ex: "Friday Lunch"); rating (int 1 - 10); hourly rate (float);
     * preferred weekly hours (int); active (boolean); start date (mm.dd.yyyy); end date (mm.dd.yyyy); days off
     * @return Formatted string containing all employee information.
     */
    @Override
    public String toString() {
        String s = "";

        // add first name
        s += "firstName: " + firstName + "; ";

        // add last name
        s += "lastName: " + lastName + "; ";


        // add all roles separated by commas
        String roles = "";
        for(Role roll : role) {
            roles += roll.getName() + ", ";
        }
        roles.trim();
        roles = roles.substring(0, roles.length() - 2);
        s += "roles: " + roles + "; ";
        System.out.println(roles);

        // add all available shifts separated by commas
        String avail = "";
        for(Shift shift : availability) {
            avail += shift.toString() + ", ";
        }
        avail.trim();
        avail = avail.substring(0, avail.length() - 2);
        s += "availability: " + avail + "; ";

        // add performance rating
        s += "rating: " + rating + "; ";

        // add hourly rate
        s += "hourlyRate: " + hourlyRate + "; ";

        // add preferred weekly hours
        s += "preferredWeeklyHours: " + preferredWeeklyHours + "; ";

        // add active
        s += "active: " + active + "; ";

        // add start date
        s += "startDate: " + startDate + "; ";

        // add end date
        s += "endDate: " + endDate + "; ";

        // add days off
        String days = "";
        for(String str : daysOff) {
            days += str + ", ";
        }
        days.trim();
        days = days.substring(0, days.length() - 2);
        s += "daysOff: " + days + ";";

        return s;
    }
}
