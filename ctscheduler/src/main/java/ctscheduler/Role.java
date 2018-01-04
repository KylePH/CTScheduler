package ctscheduler;

import java.util.HashMap;

public class Role {


    // TODO: Figure out a streamlined way to specify how many of each role is supposed to work for each shift.
    // I'm thinking about making a class called Day and have another control where you flip through each day
    // and specify how many of each role should be working each shift in that day.

    String name;
    HashMap<Shift, Integer> numPerShift;

    public Role(String name, HashMap<Shift, Integer> numPerShift) {

    }

}
