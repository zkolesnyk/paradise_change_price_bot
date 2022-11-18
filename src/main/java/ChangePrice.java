import com.google.common.base.Splitter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChangePrice {
    public static void main(String[] args) throws IOException {

        File newFile = new File("C:/paradise/descriptions.xlsx");
        File oldFile = new File("C:/paradise/old.xlsx");
        // Read XSL file
        FileInputStream inputStreamNew = new FileInputStream(newFile);
        FileInputStream inputStreamOld = new FileInputStream(oldFile);

        // Get the workbook instance for XLS file
        XSSFWorkbook workbookNew = new XSSFWorkbook(inputStreamNew);
        XSSFWorkbook workbookOld = new XSSFWorkbook(inputStreamOld);

        // Get first sheet from the workbook
        XSSFSheet sheetNew = workbookNew.getSheetAt(0);
        XSSFSheet sheetOld = workbookOld.getSheetAt(0);


        for (int i = 0; i < 2000; i++) {
            if (sheetNew.getRow(i) != null) {
                XSSFRow rowNew = sheetNew.getRow(i);
                XSSFCell cellNewSKU = rowNew.getCell(0);
                XSSFCell cellNewHashMap = rowNew.getCell(3);
                XSSFCell cellNewPrice = rowNew.getCell(2);
                XSSFCell cellNewDescription = rowNew.getCell(1);
                System.out.println(cellNewDescription);
                int newPrice = Integer.parseInt(cellNewPrice.toString().replace(".0", ""));

                int skuNew = Integer.parseInt(cellNewSKU.toString().replace(".0", ""));

                Map<String, String> productQuantity = Splitter.on(", ")
                        .withKeyValueSeparator("=")
                        .split(cellNewHashMap.toString().replace("{", "").replace("}", ""));

                System.out.println(skuNew);
                System.out.println(productQuantity);
                for (int j = 1; j < 2000; j++) {
                    if (sheetOld.getRow(j) != null) {
                        XSSFRow rowOld = sheetOld.getRow(j);
                        XSSFCell cellOldSKU = rowOld.getCell(0);
                        int skuOld = Integer.parseInt(cellOldSKU.toString().replace(".0", ""));
                        if (skuNew == skuOld) {

                            XSSFCell cellOldPrice = rowOld.getCell(16);
                            cellOldPrice.setCellValue(newPrice);

                            XSSFCell cellOldDescription = rowOld.getCell(29);
                            cellOldDescription.setCellValue(cellNewDescription.toString());

                            System.out.println("old sku: " + skuOld);
                            System.out.println("old price " + cellOldPrice);
                        }
                    } else break;
                }
            } else {
                break;
            }
        }

        inputStreamNew.close();
        inputStreamOld.close();

        // Write File
        FileOutputStream outNew = new FileOutputStream(newFile);
        FileOutputStream outOld = new FileOutputStream(oldFile);
        workbookNew.write(outNew);
        workbookOld.write(outOld);
        outNew.close();
        outOld.close();
    }
}
