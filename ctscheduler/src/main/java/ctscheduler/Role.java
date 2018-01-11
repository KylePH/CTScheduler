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

    private String toHexCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    /**
     * Information is saved as follows:
     * name (String); numPerShift (HashMap<Shift, Integer>); String color;
     * @return Formatted String containing all role information.
     */
    @Override
    public String toString() {
        String s = "";
        s += "name: " + name + "; ";

        String numShift = "";
        for(Shift shift : numPerShift.keySet()) {
            numShift += shift.toString() + " - " + numPerShift.get(shift) + ", ";
        }
        numShift.trim();
        numShift = numShift.substring(0, numShift.length() - 2);
        s += "numPerShift: " + numShift + "; ";

        s += "color: " + toHexCode(color) + ";";

        return s;
    }

}
