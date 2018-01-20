package ctscheduler.controllers.addrole;

import ctscheduler.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;


public class AddRoleController {

    @FXML
    TextField txtMondayLunch;

    @FXML
    TextField txtMondayDinner;

    @FXML
    TextField txtTuesdayLunch;

    @FXML
    TextField txtTuesdayDinner;

    @FXML
    TextField txtWednesdayLunch;

    @FXML
    TextField txtWednesdayDinner;

    @FXML
    TextField txtThursdayLunch;

    @FXML
    TextField txtThursdayDinner;

    @FXML
    TextField txtFridayLunch;

    @FXML
    TextField txtFridayDinner;

    @FXML
    TextField txtSaturdayLunch;

    @FXML
    TextField txtSaturdayDinner;

    @FXML
    TextField txtSundayLunch;

    @FXML
    TextField txtSundayDinner;

    @FXML
    TextField txtRoleName;

    @FXML
    ColorPicker colorPickerRoleColor;

    private FileManager fileManager;

    private Shift mondayLunch;
    private Shift mondayDinner;
    private Shift tuesdayLunch;
    private Shift tuesdayDinner;
    private Shift wednesdayLunch;
    private Shift wednesdayDinner;
    private Shift thursdayLunch;
    private Shift thursdayDinner;
    private Shift fridayLunch;
    private Shift fridayDinner;
    private Shift saturdayLunch;
    private Shift saturdayDinner;
    private Shift sundayLunch;
    private Shift sundayDinner;

    private Role oldRole;

