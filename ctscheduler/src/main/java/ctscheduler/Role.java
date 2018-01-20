package ctscheduler;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class Role {

    private final String name;
    private final HashMap<Shift, Integer> numPerShift;
    private final Color color;
    private final SimpleStringProperty position;
    private SimpleStringProperty employees;

    public Role(String name, HashMap<Shift, Integer> numPerShift, Color color) {
        this.name = name;
        this.numPerShift = numPerShift;
        this.color = color;
        this.position = new SimpleStringProperty(name);
    }

    public int getNumberPerShift(Shift s) {
        return numPerShift.get(s);
    }

    public Color getColor() {
        return color;
    }
    
    public String getColorHex() {
        String hex = null;
        if(color != null) {
            hex = toHexCode(this.color);
        }
        return hex;
    }

    public String getName() {
        return name;
    }

    public void setEmployees(String s) {
        this.employees = new SimpleStringProperty(s);
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

        StringBuilder numShift = new StringBuilder();
        for(Shift shift : numPerShift.keySet()) {
            numShift.append(shift.toString()).append(" - ").append(numPerShift.get(shift)).append(", ");
        }
        numShift.toString().trim();
        numShift = new StringBuilder(numShift.substring(0, numShift.length() - 2));
        s += "numPerShift: " + numShift + "; ";

        s += "color: " + toHexCode(color) + ";";

        return s;
    }

    /**
     * Presents the data associated with this object in a readable format
     * @return Formatted string containnig all data associated with this object.
     */
    public String getInfo() {
        String s = "";
        s += "Name: " + name + "\n";

        s += "Color: " + toHexCode(color) + "\n";

        StringBuilder numShift = new StringBuilder();
        for(Shift shift : numPerShift.keySet()) {
            numShift.append(shift.toString()).append(" - ").append(numPerShift.get(shift)).append("\n");
        }

        s += "\nNumbers per each shift:\n" + numShift;

        return s;
    }

    public String getPosition() {
        return position.get();
    }

    public String getEmployees() {
        return employees.get();
    }

}
