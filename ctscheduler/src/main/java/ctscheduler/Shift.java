package ctscheduler;

public class Shift {

    Day day;
    TimeOfDay timeOfDay;
    String name;

    public Shift(Day day, TimeOfDay timeOfDay) {
        this.day = day;
        this.timeOfDay = timeOfDay;

        // Construct the name String
        name = "";

        switch(day) {
            case MONDAY:
                name += "Monday";
                break;
            case TUESDAY:
                name += "Tuesday";
                break;
            case WEDNESDAY:
                name += "Wednesday";
                break;
            case THURSDAY:
                name += "Thursday";
                break;
            case FRIDAY:
                name += "Friday";
                break;
            case SATURDAY:
                name += "Saturday";
                break;
            case SUNDAY:
                name += "Sunday";
                break;
        }

        switch(timeOfDay) {
            case LUNCH:
                name += " Lunch";
                break;
            case DINNER:
                name += " Dinner";
                break;
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
