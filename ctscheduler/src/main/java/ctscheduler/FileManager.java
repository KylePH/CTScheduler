package ctscheduler;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private boolean isWindows;
    private File appDataFolder;
    private File excelFile;
    private File scheduleDirectory;
    private Path settingsTxt;
    private Path employeesTxt;
    private Path rolesTxt;
    private Path recentFilesTxt;
    private List<File> recentFiles;
    private List<String> settings;
    private List<Employee> employees;
    private List<Role> roles;

    /**
     * This constructor calls the parseAppData() method which brings all saved data into the program during runtime.
     */
    public FileManager() {
        isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        parseAppData();
    }

    /**
     * Opens (or creates) the ScheduleBuilder folder within AppData or Application Support and sets the
     * settings, employees, and roles Lists to the contents of the text files within the AppData folder.
     * If those text files do not exist, this method calls initializeTextFile(String name) to create them.
     */
    private void parseAppData() {
        // Retrieve the AppData or Application Support folder.
        if(isWindows) {
            appDataFolder = new File(System.getenv("APPDATA") + "/ScheduleBuilder");
        } else {
            appDataFolder = new File(System.getProperty("user.home") + "/Library/Application Support/ScheduleBuilder");
        }

        if(!appDataFolder.exists()) {
            appDataFolder.mkdir();
        }

        settingsTxt = Paths.get(appDataFolder.getAbsolutePath() + "/settings.txt");
        employeesTxt = Paths.get(appDataFolder.getAbsolutePath() + "/employees.txt");
        rolesTxt = Paths.get(appDataFolder.getAbsolutePath() + "/roles.txt");
        recentFilesTxt = Paths.get(appDataFolder.getAbsolutePath() + "/recentfiles.txt");

        if(!settingsTxt.toFile().exists() && !settingsTxt.toFile().isFile()) {
            initializeTextFile("settings");
            return;
        }

        if(!employeesTxt.toFile().exists() && !employeesTxt.toFile().isFile()) {
            initializeTextFile("employees");
            return;
        }

        if(!rolesTxt.toFile().exists() && !rolesTxt.toFile().isFile()) {
            initializeTextFile("roles");
            return;
        }

        if(!recentFilesTxt.toFile().exists() && !recentFilesTxt.toFile().isFile()) {
            initializeTextFile("recentfiles");
            return;
        }




        try {
            settings = Files.readAllLines(settingsTxt);
            //employees = Files.readAllLines(employeesTxt);
            //roles = Files.readAllLines(rolesTxt);
            recentFiles = new ArrayList<>();
            List<String> temp = Files.readAllLines(recentFilesTxt);
            for(int i = 0; i < temp.size(); i++) {
                recentFiles.add(new File(temp.get(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!recentFiles.isEmpty()) {
            excelFile = recentFiles.get(recentFiles.size() - 1);
        }
    }

    /**
     * Creates an empty text file within the AppData folder, then calls the parseAppData() method.
     * @param name Name for the text file without the file extension.
     */
    private void initializeTextFile(String name) {
        Path txtFile = Paths.get(appDataFolder.getAbsolutePath() + "/" + name + ".txt");
        Path writtenFile = null;
        try {
            writtenFile = Files.write(txtFile, "".getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(writtenFile != null) {
            // try again
            parseAppData();
        }
    }

    /**
     * Prompts the user to open an Excel (.xlsx) file from a JavaFX FileChooser. Saves it to global variable excelFile.
     * Also adds the file to the list recentFiles to be used for populating the "Open Recent" file menu control.
     * Saves the recentFiles list to AppData folder text file "recentfiles.txt"
     * @param title Title for the JavaFX FileChooser window.
     */
    public void openExcelFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if(file == null) {
            return;
        }
        excelFile = file; // set the excelFile global variable to this.
        recentFiles.add(file);
        List<String> temp = new ArrayList<>();
        if(!recentFiles.isEmpty()) {
            for (int i = 0; i < recentFiles.size(); i++) {
                temp.add(recentFiles.get(i).getAbsolutePath());
            }
        }
        saveDataHelper(recentFilesTxt, temp);
    }

    // TODO: Save scheduleDirectory somewhere in settings.txt
    // Will set the directory for storing all of the schedule files and save the path somewhere in the settings txt.
    // Should be a void method.
    /**
     * Prompts the user to select the working directory (or workspace) for all schedule Excel files.
     * @param title Window title for the directory chooser dialog.
     */
    public void setDirectory(String title) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        scheduleDirectory = directoryChooser.showDialog(new Stage());
    }

    /**
     * Writes all data from the settings, employees, and roles lists to their respective
     * text files within the AppData folder.
     */
    public void saveData() {
        saveDataHelper(settingsTxt, settings);
        //saveDataHelper(employeesTxt, employees);
        //saveDataHelper(rolesTxt, roles);
        // TODO: These two calls
    }

    /**
     * Does the actual writing of the text files. This is just to avoid duplicate code.
     * @param saveFile The file to be updated.
     * @param list The data to save to the file.
     */
    private void saveDataHelper(Path saveFile, List<String> list) {
        Path writtenFile = null;
        try {
            writtenFile = Files.write(saveFile, list, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(writtenFile == null) {
            System.err.println("Failed to write file " + saveFile);
        }
    }

    /**
     * Loads employee into the FileManager and saves all current data to text files in the AppData folder.
     * @param employee Employee to be added and saved.
     */
    public void saveEmployee(Employee employee) {
        employees.add(employee);
        saveData();
    }


    // TODO: These two methods
    // I've been thinking of a solution to these problems
    /*
    public Employee parseEmployeeText() {

    }

    public Role parseRoleText() {

    }
    */

    private List<Employee> parseEmployeeText() {
        List<String> lines = null;
        List<Employee> employeeLocal = new ArrayList<>();
        try {
            lines = Files.readAllLines(employeesTxt);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // The order the information is stored is as follows:
        // first name; last name; role(s); availability (ex: "Friday Lunch"); rating (1 - 10); hourly rate (float);
        // preferred weekly hours (int); active (boolean); start date (mm.dd.yyyy); end date (mm.dd.yyyy); days off
        for(String line : lines) {

            String first            = getInfo("firstName");
            String last             = getInfo("lastName");
            List<Role> roleLocal    = getRolesFromList(getInfoList("roles"));
            List<Shift> avail       = getShiftsFromList(getInfoList("availability"));
            int rating              = Integer.valueOf(getInfo("rating"));
            float hourlyRate        = Float.valueOf(getInfo("hourlyRate"));
            int weeklyHours         = Integer.valueOf(getInfo("preferredWeeklyHours"));
            boolean active          = Boolean.valueOf(getInfo("active"));
            String startDate        = (String) getInfo("startDate");
            String endDate          = (String) getInfo("endDate");
            List<String> daysOff    = getInfoList("daysOff");

            Employee emp = new Employee(
                    first,
                    last,
                    roleLocal,
                    avail,
                    rating,
                    hourlyRate,
                    weeklyHours,
                    active
            );
            //setstartdate etc
            employeeLocal.add(emp);
        }

        return employeeLocal;
    }

    //TODO
    private List<Role> parseRoleText() {

        return new ArrayList<>();
    }

    //TODO
    private String getInfo(String tag) {

        return "";
    }

    //TODO
    private List<String> getInfoList(String tag) {

        return new ArrayList<>();
    }

    //TODO
    private List<Role> getRolesFromList(List<String> strs) {
        return new ArrayList<>();
    }

    //TODO
    private List<Shift> getShiftsFromList(List<String> strs) {
        return new ArrayList<>();
    }

    /**
     * @return The last opened Excel (.xlsx) File.
     */
    public File getExcelFile() {
        return excelFile;
    }
}