package ctscheduler.controllers.addemployee;

import ctscheduler.Employee;
import ctscheduler.FileManager;
import ctscheduler.Role;
import ctscheduler.Shift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;
import org.controlsfx.control.ListSelectionView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeeController {

    @FXML
    ListSelectionView availabilityListSelection;

    @FXML
    TextField txtFirstName;

    @FXML
    TextField txtLastName;

    @FXML
    TextField textFieldHourlyRate;

    @FXML
    TextField textFieldPreferredWeeklyHours;

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

    @FXML
    ComboBox comboBoxRating;

    private DateTimeFormatter dateFormat;

    ObservableList<String> daysOffList;

    ObservableList<String> shiftList;

    ObservableList<String> rolesList;

    FileManager fileManager;

    List<Shift> shifts;

    List<Role> roles;

    /**
     * Retrieves data from all user input controls and sets them to the appropriate fields within the Employee
     * class. Some data is reformatted. Then calls on the FileManager class to save the employee to a text file.
     */
    @FXML
    protected void btnAddEmployee() {

        if(txtFirstName.getText().equals("")
                || txtLastName.getText().equals("")
                || chkcomboRoles.getCheckModel().getCheckedItems().isEmpty()
                || comboBoxRating.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Some required fields are empty.",
                    ButtonType.OK);
            alert.showAndWait();
        }

        Employee employee = new Employee(
                txtFirstName.getText(),
                txtLastName.getText(),
                fileManager.getRolesList(chkcomboRoles.getCheckModel().getCheckedItems()),
                fileManager.getShiftsList(availabilityListSelection.getTargetItems()), //TODO: need to be List<Shift>
                (int) comboBoxRating.getValue(),
                Float.valueOf(textFieldHourlyRate.getText()),
                Integer.valueOf(textFieldPreferredWeeklyHours.getText()),
                chkboxActive.isSelected()
        );

        List<String> daysOffItems = listViewDaysOff.getItems();
        if(!daysOffItems.isEmpty()) {
            // Manually change the date format ("EEEE MM/dd/yyyy" to "MM.dd.yyyy")
            ArrayList<String> tempList = new ArrayList<>();
            for(String str : daysOffItems) {
                tempList.add(str.substring(str.length() - 11, str.length() - 1));
            }
            employee.setDaysOff(tempList);
        }

        DateTimeFormatter tempDateFormat = DateTimeFormatter.ofPattern("MM.dd.yyyy");

        if(datePickerStartDate.getValue() != null) {
            employee.setStartDate(tempDateFormat.format(datePickerStartDate.getValue()));
        }

        if(datePickerEndDate.getValue() != null) {
            employee.setEndDate(tempDateFormat.format(datePickerEndDate.getValue()));
        }

        employee.save(fileManager);

        // Then close the add employee window.
        Stage stage = (Stage) chkboxActive.getScene().getWindow();
        stage.close();

    }

    @FXML
    protected void btnManageRoles() {
        // TODO
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

        ObservableList<Integer> ratingList = FXCollections.observableArrayList();
        for(int x = 0; x < 10; x++) {
            ratingList.add(x + 1);
        }
        comboBoxRating.setItems(ratingList);

    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
        shiftList = FXCollections.observableArrayList();

        for(Shift shift : shifts) {
            shiftList.add(shift.toString());
        }
        availabilityListSelection.setSourceItems(shiftList);
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
        rolesList = FXCollections.observableArrayList();

        for(Role role : roles) {
            rolesList.add(role.getName());
            chkcomboRoles.getItems().add(role.getName());
        }
    }
}
