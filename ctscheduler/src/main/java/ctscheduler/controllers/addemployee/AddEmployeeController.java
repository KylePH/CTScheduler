package ctscheduler.controllers.addemployee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.ListSelectionView;

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
    CheckBox chkboxActive;

    @FXML
    CheckComboBox chkcomboRoles;

    @FXML
    protected void btnAddEmployee() {

    }

    @FXML
    protected void btnManageRoles() {

    }

    @FXML
    public void initialize() {
        Label sourceHeaderLabel = new Label();
        Label targetHeaderLabel = new Label();
        sourceHeaderLabel.setText("Unavailable");
        targetHeaderLabel.setText("Available");
        availabilityListSelection.setSourceHeader(sourceHeaderLabel);
        availabilityListSelection.setTargetHeader(targetHeaderLabel);

        ObservableList<String> shiftList = FXCollections.observableArrayList();
        shiftList.add("Monday Lunch");
        shiftList.add("Monday Dinner");
        shiftList.add("Tuesday Lunch");
        shiftList.add("Tuesday Dinner");
        shiftList.add("Wednesday Lunch");
        shiftList.add("Wednesday Dinner");
        shiftList.add("Thursday Lunch");
        shiftList.add("Thursday Dinner");
        shiftList.add("Friday Lunch");
        shiftList.add("Friday Dinner");
        shiftList.add("Saturday Lunch");
        shiftList.add("Saturday Dinner");
        shiftList.add("Sunday Lunch");
        shiftList.add("Sunday Dinner");

        availabilityListSelection.setSourceItems(shiftList);
    }
}
