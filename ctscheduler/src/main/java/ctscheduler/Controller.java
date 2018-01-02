package ctscheduler;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import java.io.File;
import java.net.MalformedURLException;

public class Controller {

    /*
    -------------------------------------------------------------------------------------------------------
    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> FXML INITIALIZATIONS <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    -------------------------------------------------------------------------------------------------------
     */

    @FXML
    Label labelScheduleStatus;

    @FXML
    Label noScheduleOpenLabel;

    @FXML
    Button btnCreateSchedule;

    @FXML
    Button btnSaveSchedule;

    @FXML
    CheckBox chkboxEditMode;

    @FXML
    DatePicker dpSelectScheduleWeek;

    @FXML
    SpreadsheetView spreadsheetView;


    /*
    -------------------------------------------------------------------------------------------------------
    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> FXML METHODS <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    -------------------------------------------------------------------------------------------------------
     */

    /*
    ================================================
    ================= Menu Options =================
    ================================================
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
    ================================================
    ==================== Buttons ===================
    ================================================
     */

    @FXML
    protected void btnCreateSchedule() {

    }

    @FXML
    protected void btnBuildSchedule() {

    }

    @FXML
    protected void btnSaveSchedule() {

    }

    @FXML
    protected void chkBoxEditModeChanged() {
        if(chkboxEditMode.isSelected()) {
            spreadsheetView.setEditable(true);
        } else {
            spreadsheetView.setEditable(false);
        }
    }

    /*
    ================================================
    ===================== Misc =====================
    ================================================
     */

    @FXML
    protected void handleDragDrop() {

    }

    /*
    -------------------------------------------------------------------------------------------------------
    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> NON-FXML <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    -------------------------------------------------------------------------------------------------------
     */

    HostServices hostServices;
    File scheduleExcelFile;

    public void unlockScheduleControls() {
        noScheduleOpenLabel.setVisible(false);
        dpSelectScheduleWeek.setDisable(false);
        btnCreateSchedule.setDisable(false);
        chkboxEditMode.setDisable(false);
    }

    // Receives the HostServices object associated with the application.
    // This is called from Main in the start method.
    protected void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    public void initialize() {
        noScheduleOpenLabel.setVisible(true);
        spreadsheetView.setVisible(false);
        spreadsheetView.setEditable(false);
        dpSelectScheduleWeek.setDisable(true);
        btnCreateSchedule.setDisable(true);
        btnSaveSchedule.setDisable(true);
        chkboxEditMode.setDisable(true);
    }
}