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

    public Shift() {};

    public Shift getShiftFromString(String daytime) {
        String day;
        String time;

        day = daytime.substring(0, daytime.indexOf(" ") - 1);
        time = daytime.substring(daytime.indexOf(" ") + 1);

        Day d = null;
        TimeOfDay t = null;

        if(day.equalsIgnoreCase("monday")) {
            d = Day.MONDAY;
        } else if(day.equalsIgnoreCase("tuesday")) {
            d = Day.TUESDAY;
        } else if(day.equalsIgnoreCase("wednesday")) {
            d = Day.WEDNESDAY;
        } else if(day.equalsIgnoreCase("thursday")) {
            d = Day.THURSDAY;
        } else if(day.equalsIgnoreCase("friday")) {
            d = Day.FRIDAY;
        } else if(day.equalsIgnoreCase("saturday")) {
            d = Day.SATURDAY;
        } else if(day.equalsIgnoreCase("sunday")) {
            d = Day.SUNDAY;
        }

        if(time.equalsIgnoreCase("lunch")) {
            t = TimeOfDay.LUNCH;
        } else if(time.equalsIgnoreCase("dinner")) {
            t = TimeOfDay.DINNER;
        }

        return new Shift(d, t);
    }

    @Override
    public String toString() {
        return name;
    }

}