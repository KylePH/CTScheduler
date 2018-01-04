package ctscheduler.controllers.addemployee;

import ctscheduler.Role;
import ctscheduler.Shift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.ListSelectionView;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddEmployeeController {

    List<Shift> shifts;

    @FXML
    ListSelectionView availabilityListSelection;

    @FXML
    TextField txtFirstName;

    @FXML
    TextField txtLastName;

    @FXML
    CheckBox chkboxActive;

    @FXML
    CheckComboBox chkcomboRoles;

    @FXML
    DatePicker datePickerDayOff;

    @FXML
    DatePicker datePickerStartDate;

    @FXML
    DatePicker datePickerEndDate;

    @FXML
    ListView listViewDaysOff;

    @FXML
    Button btnAddDayOff;

    private DateTimeFormatter dateFormat;
    ObservableList<String> daysOffList;


    @FXML
    protected void btnAddEmployee() {

    }

    @FXML
    protected void btnManageRoles() {

    }

    @FXML
    protected void btnAddDayOffAction() {
        String date = dateFormat.format(datePickerDayOff.getValue());
        daysOffList.add(date);
        listViewDaysOff.setItems(daysOffList);
        btnAddDayOff.setDisable(true);
    }

    @FXML
    protected void datePickerDayOffAction() {
        btnAddDayOff.setDisable(false);
    }

    @FXML
    public void initialize() {
        Label sourceHeaderLabel = new Label();
        Label targetHeaderLabel = new Label();
        sourceHeaderLabel.setText("Unavailable");
        targetHeaderLabel.setText("Available");
        availabilityListSelection.setSourceHeader(sourceHeaderLabel);
        availabilityListSelection.setTargetHeader(targetHeaderLabel);
        dateFormat = DateTimeFormatter.ofPattern("EEEE MM/dd/yyyy");
        daysOffList = FXCollections.observableArrayList();

    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
        ObservableList<String> shiftList = FXCollections.observableArrayList();

        for(Shift shift : shifts) {
            shiftList.add(shift.toString());
        }
        availabilityListSelection.setSourceItems(shiftList);
    }
}
