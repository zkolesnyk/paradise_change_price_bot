import com.google.common.base.Splitter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangePrice {

    private static List<Product> productList = new ArrayList<>();
    static {
        productList.add(new Product(1, "Латексна гелієва кулька без малюнка", "12д без мал", 65, true));
        productList.add(new Product(2, "Латексна гелієва кулька з малюнком/написом", "12д з мал", 70, true));
        productList.add(new Product(3, "Латексна гелієва кулька хром", "Хром", 80, true));
        productList.add(new Product(4, "Латексна гелієва кулька агат", "Агат", 90, true));
        productList.add(new Product(5, "Латексна гелієва кулька з конфетті", "Конфетті 12д", 80, true));
        productList.add(new Product(12, "Латексна гелієва кулька з індивідуальним написом", "12д з індивід", 105, true));
        productList.add(new Product(13, "Прозорий гігант з конфетті", "Гігант з конфетті без нап", 850, true));
        productList.add(new Product(14, "Прозорий гігант з конфетті та написом", "Гігант з конфетті та нап", 1000, true));
        productList.add(new Product(15, "Латексний кольоровий гігант з написом", "Гігант з написом", 900, true));
        productList.add(new Product(16, "Дзеркальний гігант з написом", "Дзеркальний з написом", 1000, true));
        productList.add(new Product(31, "Bubble з пір’ям або кульками без напису", "Bubble без напису", 650, true));
        productList.add(new Product(32, "Bubble з пір’ям або кульками з написом", "Bubble з написом", 750, true));
        productList.add(new Product(6, "Фольгована гелієва кулька 18д без малюнка", "18д без мал", 130, true));
        productList.add(new Product(7, "Фольгована гелієва кулька 18д з малюнком", "18д з мал", 190, true));
        productList.add(new Product(8, "Фольгована гелієва кулька 18д з індивідуальним написом", "18д з індивід", 190, true));
        productList.add(new Product(33, "Фольговане серце/зірка гігант без напису", "Фольга гігант без напису", 550, true));
        productList.add(new Product(17, "Фольговане серце/зірка гігант з написом", "Фольга гігант з написом", 700, true));
        productList.add(new Product(9, "Коробка-сюрприз з індивідуальним написом", "Коробка з написом", 650, false));
        productList.add(new Product(30, "Коробка-сюрприз з кольоровою наліпкою", "Коробка з наклейкою", 700, false));
        productList.add(new Product(20, "Фольгована цифра 96 см", "Цифра 96 см", 400, true));
        productList.add(new Product(21, "Фольгована цифра 70 см", "Цифра 70 см", 310, true));
        productList.add(new Product(34, "Цифра з повітрям на підставці", "Цифра з повітр", 550, true));
        productList.add(new Product(22, "Ходяча фольгована фігура Міккі/Мінні", "Ходячка Міккі/Мінні", 1350, true));
        productList.add(new Product(23, "Ходяча фольгована фігура", "Ходячки всі", 1550, true));
        productList.add(new Product(25, "Велика фольгована фігура", "Фігура за 300", 300, true));
        productList.add(new Product(26, "Велика фольгована фігура", "Фігура за 350", 350, true));
        productList.add(new Product(27, "Велика фольгована фігура", "Фігура за 360", 360, true));
        productList.add(new Product(28, "Велика фольгована фігура", "Фігура за 400", 400, true));
        productList.add(new Product(29, "Велика фольгована фігура", "Фігура за 450", 450, true));
        productList.add(new Product(10, "Вантаж декоративний", "Груз", 10, false));
        productList.add(new Product(11, "Транспортувальний пакет", "Пакет", 30, false));
        productList.add(new Product(18, "Китиця тассел", "Китиця", 40, false));
        productList.add(new Product(19, "Спіраль тассел", "Спіраль", 100, false));
        productList.add(new Product(24, "Фольгована кулька сфера", "Сфера", 400, true));
        productList.add(new Product(35, "Гендерна кулька з кульками та конфетті всередині", "Гендерна", 950, true));
        productList.add(new Product(36, "Повітряна композиція з місяцем та пупсом", "Повітряна комп", 600, true));
        productList.add(new Product(37, "Латексний кольоровий гігант", "Гігант без напису", 750, true));
        productList.add(new Product(38, "Латексний кольоровий гігант у вигляді тваринки", "Гігант тваринка", 1200, true));
        productList.add(new Product(39, "Каркасна цифра аеромозаїка", "Аеромозаїка", 1500, false));
        productList.add(new Product(40, "?", "?", 1200, false));

    }
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
                int newPrice = Integer.parseInt(cellNewPrice.toString().replace(".0", ""));

                int skuNew = Integer.parseInt(cellNewSKU.toString().replace(".0", ""));

                Map<String, String> productQuantity = Splitter.on(", ")
                        .withKeyValueSeparator("=")
                        .split(cellNewHashMap.toString().replace("{", "").replace("}", ""));

                int sum = 0;
                for (String k : productQuantity.keySet()) {
                    Product product = getProductById(Integer.parseInt(k));
                    sum += Integer.parseInt(productQuantity.get(k)) * product.getPrice();
                }

                System.out.println(skuNew);
//                System.out.println(productQuantity);
                System.out.println("bundle cost: " + sum);


                for (int j = 1; j < 2000; j++) {
                    if (sheetOld.getRow(j) != null) {
                        XSSFRow rowOld = sheetOld.getRow(j);
                        XSSFCell cellOldSKU = rowOld.getCell(0);
                        int skuOld = Integer.parseInt(cellOldSKU.toString().replace(".0", ""));
                        if (skuNew == skuOld) {

                            XSSFCell cellOldPrice = rowOld.getCell(16);
                            cellOldPrice.setCellValue(sum);

                            XSSFCell cellOldDescription = rowOld.getCell(29);
                            cellOldDescription.setCellValue(cellNewDescription.toString());
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

    public static Product getProductById(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
