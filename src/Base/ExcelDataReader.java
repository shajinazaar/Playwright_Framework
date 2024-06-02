package Base;

import org.apache.poi.ss.usermodel.*;

import java.io.File;

public class ExcelDataReader {

    public static Object[][] getDataFromExcel(String sheetName, String filePath) throws Exception {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum() + 1; // +1 to account for header row
        int colCount = sheet.getRow(0).getLastCellNum(); // Assuming header row exists

        Object[][] data = new Object[rowCount - 1][colCount]; // Exclude header row

        for (int i = 1; i < rowCount; i++) { // Start from row 1 (skipping header)
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                String cellValue = cell != null ? cell.getStringCellValue() : "";
                data[i - 1][j] = cellValue;
            }
        }

        return data;
    }
}