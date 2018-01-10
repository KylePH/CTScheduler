package ctscheduler.controllers.addrole;

import javafx.fxml.FXML;
import javafx.scene.control.*;


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

    @FXML
    protected void btnAddRole() {
        if(     txtMondayLunch.getText().equals("")
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
        //TODO
    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
