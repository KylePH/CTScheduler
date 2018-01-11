package ctscheduler;

import javafx.scene.paint.Color;
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
import java.util.HashMap;
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
        employees = new ArrayList<>();
        roles = new ArrayList<>();
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
            roles = parseRoleText();
            employees = parseEmployeeText();
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
        saveDataHelper(employeesTxt, objListToStringList(employees));
        saveDataHelper(rolesTxt, objListToStringList(roles));
    }

    private List<String> objListToStringList(List<?> list) {
        List<String> li = new ArrayList<>();
        for(Object o : list) {
            li.add(o.toString());
        }
        return li;
    }

    /**
     * Does the actual writing of the text files. This is just to avoid duplicate code.
     * @param saveFile The file to be updated.
     * @param list The data to save to the file.
     */
    private void saveDataHelper(Path saveFile, List<String> list) {
        Path writtenFile = null;
        try {
            writtenFile = Files.write(saveFile, list, StandardOpenOption.WRITE);
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

    /**
     * Loads a role into the FileManager and saves all current data to text files in the AppData folder.
     * @param role Role to be added and saved.
     */
    public void saveRole(Role role) {
        roles.add(role);
        saveData();
    }


    /**
     * Reads each line from the employeesTxt file, and with each line the method then creates one employee object with
     * its fields being filled with the provided data. Then adds that employee to an ArrayList containing type Employee.
     * @return An ArrayList filled with each Employee object contained within the employees text file.
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
        // first name; last name; role(s); availability (ex: "Friday Lunch"); rating (1 - 10); hourlyRate (float);
        // preferredWeeklyHours (int); active (boolean); startDate (mm.dd.yyyy); endDate (mm.dd.yyyy); daysOff;
        for(String line : lines) {

            String first            = getInfo(line, "firstName");
            String last             = getInfo(line, "lastName");
            List<Role> roleLocal    = getRolesList(getInfoList(line, "roles")); //TODO
            List<Shift> avail       = getShiftsList(getInfoList(line, "availability"));
            int rating              = Integer.valueOf(getInfo(line, "rating"));
            float hourlyRate        = Float.valueOf(getInfo(line, "hourlyRate"));
            int weeklyHours         = Integer.valueOf(getInfo(line, "preferredWeeklyHours"));
            boolean active          = Boolean.valueOf(getInfo(line, "active"));
            String startDate        = getInfo(line, "startDate");
            String endDate          = getInfo(line, "endDate");
            List<String> daysOff    = getInfoList(line, "daysOff");

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

            if(startDate != null) {
                emp.setStartDate(startDate);
            }

            if(endDate != null) {
                emp.setEndDate(endDate);
            }

            if (!daysOff.isEmpty()) {
                emp.setDaysOff(daysOff);
            }

            employeeLocal.add(emp);
        }

        return employeeLocal;
    }

    /**
     * Reads through all lines in the roles text file and creates a list containing Role objects associated with
     * the data provided on each line of the text file.
     * @return a list of previously saved roles.
     */
    private List<Role> parseRoleText() {
        List<String> lines = null;
        List<Role> rolesLocal = new ArrayList<>();
        try {
            lines = Files.readAllLines(rolesTxt);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Information is stored as follows:
        // name (String); numPerShift (HashMap<Shift, Integer>); String color;
        for(String line : lines) {
            String name = getInfo(line, "name");
            HashMap<Shift, Integer> numPerShift = getInfoHashMap(line, "numPerShift");
            Color color = Color.web(getInfo(line, "color"));

            rolesLocal.add(new Role(name, numPerShift, color));
        }
        return new ArrayList<>();
    }

    /**
     * Calculates a string between the tag parameter and the following semicolon, non inclusive.
     * Each tag is assumed to be followed by ": " so it should not be included in the parameter.
     * @param line The string to be searched
     * @param tag The keyword being looked for in the String line parameter.
     * @return String containing all characters between line + ": ".
     */
    private String getInfo(String line, String tag) {

        if(!tag.endsWith(": ")) {
            tag += ": ";
        }

        int ind = line.indexOf(tag) + tag.length();

        return line.substring(
                ind,
                line.indexOf(';', ind)
        );
    }

    /**
     * Calls on getInfo to isolate the substring between the tag parameter and the following ";". Then
     * iterates through the resulting string to create a list with each item being the substring between each
     * comma in the main String. Then adds the tail end of the string which should be the last item.
     * @param line The String to be searched.
     * @param tag The String keyword to search for within the line parameter.
     * @return A list with each element being the substrings contained between the commas in the substring between tag and the following semicolon.
     */
    private List<String> getInfoList(String line, String tag) {

        List<String> items = new ArrayList<>();

        String data = getInfo(line, tag);
        while (data.contains(",")) {
            items.add(data.substring(0, data.indexOf(",")));
            data = data.substring(data.indexOf(",") + 2);
        }

        items.add(data);

        return items;
    }

    /**
     * Calls on getInfo to isolate the substring between the tag parameter and the following ";". Then
     * iteates through the resulting string to create a list with each item being the substring between each
     * comma in the main String. The substring has two items in it separated by " - ". The first item represents
     * a Shift object, and the second represents an integer. These values are loaded into a HashMap with the key
     * a value respectively.
     * @param line
     * @param tag
     * @return
     */
    private HashMap<Shift, Integer> getInfoHashMap(String line, String tag) {
        HashMap<Shift, Integer> hashMap = new HashMap<>();


        String data = getInfo(line, tag);
        while (data.contains(",")) {
            hashMap.put(Shift.getShiftFromString(data.substring(0, data.indexOf("-") - 1)),
                    Integer.valueOf(data.substring(data.indexOf("-") + 2, data.indexOf(","))));
            data = data.substring(data.indexOf(",") + 2);
        }

        hashMap.put(Shift.getShiftFromString(data.substring(0, data.indexOf("-") - 1)),
                Integer.valueOf(data.substring(data.indexOf("-") + 2)));

        return hashMap;
    }

    private List<Role> getRolesList(List<String> strs) {
        return new ArrayList<>();
    }


    /**
     * Creates a List of Shift objects based on a List of Strings.
     * @param strs The List of Strings representing Shift objects.
     * @return A List of Shift objects representing the supplied List of Strings.
     */
    private List<Shift> getShiftsList(List<String> strs) {
        List<Shift> tempShifts = new ArrayList<>();
        for(String s : strs) {
            tempShifts.add(Shift.getShiftFromString(s));
        }
        return tempShifts;
    }

    /**
     * @return The last opened Excel (.xlsx) File.
     */
    public File getExcelFile() {
        return excelFile;
    }
}