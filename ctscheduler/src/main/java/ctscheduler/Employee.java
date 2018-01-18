package ctscheduler;

import javafx.beans.property.SimpleStringProperty;

import java.text.DecimalFormat;
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

    SimpleStringProperty name;
    SimpleStringProperty position;

    public Employee(String firstName, String lastName, List<Role> role, List<Shift> availability, int rating, float hourlyRate, int preferredWeeklyHours, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.availability = availability;
        this.rating = rating;
        this.hourlyRate = hourlyRate;
        this.preferredWeeklyHours = preferredWeeklyHours;
        this.active = active;

        name = new SimpleStringProperty(lastName + ", " + firstName);
        position = new SimpleStringProperty(getRole());

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

    /**
     * For use with the Manage Employees dialog. Returns a list of the employee's current Roles
     * with each element separated by a comma.
     * @return List of Roles associated with the Employee.
     */
    public String getRole() {
        String roles = "";
        for(Role roll : role) {
            roles += roll.getName() + ", ";
        }
        roles.trim();
        roles = roles.substring(0, roles.length() - 2);
        return roles;
    }

    /**
     * Much like the toString() method, but presents the data in a readable format. Each piece of data is
     * separated by a newline character.
     * @return All data associated with the Employee presented in a readable format.
     */
    public String getInfo() {

        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        String s = "";

        // add first name
        s += "First name: " + firstName + "\n";

        // add last name
        s += "Last name: " + lastName + "\n";


        // add all roles separated by commas
        String roles = "";
        for(Role roll : role) {
            roles += roll.getName() + ", ";
        }
        roles.trim();
        roles = roles.substring(0, roles.length() - 2);
        s += "Position: " + roles + "\n";

        // add all available shifts separated by commas
        String avail = "";
        for(Shift shift : availability) {
            avail += shift.toString() + ", ";
        }
        avail.trim();
        avail = avail.substring(0, avail.length() - 2);
        s += "Availability: " + avail + "\n";

        // add performance rating
        s += "Performance rating: " + rating + "\n";

        // add hourly rate
        s += "Hourly rate: $" + decimalFormat.format(hourlyRate) + "\n";

        // add preferred weekly hours
        s += "Preferred weekly hours: " + preferredWeeklyHours + "\n";

        // add active
        s += "Active: " + active + "\n";

        // add start date
        s += "Start date: " + startDate + "\n";

        // add end date
        s += "End date: " + endDate + "\n";

        // add days off
        String days = "";
        for(String str : daysOff) {
            days += str + ", ";
        }
        days.trim();
        days = days.substring(0, days.length() - 2);
        s += "Days off: " + days;

        s = s.replace("true", "yes");
        s = s.replace("false", "no");
        s = s.replace("null", "");

        return s;
    }

    // getters

    /**
     * @return Name formatted as 'Last, First'
     */
    public String getName() {
        return name.get();
    }

    /**
     * @return List of roles separated by commas.
     */
    public String getPosition() {
        return position.get();
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isActive() {
        return active;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getRating() {
        return rating;
    }

    public int getPreferredWeeklyHours() {
        return preferredWeeklyHours;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }

    public List<Role> getRoles() {
        return role;
    }

    public List<Shift> getAvailability() {
        return availability;
    }

    public List<String> getDaysOff() {
        return daysOff;
    }

}
