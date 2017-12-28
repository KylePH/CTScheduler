package kyleph.ctscheduler;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.io.File;
import java.net.MalformedURLException;

public class Controller {

    @FXML
    TableView table;

    HostServices hostServices;
    File scheduleExcelFile;

    /*

    ============== Start Menu Buttons ==============

     */
    @FXML
    protected void mnuOpenAddEmployeeForm() {
        ObservableList<ObservableList<String>> superList = FXCollections.observableArrayList();
        ObservableList<String> kiddieList = FXCollections.observableArrayList();

        for(int i = 0; i < 25; ++i) {
            TableColumn tempColumn = new TableColumn("" + i);
            for(int j = 0; j < 25; j++) {
                kiddieList.add("" + j);
            }
            table.getColumns().add(tempColumn);
            superList.add(kiddieList);
            kiddieList.clear();
        }
        table.setItems(superList);
    }

    @FXML
    protected void mnuOpenRequestOffForm() {

    }

    @FXML
    protected void mnuOpenEditEmployeeForm() {

    }

    @FXML
    protected void mnuOpenViewEmployeesForm() throws MalformedURLException {

    }

    @FXML
    protected void mnuOpenAddHolidayForm() {

    }

    @FXML
    protected void mnuOpenScheduleSettingsForm() {

    }

    @FXML
    protected void mnuParseSchedule() {

    }

    @FXML
    protected void mnuSetScheduleDirectory() {

    }

    /*

    ============== End Menu Buttons ==============

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

    @FXML
    protected void buttonOpenScheduleInExcel() throws MalformedURLException {
        hostServices.showDocument(scheduleExcelFile.toURI().toURL().toExternalForm());
    }

    // Receives the HostServices object associated with the application.
    // This is called from Main in the start method.
    protected void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

}
