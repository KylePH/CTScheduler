package ctscheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.controlsfx.control.spreadsheet.Grid;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;

import java.io.File;
import java.io.IOException;
import java.util.List;

class SpreadsheetManager {

    private List<Role> roles;

    public SpreadsheetManager(List<Role> roles) {
        this.roles = roles;
    }

    // TODO: Return a list of grids if the file has more than one Sheet
    // TODO: Proper CSS formatting
    // TODO: Figure out how to apply merged cells/spanning from the excel file to the GridBase (I've done it manually)
    /**
     * Returns a GridBase filled with the data from the Excel (.xlsx) file. Currently
     * hard coded to be formatted properly when loading up a Continental Tavern schedule.
     * @param excelFile Excel file to be copied to the GridBase.
     * @return a GridBase filled with data from the cells in the Excel file.
     */
    public GridBase getGridFromSpreadsheet(File excelFile) {
        Workbook wb;
        try {
            wb = WorkbookFactory.create(excelFile);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            return null;
        }

        DataFormatter formatter = new DataFormatter();
        Sheet sheet = wb.getSheetAt(0);

        int rowCount = 0;
        int columnCount = 0;

        // Set rowCount and columnCount to the furthest cell/row indices that contain text in the Excel spreadsheet.
        // Basically getting the smallest bounds for the spreadsheet without eliminating any data.
        for(Row row : sheet) {
            for(Cell cell : row) {
                if(!formatter.formatCellValue(cell).equals("")) {
                    if(cell.getRowIndex() > rowCount) {
                        rowCount = cell.getRowIndex();
                    }
                    if(cell.getColumnIndex() > columnCount) {
                        columnCount = cell.getColumnIndex();
                    }
                }
            }
        }

        // Return the values retrieved from a zero-indexed environment to their regular values
        rowCount++;
        //columnCount++; Took this out because the last column contains a "key" for colors that aren't displayed yet.

        // Initialize the GridBase with the rowCount and columnCount found above.
        GridBase grid = new GridBase(rowCount, columnCount);

        ObservableList<ObservableList<SpreadsheetCell>> gridRows = FXCollections.observableArrayList();

        // Specifies the row that a role/position "header" is on. I keep track of this because the whole
        // row and the row below it needs to be a certain color. (role.getColorHex())
        int roleRowIndex = -5;

        // If a cell's content matches a role name then this will be set to that role.
        Role currRole = null;

        // Begin iterating through the rowCount and columnCount and getting rows and cells from the
        // excel spreadsheet to add to the gridRows and rowCells lists respectively.
        for(int rowNum = 0; rowNum < grid.getRowCount(); rowNum++) {

            final ObservableList<SpreadsheetCell> rowCells = FXCollections.observableArrayList();

            // Get a row from the Excel spreadsheet.
            Row row = sheet.getRow(rowNum);

            if(row != null) { // Row has cells that contain some data.

                for (int columnNum = 0; columnNum < grid.getColumnCount(); columnNum++) {

                    Cell cell = row.getCell(columnNum); // Get a cell from the row in the Excel spreadsheet.
                    String cellContent = formatter.formatCellValue(cell); // Get its contents in String form.
                    String css = ""; // The CSS to be applied to the cell.
                    int rowSpan = 1; // How many rows the cell will cover.
                    int columnSpan = 1; // How many columns the cell will cover.

                    // Check to see if the cell content matches a Role name.
                    for(Role role : roles) {
                        if(cellContent.equalsIgnoreCase(role.getName())) {
                            // If it does, the role name should be displayed in a cell that spans 2 rows.
                            rowSpan = 2;
                            // Set the index of the row that the role is on and the Role object to local variables.
                            roleRowIndex = rowNum;
                            currRole = role;
                        }
                    }

                    // Set the CSS for cells falling on the same row as the cell that contains a role name.
                    // The cell that contains a role name spans 2 cells, which is the reason for checking
                    // both roleRowIndex and roleRowIndex + 1.
                    if(rowNum == roleRowIndex || rowNum == roleRowIndex + 1) {
                        css += "-fx-background-color: " + currRole.getColorHex() + ";";
                        css += "-fx-text-alignment: center;";
                        css += "-fx-alignment: center;";
                    }

                    // Set the CSS for all cells except for the first column. The first column lists the
                    // names of employees and should all have left alignment, which is the default style.
                    // Some cells in the first column such as role names need center alignment, but that is
                    // set above and is the reason for checking if the css already includes center alignment.
                    if(columnNum != 0 && !css.contains("-fx-alignment: center;")) {
                        css += "-fx-text-alignment: center;";
                        css += "-fx-alignment: center;";
                    }

                    // Set the span and CSS for the first cell in the first row. This is the title cell.
                    // This should eventually be done automatically by getting cell spans from the loaded
                    // spreadsheet from Apache POI.
                    if(rowNum == 0) {
                        columnSpan = columnCount;
                        css += "-fx-text-alignment: center;";
                        css += "-fx-alignment: center;";
                        css += "-fx-underline: true;";
                        css += "-fx-effect:dropshadow(gaussian, black, 10, 0.1, 0, 0);";
                    }

                    // Set the CSS for an alternating row background color to all rows for better visibility.
                    if(rowNum % 2 == 0 && !css.contains("-fx-background-color")) {
                        css += "-fx-background-color: #e6e6e6;";
                    }

                    // Create the ControlsFX cell
                    SpreadsheetCell cellToAdd = SpreadsheetCellType.STRING.createCell(
                            rowNum,
                            columnNum,
                            rowSpan,
                            columnSpan,
                            cellContent
                    );

                    // Set cell WrapText attribute and set the CSS string to cell Style property.
                    cellToAdd.setWrapText(false);
                    cellToAdd.setStyle(css);

                    // Finally add the cell to the rowCells list.
                    rowCells.add(cellToAdd);
                }
                // Add a complete row of cells to the gridRows list.
                gridRows.add(rowCells);
            } else {
                // Excel spreadsheet row is null, load the list with blank cells.
                for(int columnNum = 0; columnNum < grid.getColumnCount(); columnNum++) {
                    rowCells.add(
                            SpreadsheetCellType.STRING.createCell(
                                    rowNum,
                                    columnNum,
                                    1,
                                    1,
                                    ""
                            )

                    );
                }
            }
        }

        // Finally set the full gridRows<ObservableList<ObservableList<SpreadsheetCell>> to the GridBase object.
        grid.setRows(gridRows);

        return grid;

    }

    public void setRoles(List<Role> roles){
        this.roles = roles;
    }
}
