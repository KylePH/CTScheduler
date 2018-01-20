package ctscheduler;

import ctscheduler.controllers.addemployee.AddEmployeeController;
import ctscheduler.controllers.addrole.AddRoleController;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.spreadsheet.GridBase;
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
            addEmployeeController.setFileManager(fileManager);
            addEmployeeController.setRoles(fileManager.getRoles());

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
    protected void mnuOpenAddRoleForm() {

        //TODO: If role name is updated, employees are no longer associated with the role. Need to fix
        //TODO: Delete button
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addRoleForm.fxml"));
            Parent form = loader.load();

            AddRoleController addRoleController = loader.getController();
            addRoleController.setFileManager(fileManager);

            Stage stage = new Stage();
            Scene scene = new Scene(form);
            stage.setTitle("Add Position");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void mnuOpenManageRolesForm() {
        int height = 450;
        int width = 600;

        List<Role> roleListLocal = fileManager.getRoles();

        for(Role role : roleListLocal) {
            StringBuilder emps = new StringBuilder();
            for(Employee emp : fileManager.getEmployees()) {
                if(emp.getRole().contains(role.getName())) {
                    emps.append(emp.getNameSimple()).append(", ");
                }
            }
            emps.toString().trim();
            if(emps.length() > 2) {
                emps = new StringBuilder(emps.substring(0, emps.length() - 2));
            }
            role.setEmployees(emps.toString());
        }

        final Role[] selectedRole = new Role[1]; // So I can set this from a child class

        MasterDetailPane masterDetailPane = new MasterDetailPane();
        TableView<Role> employeeTable = new TableView<>();
        Label detailLabel = new Label();
        detailLabel.setWrapText(true);
        detailLabel.setTextAlignment(TextAlignment.LEFT);
        ScrollPane labelPane = new ScrollPane(detailLabel);


        employeeTable.setItems(FXCollections.observableList(roleListLocal));

        TableColumn employeeNameCol  = new TableColumn("Position");
        employeeNameCol.setCellValueFactory(new PropertyValueFactory("position"));

        TableColumn employeeRoleCol  = new TableColumn("Employees");
        employeeRoleCol.setCellValueFactory(new PropertyValueFactory("employees"));

        employeeTable.getColumns().setAll(employeeNameCol, employeeRoleCol);
        //employeeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        employeeNameCol.prefWidthProperty().bind(employeeTable.widthProperty().multiply(0.3));
        employeeRoleCol.prefWidthProperty().bind(employeeTable.widthProperty().multiply(0.7));
        employeeNameCol.setResizable(false);
        employeeRoleCol.setResizable(false); // Table column size will need to be tweaked eventually.

        masterDetailPane.setMasterNode(employeeTable);
        masterDetailPane.setDividerPosition(0.69D);
        masterDetailPane.setDetailNode(labelPane);
        masterDetailPane.setDetailSide(Side.RIGHT);
        masterDetailPane.setShowDetailNode(true);
        masterDetailPane.setPrefSize(width - 20, height - 60);
        masterDetailPane.setLayoutX(10d);
        masterDetailPane.setLayoutY(10d);

        Button btnCancel = new Button();
        btnCancel.setText("Cancel");
        btnCancel.setLayoutX(10d);
        btnCancel.setLayoutY(height - 37d); // height is 27 then subtract 10 for padding.

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                stage.close();
            }
        });

        Button btnEdit = new Button(); // Width is 42, height is 27
        btnEdit.setText("Edit");
        btnEdit.setLayoutX(width - 52d); // width is 42 after added to the container. Then subtract 10 for padding.
        btnEdit.setLayoutY(height - 37d); // height is 27 then subtract 10 for padding.
        btnEdit.setDisable(true);

        btnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addRoleForm.fxml"));
                    Parent form = loader.load();

                    AddRoleController addRoleController = loader.getController();
                    addRoleController.setFileManager(fileManager);
                    addRoleController.setRole(selectedRole[0]);

                    Stage stage = new Stage();
                    Scene scene = new Scene(form);
                    stage.setTitle("Edit Position");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // They clicked the add employee button and the dialog closed.
                                    // Now reopen the manage employees dialog to have updated data
                                    Stage stage = (Stage) btnCancel.getScene().getWindow();
                                    stage.close();
                                    mnuOpenManageRolesForm();
                                }
                            });
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        employeeTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int ix = newValue.intValue();
                if(ix == roleListLocal.size()) {
                    btnEdit.setDisable(true);
                    return; // invalid data
                }

                selectedRole[0] = roleListLocal.get(ix);
                detailLabel.setText(selectedRole[0].getInfo());
                btnEdit.setDisable(false);

            }
        });

        AnchorPane root = new AnchorPane();

        root.getChildren().add(masterDetailPane);
        root.getChildren().add(btnCancel);
        root.getChildren().add(btnEdit);

        Scene scene = new Scene(root, width, height);

        Stage stage = new Stage();

        stage.setTitle("Manage Positions");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    // I just make the whole thing manually here without loading up a scene from an FXML document.
    @FXML
    protected void mnuOpenManageEmployeesForm() {
        //TODO: Delete button
        int height = 450;
        int width = 600;

        List<Employee> empListLocal = fileManager.getEmployees();
        final Employee[] selectedEmployee = new Employee[1]; // So I can set this from a child class

        MasterDetailPane masterDetailPane = new MasterDetailPane();
        TableView<Employee> employeeTable = new TableView<>();
        Label detailLabel = new Label();
        detailLabel.setWrapText(true);
        detailLabel.setTextAlignment(TextAlignment.LEFT);
        ScrollPane labelPane = new ScrollPane(detailLabel);


        employeeTable.setItems(FXCollections.observableList(empListLocal));

        TableColumn employeeNameCol  = new TableColumn("Name");
        employeeNameCol.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn employeeRoleCol  = new TableColumn("Position");
        employeeRoleCol.setCellValueFactory(new PropertyValueFactory("position"));

        employeeTable.getColumns().setAll(employeeNameCol, employeeRoleCol);
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        masterDetailPane.setMasterNode(employeeTable);
        masterDetailPane.setDividerPosition(0.5D);
        masterDetailPane.setDetailNode(labelPane);
        masterDetailPane.setDetailSide(Side.RIGHT);
        masterDetailPane.setShowDetailNode(true);
        masterDetailPane.setPrefSize(width - 20, height - 60);
        masterDetailPane.setLayoutX(10d);
        masterDetailPane.setLayoutY(10d);

        Button btnCancel = new Button();
        btnCancel.setText("Cancel");
        btnCancel.setLayoutX(10d);
        btnCancel.setLayoutY(height - 37d); // height is 27 then subtract 10 for padding.

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                stage.close();
            }
        });

        Button btnEdit = new Button(); // Width is 42, height is 27
        btnEdit.setText("Edit");
        btnEdit.setLayoutX(width - 52d); // width is 42 after added to the container. Then subtract 10 for padding.
        btnEdit.setLayoutY(height - 37d); // height is 27 then subtract 10 for padding.
        btnEdit.setDisable(true);

        btnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addEmployeeForm.fxml"));
                    Parent form = loader.load();

                    AddEmployeeController addEmployeeController = loader.getController();
                    addEmployeeController.setShifts(shifts);
                    addEmployeeController.setFileManager(fileManager);
                    addEmployeeController.setRoles(fileManager.getRoles());
                    addEmployeeController.setEmployee(selectedEmployee[0]);

                    Stage stage = new Stage();
                    Scene scene = new Scene(form);
                    stage.setTitle("Edit Employee");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // They clicked the add employee button and the dialog closed.
                                    // Now reopen the manage employees dialog to have updated data
                                    Stage stage = (Stage) btnCancel.getScene().getWindow();
                                    stage.close();
                                    mnuOpenManageEmployeesForm();
                                }
                            });
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        employeeTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int ix = newValue.intValue();
                if(ix == empListLocal.size()) {
                    btnEdit.setDisable(true);
                    return; // invalid data
                }

                selectedEmployee[0] = empListLocal.get(ix);
                detailLabel.setText(selectedEmployee[0].getInfo());
                btnEdit.setDisable(false);

            }
        });

        AnchorPane root = new AnchorPane();

        root.getChildren().add(masterDetailPane);
        root.getChildren().add(btnCancel);
        root.getChildren().add(btnEdit);

        Scene scene = new Scene(root, width, height);

        Stage stage = new Stage();

        stage.setTitle("Manage Employees");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void mnuOpenPreferencesForm() {

    }


    @FXML
    protected void mnuOpenSchedule() {
        fileManager.openExcelFile("Select the schedule Excel file");
        spreadsheetManager.setRoles(fileManager.getRoles());
        GridBase grid = spreadsheetManager.getGridFromSpreadsheet(fileManager.getExcelFile());
        if(grid == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Something went wrong while opening the file.",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }
        spreadsheetView.setGrid(grid);
        unlockScheduleControls();

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

    private HostServices hostServices; // This is used primarily to open the .xlsx file in Excel from a control within this app.
    private File scheduleExcelFile;
    private final FileManager fileManager;
    List<Role> roles;
    private List<Shift> shifts;
    List<Employee> employees;
    private final SpreadsheetManager spreadsheetManager;
    final private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM.dd.yyyy");

    /**
     * Enables controls that interact with the SpreadSheetView control. This should be called after a spreadsheet
     * is loaded and the SpreadSheetView is visibile and enabled.
     */
    public void unlockScheduleControls() {
        noScheduleOpenLabel.setVisible(false);
        spreadsheetView.setVisible(true);
        dpSelectScheduleWeek.setDisable(false);
        btnCreateSchedule.setDisable(false);
        chkboxEditMode.setDisable(false);
    }

    /**
     * This will open a new window on top of the main window. Streamlines the opening of other FXML forms.
     * Only use this method if no additional information or objects need to be sent to the FXML controller.
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
    void setHostServices(HostServices hostServices) {
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

    public Controller() {
        fileManager = new FileManager();
        spreadsheetManager = new SpreadsheetManager(fileManager.getRoles());

    }
}