import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateExcel {

    public static void createRow(int sku, String description, int price, String array) throws IOException {

        File file = new File("/Users/yevheniikolesnyk/Мой диск/paradise/descriptions.xlsx");
        // Read XSL file
        FileInputStream inputStream = new FileInputStream(file);

        // Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        // Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        int emptyRow = 0;
        for (int i = 0; i < 2000; i++) {
            if (sheet.getRow(i) != null) {
            } else {
                emptyRow = i;
                break;
            }
        }

        XSSFRow newRow = sheet.createRow(emptyRow);
        XSSFCell cell0 = newRow.createCell(0);
        cell0.setCellValue(sku);
        XSSFCell cell1 = newRow.createCell(1);
        cell1.setCellValue(description);
        XSSFCell cell2 = newRow.createCell(2);
        cell2.setCellValue(price);
        XSSFCell cell3 = newRow.createCell(3);
        cell3.setCellValue(array);

        System.out.println(emptyRow);

        inputStream.close();
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();

    }

}