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

    FileManager fileManager;

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

        numPerShift.put(new Shift(Day.MONDAY, TimeOfDay.LUNCH), Integer.valueOf(txtMondayLunch.getText()));
        numPerShift.put(new Shift(Day.MONDAY, TimeOfDay.DINNER), Integer.valueOf(txtMondayDinner.getText()));
        numPerShift.put(new Shift(Day.TUESDAY, TimeOfDay.LUNCH), Integer.valueOf(txtTuesdayLunch.getText()));
        numPerShift.put(new Shift(Day.TUESDAY, TimeOfDay.DINNER), Integer.valueOf(txtTuesdayDinner.getText()));
        numPerShift.put(new Shift(Day.WEDNESDAY, TimeOfDay.LUNCH), Integer.valueOf(txtWednesdayLunch.getText()));
        numPerShift.put(new Shift(Day.WEDNESDAY, TimeOfDay.DINNER), Integer.valueOf(txtWednesdayDinner.getText()));
        numPerShift.put(new Shift(Day.THURSDAY, TimeOfDay.LUNCH), Integer.valueOf(txtThursdayLunch.getText()));
        numPerShift.put(new Shift(Day.THURSDAY, TimeOfDay.DINNER), Integer.valueOf(txtThursdayDinner.getText()));
        numPerShift.put(new Shift(Day.FRIDAY, TimeOfDay.LUNCH), Integer.valueOf(txtFridayLunch.getText()));
        numPerShift.put(new Shift(Day.FRIDAY, TimeOfDay.DINNER), Integer.valueOf(txtFridayDinner.getText()));
        numPerShift.put(new Shift(Day.SATURDAY, TimeOfDay.LUNCH), Integer.valueOf(txtSaturdayLunch.getText()));
        numPerShift.put(new Shift(Day.SATURDAY, TimeOfDay.DINNER), Integer.valueOf(txtSaturdayDinner.getText()));
        numPerShift.put(new Shift(Day.SUNDAY, TimeOfDay.LUNCH), Integer.valueOf(txtSundayLunch.getText()));
        numPerShift.put(new Shift(Day.SUNDAY, TimeOfDay.DINNER), Integer.valueOf(txtSundayDinner.getText()));

        Color roleColor = colorPickerRoleColor.getValue();

        fileManager.saveRole(new Role(txtRoleName.getText(), numPerShift, roleColor));

        Stage stage = (Stage) txtThursdayLunch.getScene().getWindow();
        stage.close();
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
}
