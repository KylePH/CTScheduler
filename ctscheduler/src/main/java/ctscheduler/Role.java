package ctscheduler;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class Role {

    String name;
    HashMap<Shift, Integer> numPerShift;
    Color color;

    public Role(String name, HashMap<Shift, Integer> numPerShift, Color color) {
        this.name = name;
        this.numPerShift = numPerShift;
        this.color = color;
    }

    public void getNumberPerShift(Shift shift) {
        numPerShift.get(shift);
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
