package ctscheduler;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.net.MalformedURLException;

public class Controller {

    HostServices hostServices;
    File scheduleExcelFile;

    @FXML
    Label noScheduleOpenLabel;

    @FXML
    Button btnCreateSchedule;

    @FXML
    DatePicker dpSelectScheduleWeek;

    /*
    ============== Start Menu Options ==============
     */

    @FXML
    protected void mnuNewSchedule() {

    }

    @FXML
    protected void mnuSaveSchedule() {

    }

    @FXML
    protected void mnuSaveScheduleAs() {

    }

    @FXML
    protected void mnuOpenAddEmployeeForm() {
    }

    @FXML
    protected void mnuOpenRequestOffForm() {

    }

    @FXML
    protected void mnuOpenEditEmployeeForm() {

    }

    @FXML
    protected void mnuOpenViewEmployeesForm() {

    }

    @FXML
    protected void mnuOpenPreferencesForm() {

    }

    @FXML
    protected void mnuOpenSchedulePreferencesForm() {

    }

    @FXML
    protected void mnuOpenSchedule() {

    }

    @FXML
    protected void mnuSetScheduleDirectory() {

    }

    @FXML
    protected void mnuOpenInExcel() throws MalformedURLException {
        hostServices.showDocument(scheduleExcelFile.toURI().toURL().toExternalForm());
    }

    @FXML
    protected void mnuQuit() {
        System.exit(0);
    }

    /*
    ============== End Menu Options ==============
     */

    /*
    ============== Start Buttons ==============
     */

    @FXML
    protected void btnCreateSchedule() {

    }

    /*
    ============== End Buttons ==============
     */

    @FXML
    protected void handleDragDrop() {

    }

    @FXML
    protected void buttonBuildSchedule() {

    }

    @FXML
    protected void buttonSaveSchedule() {

    }


    // Receives the HostServices object associated with the application.
    // This is called from Main in the start method.
    protected void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

}