    @FXML
    protected void btnAddRole() {
        if( txtMondayLunch.getText().equals("")
                || txtMondayDinner.getText().equals("")
                || txtTuesdayLunch.getText().equals("")
                || txtTuesdayDinner.getText().equals("")
                || txtWednesdayLunch.getText().equals("")
                || txtWednesdayDinner.getText().equals("")
                || txtThursdayLunch.getText().equals("")
                || txtThursdayDinner.getText().equals("")
                || txtFridayLunch.getText().equals("")
                || txtFridayDinner.getText().equals("")
                || txtSaturdayLunch.getText().equals("")
                || txtSaturdayDinner.getText().equals("")
                || txtSundayLunch.getText().equals("")
                || txtSundayDinner.getText().equals("")
                || txtRoleName.getText().equals("")
                ) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Not all fields have been filled in. Try again.",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        } else if( !isNumeric(txtMondayLunch.getText())
                || !isNumeric(txtMondayDinner.getText())
                || !isNumeric(txtTuesdayLunch.getText())
                || !isNumeric(txtTuesdayDinner.getText())
                || !isNumeric(txtWednesdayLunch.getText())
                || !isNumeric(txtWednesdayDinner.getText())
                || !isNumeric(txtThursdayLunch.getText())
                || !isNumeric(txtThursdayDinner.getText())
                || !isNumeric(txtFridayLunch.getText())
                || !isNumeric(txtFridayDinner.getText())
                || !isNumeric(txtSaturdayLunch.getText())
                || !isNumeric(txtSaturdayDinner.getText())
                || !isNumeric(txtSundayLunch.getText())
                || !isNumeric(txtSundayDinner.getText())
                ) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "One of the fields has been filled in with a value that is not a number. Try again.",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }

        HashMap<Shift, Integer> numPerShift = new HashMap<>();

        numPerShift.put(mondayLunch, Integer.valueOf(txtMondayLunch.getText()));
        numPerShift.put(mondayDinner, Integer.valueOf(txtMondayDinner.getText()));
        numPerShift.put(tuesdayLunch, Integer.valueOf(txtTuesdayLunch.getText()));
        numPerShift.put(tuesdayDinner, Integer.valueOf(txtTuesdayDinner.getText()));
        numPerShift.put(wednesdayLunch, Integer.valueOf(txtWednesdayLunch.getText()));
        numPerShift.put(wednesdayDinner, Integer.valueOf(txtWednesdayDinner.getText()));
        numPerShift.put(thursdayLunch, Integer.valueOf(txtThursdayLunch.getText()));
        numPerShift.put(thursdayDinner, Integer.valueOf(txtThursdayDinner.getText()));
        numPerShift.put(fridayLunch, Integer.valueOf(txtFridayLunch.getText()));
        numPerShift.put(fridayDinner, Integer.valueOf(txtFridayDinner.getText()));
        numPerShift.put(saturdayLunch, Integer.valueOf(txtSaturdayLunch.getText()));
        numPerShift.put(saturdayDinner, Integer.valueOf(txtSaturdayDinner.getText()));
        numPerShift.put(sundayLunch, Integer.valueOf(txtSundayLunch.getText()));
        numPerShift.put(sundayDinner, Integer.valueOf(txtSundayDinner.getText()));

        Color roleColor = colorPickerRoleColor.getValue();

        Role newRole = new Role(txtRoleName.getText(), numPerShift, roleColor);

        if(oldRole == null) {
            fileManager.saveRole(newRole);
        } else {
            fileManager.updateRole(newRole, oldRole);
        }
        Stage stage = (Stage) txtThursdayLunch.getScene().getWindow();
        stage.close();
    }

    public void initialize() {

        mondayLunch = new Shift(Day.MONDAY, TimeOfDay.LUNCH);
        mondayDinner = new Shift(Day.MONDAY, TimeOfDay.DINNER);
        tuesdayLunch = new Shift(Day.TUESDAY, TimeOfDay.LUNCH);
        tuesdayDinner = new Shift(Day.TUESDAY, TimeOfDay.DINNER);
        wednesdayLunch = new Shift(Day.WEDNESDAY, TimeOfDay.LUNCH);
        wednesdayDinner = new Shift(Day.WEDNESDAY, TimeOfDay.DINNER);
        thursdayLunch = new Shift(Day.THURSDAY, TimeOfDay.LUNCH);
        thursdayDinner = new Shift(Day.THURSDAY, TimeOfDay.DINNER);
        fridayLunch = new Shift(Day.FRIDAY, TimeOfDay.LUNCH);
        fridayDinner = new Shift(Day.FRIDAY, TimeOfDay.DINNER);
        saturdayLunch = new Shift(Day.SATURDAY, TimeOfDay.LUNCH);
        saturdayDinner = new Shift(Day.SATURDAY, TimeOfDay.DINNER);
        sundayLunch = new Shift(Day.SUNDAY, TimeOfDay.LUNCH);
        sundayDinner = new Shift(Day.SUNDAY, TimeOfDay.DINNER);

    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public void setRole(Role role) {
        this.oldRole = role;
        txtRoleName.setText(role.getName());
        colorPickerRoleColor.setValue(role.getColor());
        txtMondayLunch.setText("" + role.getNumberPerShift(mondayLunch));
        txtMondayDinner.setText("" + role.getNumberPerShift(mondayDinner));
        txtTuesdayLunch.setText("" + role.getNumberPerShift(tuesdayLunch));
        txtTuesdayDinner.setText("" + role.getNumberPerShift(tuesdayDinner));
        txtWednesdayLunch.setText("" + role.getNumberPerShift(wednesdayLunch));
        txtWednesdayDinner.setText("" + role.getNumberPerShift(wednesdayDinner));
        txtThursdayLunch.setText("" + role.getNumberPerShift(thursdayLunch));
        txtThursdayDinner.setText("" + role.getNumberPerShift(thursdayDinner));
        txtFridayLunch.setText("" + role.getNumberPerShift(fridayLunch));
        txtFridayDinner.setText("" + role.getNumberPerShift(fridayDinner));
        txtSaturdayLunch.setText("" + role.getNumberPerShift(saturdayLunch));
        txtSaturdayDinner.setText("" + role.getNumberPerShift(saturdayDinner));
        txtSundayLunch.setText("" + role.getNumberPerShift(sundayLunch));
        txtSundayDinner.setText("" + role.getNumberPerShift(sundayDinner));
    }
}
