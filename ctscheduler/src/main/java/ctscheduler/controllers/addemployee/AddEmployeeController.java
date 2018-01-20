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
import org.controlsfx.control.ListSelectionView;

import java.time.LocalDate;
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

    private ObservableList<String> daysOffList;

    private ObservableList<String> shiftList;

    private ObservableList<String> rolesList;

    private FileManager fileManager;

    private List<Shift> shifts;

    private List<Role> roles;

    private Employee oldEmployee;

    // TODO: Make a way to specify hourly rate for each role selected.
    // TODO: Make a way to remove dates from listViewDaysOff
    // TODO: Figure out a solution to the guaranteed shifts problem

    /**
     * Retrieves data from all user input controls and sets them to the appropriate fields within the Employee
     * class. Some data is reformatted. Then calls on the FileManager class to save the employee to a text file.
     */
    @FXML
    protected void btnAddEmployee() {

        if(txtFirstName.getText().equals("")
                || txtLastName.getText().equals("")
                || chkcomboRoles.getCheckModel().getCheckedItems().isEmpty()
                || comboBoxRating.getValue() == null
                || availabilityListSelection.getTargetItems().isEmpty()
                || textFieldHourlyRate.getText().equals("")
                || textFieldPreferredWeeklyHours.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Some required fields are empty.",
                    ButtonType.OK);
            alert.showAndWait();
        }

        Employee employee = new Employee(
                txtFirstName.getText(),
                txtLastName.getText(),
                fileManager.getRolesList(chkcomboRoles.getCheckModel().getCheckedItems()),
                fileManager.getShiftsList(availabilityListSelection.getTargetItems()),
                (int) comboBoxRating.getValue(),
                Float.valueOf(textFieldHourlyRate.getText()),
                Integer.valueOf(textFieldPreferredWeeklyHours.getText()),
                chkboxActive.isSelected()
        );

        DateTimeFormatter tempDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter listDateFormat = DateTimeFormatter.ofPattern("EEEE MM/dd/yyyy");

        List<String> daysOffItems = listViewDaysOff.getItems();
        if(!daysOffItems.isEmpty()) {
            // Manually change the date format ("EEEE MM/dd/yyyy" to "yyyy-MM-dd")
            ArrayList<String> tempList = new ArrayList<>();
            for(String str : daysOffItems) {
                tempList.add(tempDateFormat.format(LocalDate.parse(str, listDateFormat)));
            }
            employee.setDaysOff(tempList);
        }



        if(datePickerStartDate.getValue() != null) {
            employee.setStartDate(tempDateFormat.format(datePickerStartDate.getValue()));
        }

        if(datePickerEndDate.getValue() != null) {
            employee.setEndDate(tempDateFormat.format(datePickerEndDate.getValue()));
        }

        if(oldEmployee != null) {
            fileManager.updateEmployee(employee, oldEmployee);
        } else {
            fileManager.saveEmployee(employee);
        }

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

    /**
     * Fills in all fields with an existing employee's data.
     * @param emp employee whose fields will be used.
     */
    public void setEmployee(Employee emp) {
        oldEmployee = emp;
        txtFirstName.setText(emp.getFirstName());
        txtLastName.setText(emp.getLastName());
        textFieldHourlyRate.setText(emp.getHourlyRate() + "");
        textFieldPreferredWeeklyHours.setText(emp.getPreferredWeeklyHours() + "");
        chkboxActive.setSelected(emp.isActive());

        for(Role r : emp.getRoles()) {
            for(int x = 0; x < chkcomboRoles.getCheckModel().getItemCount(); x++) {
                if(chkcomboRoles.getCheckModel().getItem(x).equals(r.getName())) {
                    chkcomboRoles.getCheckModel().check(x);

                }
            }
        }
        comboBoxRating.setValue(emp.getRating());

        ObservableList<String> availList = FXCollections.observableArrayList();
        for(Shift sh : emp.getAvailability()) {
            availList.add(sh.toString());
            for(int x = 0; x < availabilityListSelection.getSourceItems().size(); x++) {
                if(availabilityListSelection.getSourceItems().get(x).equals(sh.toString())) {
                    availabilityListSelection.getSourceItems().remove(x);
                }
            }
        }
        availabilityListSelection.setTargetItems(availList);

        if(!emp.getDaysOff().isEmpty()) {
            for(String s : emp.getDaysOff()) {
                LocalDate date = LocalDate.parse(s);
                daysOffList.add(dateFormat.format(date));
            }
            listViewDaysOff.setItems(daysOffList);
        }

        if(emp.getStartDate() != null && !emp.getStartDate().equals("") && !emp.getStartDate().equals("null")) {
            datePickerStartDate.setValue(LocalDate.parse(emp.getStartDate()));
        }

        if(emp.getEndDate() != null && !emp.getEndDate().equals("") && !emp.getEndDate().equals("null")) {
            datePickerEndDate.setValue(LocalDate.parse(emp.getEndDate()));
        }
    }
}
