package ctscheduler;

import ctscheduler.controllers.addemployee.AddEmployeeController;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addEmployeeForm.fxml"));
            Parent form = loader.load();

            AddEmployeeController addEmployeeController = loader.getController();
            addEmployeeController.setShifts(shifts);

            Stage stage = new Stage();
            Scene scene = new Scene(form);
            stage.setTitle("Add Employee");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        fileManager.openExcelFile("Select the schedule Excel file");
    }

    @FXML
    protected void mnuSetScheduleDirectory() {
        fileManager.setDirectory("Select the folder that you want to store your schedules in.");
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

    HostServices hostServices; // This is used primarily to open the .xlsx file in Excel from a control within this app.
    File scheduleExcelFile;
    FileManager fileManager;
    List<Role> roles;
    List<Shift> shifts;
    final private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM.dd.yyyy");

    /**
     * Enables controls that interact with the SpreadSheetView control. This should be called after a spreadsheet
     * is loaded and the SpreadSheetView is visibile and enabled.
     */
    public void unlockScheduleControls() {
        noScheduleOpenLabel.setVisible(false);
        dpSelectScheduleWeek.setDisable(false);
        btnCreateSchedule.setDisable(false);
        chkboxEditMode.setDisable(false);
    }

    /**
     * This will open a new window on top of the main window. Made for streamlined opening of other FXML forms.
     * @param title Title of the window.
     * @param FXMLName Name of the FXML file associated with the window being opened.
     * @param resizable Passed to Stage.setResizable(bool)
     * @param height Sets the height of the window. 0 to use the default height specified by the FXML file.
     * @param width Sets the width of the window. 0 to use the default width specified by the FXML file.
     * @throws IOException
     */
    public void openWindow(String title, String FXMLName, boolean resizable, int height, int width) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + FXMLName + ".fxml"));
        Parent form = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(form);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(resizable);
        if(width > 0)
            stage.setWidth(width);
        if(height > 0)
            stage.setHeight(height);
        stage.show();
    }

    /**
     * Receives the HostServices object associated with the application.
     * This is called from the Main class after the controller instance is received from the FXMLLoader.
     * Having the HostServices object is necessary for being able to open other applications in Windows.
     */
    protected void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }


    /**
     * This is called automatically by JavaFX after all FXML elements have been initialized.
     */
    @FXML
    public void initialize() {
        noScheduleOpenLabel.setVisible(true);
        spreadsheetView.setVisible(false);
        spreadsheetView.setEditable(false);
        dpSelectScheduleWeek.setDisable(true);
        btnCreateSchedule.setDisable(true);
        btnSaveSchedule.setDisable(true);
        chkboxEditMode.setDisable(true);
        dpSelectScheduleWeek.setShowWeekNumbers(true);

        fileManager = new FileManager();

        // populate shifts
        shifts = new ArrayList<>();
        shifts.add(new Shift(Day.MONDAY, TimeOfDay.LUNCH));
        shifts.add(new Shift(Day.MONDAY, TimeOfDay.DINNER));
        shifts.add(new Shift(Day.TUESDAY, TimeOfDay.LUNCH));
        shifts.add(new Shift(Day.TUESDAY, TimeOfDay.DINNER));
        shifts.add(new Shift(Day.WEDNESDAY, TimeOfDay.LUNCH));
        shifts.add(new Shift(Day.WEDNESDAY, TimeOfDay.DINNER));
        shifts.add(new Shift(Day.THURSDAY, TimeOfDay.LUNCH));
        shifts.add(new Shift(Day.THURSDAY, TimeOfDay.DINNER));
        shifts.add(new Shift(Day.FRIDAY, TimeOfDay.LUNCH));
        shifts.add(new Shift(Day.FRIDAY, TimeOfDay.DINNER));
        shifts.add(new Shift(Day.SATURDAY, TimeOfDay.LUNCH));
        shifts.add(new Shift(Day.SATURDAY, TimeOfDay.DINNER));
        shifts.add(new Shift(Day.SUNDAY, TimeOfDay.LUNCH));
        shifts.add(new Shift(Day.SUNDAY, TimeOfDay.DINNER));

    }
}