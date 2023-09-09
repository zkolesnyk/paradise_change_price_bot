import com.google.common.base.Splitter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ChangePrice {
    private static final List<Product> productList = new ArrayList<>();
    static {
        productList.add(new Product(1, "Латексна гелієва кулька без малюнка", "12д без мал", 65, true));
        productList.add(new Product(2, "Латексна гелієва кулька з малюнком/написом", "12д з мал", 70, true));
        productList.add(new Product(3, "Латексна гелієва кулька хром", "Хром", 80, true));
        productList.add(new Product(4, "Латексна гелієва кулька агат", "Агат", 90, true));
        productList.add(new Product(5, "Латексна гелієва кулька з конфетті", "Конфетті 12д", 80, true));
        productList.add(new Product(12, "Латексна гелієва кулька з індивідуальним написом", "12д з індивід", 105, true));
        productList.add(new Product(13, "Прозорий гігант з конфетті", "Гігант з конфетті без нап", 950+100, true));
        productList.add(new Product(14, "Прозорий гігант з конфетті та написом", "Гігант з конфетті та нап", 1000+100, true));
        productList.add(new Product(15, "Латексний кольоровий гігант з написом", "Гігант з написом", 900+100, true));
        productList.add(new Product(16, "Дзеркальний гігант з написом", "Дзеркальний з написом", 1000+100, true));
        productList.add(new Product(31, "Bubble з пір’ям або кульками без напису", "Bubble без напису", 650+100, true));
        productList.add(new Product(32, "Bubble з пір’ям або кульками з написом", "Bubble з написом", 750+100, true));
        productList.add(new Product(6, "Фольгована гелієва кулька 18д без малюнка", "18д без мал", 130+20, true));
        productList.add(new Product(7, "Фольгована гелієва кулька 18д з малюнком", "18д з мал", 190+50, true));
        productList.add(new Product(8, "Фольгована гелієва кулька 18д з індивідуальним написом", "18д з індивід", 130+70+20, true));
        productList.add(new Product(33, "Фольговане серце/зірка гігант без напису", "Фольга гігант без напису", 550+100, true));
        productList.add(new Product(17, "Фольговане серце/зірка гігант з написом", "Фольга гігант з написом", 700+100, true));
        productList.add(new Product(9, "Коробка-сюрприз з індивідуальним написом", "Коробка з написом", 650+50, false));
        productList.add(new Product(30, "Коробка-сюрприз з кольоровою наліпкою", "Коробка з наклейкою", 700+50, false));
        productList.add(new Product(20, "Фольгована цифра 96 см", "Цифра 96 см", 450, true));
        productList.add(new Product(21, "Фольгована цифра 70 см", "Цифра 70 см", 310, true));
        productList.add(new Product(34, "Цифра з повітрям на підставці", "Цифра з повітр", 550, true));
        productList.add(new Product(22, "Ходяча фольгована фігура Міккі/Мінні", "Ходячка Міккі/Мінні", 1800, true));
        productList.add(new Product(23, "Ходяча фольгована фігура", "Ходячки всі", 1800, true));
        productList.add(new Product(25, "Велика фольгована фігура", "Фігура за 300", 300+60, true));
        productList.add(new Product(26, "Велика фольгована фігура", "Фігура за 350", 350+60, true));
        productList.add(new Product(27, "Велика фольгована фігура", "Фігура за 360", 360+60, true));
        productList.add(new Product(28, "Велика фольгована фігура", "Фігура за 400", 400+80, true));
        productList.add(new Product(29, "Велика фольгована фігура", "Фігура за 450", 450+70, true));
        productList.add(new Product(10, "Вантаж декоративний", "Груз", 10, false));
        productList.add(new Product(11, "Транспортувальний пакет", "Пакет", 30, false));
        productList.add(new Product(18, "Китиця тассел", "Китиця", 40, false));
        productList.add(new Product(19, "Спіраль тассел", "Спіраль", 100, false));
        productList.add(new Product(24, "Фольгована кулька сфера", "Сфера", 500, true));
        productList.add(new Product(35, "Гендерна кулька з кульками та конфетті всередині", "Гендерна", 950+100, true));
        productList.add(new Product(36, "Повітряна композиція з місяцем та пупсом", "Повітряна комп", 600, true));
        productList.add(new Product(37, "Латексний кольоровий гігант", "Гігант без напису", 750+100, true));
        productList.add(new Product(38, "Латексний кольоровий гігант у вигляді тваринки", "Гігант тваринка", 1200+100, true));
        productList.add(new Product(39, "Каркасна цифра аеромозаїка", "Аеромозаїка", 1500, false));
        productList.add(new Product(40, "Bubble з квітами", "Bubble з квітами", 2200, false));
        productList.add(new Product(41, "Коробка з трояндами та ведмідь", "Коробка+медведь", 2200+2000, false));
        productList.add(new Product(42, "Кульки з повітрям на підлогу", "На підлогу", 10, false));
        productList.add(new Product(43, "Індивідуальна гірлянда на кульку", "Гірлянда на кульку", 50, false));
        productList.add(new Product(44, "Індивідуальний дощик на кульку", "Дощик на кульку", 40, false));
        productList.add(new Product(45, "Дзеркальний гігант", "Дзеркальний без напису", 850+100, true));
        productList.add(new Product(46, "Зірки з повітрям на підлогу", "Зірки на підлогу", 70, false));
        productList.add(new Product(47, "Корзина з повітряною кулею з кульок", "Шар с корзиной", 4500, false));


    }

    public static void writeDescriptionToOldFile() throws IOException{
        File dataFile = new File("C:/paradise/descriptions.xlsx");
        File productsFromSite = new File("C:/paradise/old.xlsx");
        // Read XSL file
        FileInputStream inputStreamNew = new FileInputStream(dataFile);
        FileInputStream inputStreamOld = new FileInputStream(productsFromSite);

        // Get the workbook instance for XLS file
        XSSFWorkbook dataWorkbook = new XSSFWorkbook(inputStreamNew);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamOld);

        // Get first sheet from the workbook
        XSSFSheet dataSheet = dataWorkbook.getSheetAt(0);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = 0; i < 2000; i++) {
            if (dataSheet.getRow(i) != null) {
                XSSFRow row = dataSheet.getRow(i);
                XSSFCell dataCellSKU = row.getCell(0);
                XSSFCell dataCellHashMap = row.getCell(3);

                int dataSKU = Integer.parseInt(dataCellSKU.toString().replace(".0", ""));

                Map<String, String> productQuantity = Splitter.on(", ")
                        .withKeyValueSeparator("=")
                        .split(dataCellHashMap.toString().replace("{", "").replace("}", ""));

                int sum = 0;
                int balloons = 0;
                StringBuilder description = new StringBuilder();
                description.append("<p>Композиція гелієвих кульок складається з:</p>\n");
                description.append("<ul class=\"product-description\">\n");
                for (String j : productQuantity.keySet()) {
                    Product product = getProductById(Integer.parseInt(j));
                    description.append(String.format("<li>%s — %s шт.</li>\n", getProductById(Integer.parseInt(j)).getFullName(), productQuantity.get(j)));
                    if (product.isBalloon()) {
                        balloons += Integer.parseInt(productQuantity.get(j));
                    }
                    sum += Integer.parseInt(productQuantity.get(j)) * product.getPrice();
                }

                description.append("</ul>");
                description.append("\n<p>Всього кульок у наборі: ").append(balloons).append(" шт.").append("</p>");

                description.append("\n\n<ul><br>");
                if (productQuantity.containsKey("20") || productQuantity.containsKey("21") || productQuantity.containsKey("34") || productQuantity.containsKey("39")) {
                    description.append("\n<li class=\"description_alert\">Цифри у наборі можна замінити на будь-які інші.</li>");
                }
                if (productQuantity.containsKey("14") || productQuantity.containsKey("15") || productQuantity.containsKey("16") || productQuantity.containsKey("32") || productQuantity.containsKey("8") || productQuantity.containsKey("17")) {
                    description.append("\n<li class=\"description_alert\">Написи на кульках можна змінити за вашим бажанням.</li>");
                }
                if (productQuantity.containsKey("9")) {
                    description.append("\n<li class=\"description_alert\">Напис на коробці можна змінити за вашим бажанням.</li>");
                }
                if (productQuantity.keySet().size() > 2 ) {
                    if (sum < 1700) {
                        description.append("\n<li class=\"description_delivery\">Для безкоштовної доставки цього набору потрібно додати товарів ще на ").append(1700 - sum).append(" грн.</li>");
                    } else {
                        description.append("\n<li class=\"description_delivery\">Можлива безкоштовна доставка цього набору. Додайте набір у кошик, щоб подивитись деталі.</li>");
                    }
                    description.append("\n<li class=\"description_flowers\">Додайте квіти до замовлення: <a href=\"/floristika-2/\">розділ «Флористика»</a></li>");
                }
                description.append("</ul>");

                System.out.println(dataSKU);
                System.out.println(description);


                for (int j = 1; j < 2000; j++) {
                    if (sheet.getRow(j) != null) {
                        XSSFRow rowProductsFile = sheet.getRow(j);
                        XSSFCell cellOldSKU = rowProductsFile.getCell(0);
                        int SKU = Integer.parseInt(cellOldSKU.toString().replace(".0", ""));
                        if (dataSKU == SKU) {

                            XSSFCell cellDescription = rowProductsFile.getCell(29);
                            cellDescription.setCellValue(description.toString());
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
        FileOutputStream outNew = new FileOutputStream(dataFile);
        FileOutputStream outOld = new FileOutputStream(productsFromSite);
        dataWorkbook.write(outNew);
        workbook.write(outOld);
        outNew.close();
        outOld.close();
    }

    public static void writePriceWithoutDiscountToNewFile() throws IOException{
        File dataFile = new File("C:/paradise/descriptions.xlsx");
        File productsFromSite = new File("C:/paradise/old.xlsx");
        // Read XSL file
        FileInputStream inputStreamNew = new FileInputStream(dataFile);
        FileInputStream inputStreamOld = new FileInputStream(productsFromSite);

        // Get the workbook instance for XLS file
        XSSFWorkbook dataWorkbook = new XSSFWorkbook(inputStreamNew);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamOld);

        // Get first sheet from the workbook
        XSSFSheet sheetNew = dataWorkbook.getSheetAt(0);
        XSSFSheet sheetOld = workbook.getSheetAt(0);


        for (int i = 0; i < 2000; i++) {
            if (sheetNew.getRow(i) != null) {
                XSSFRow dataRow = sheetNew.getRow(i);
                XSSFCell dataCellSKU = dataRow.getCell(0);
                XSSFCell dataCellPriceWithDiscount = dataRow.getCell(9);
                int skuNew = Integer.parseInt(dataCellSKU.toString().replace(".0", ""));

                for (int j = 1; j < 2000; j++) {
                    if (sheetOld.getRow(j) != null) {
                        XSSFRow rowOld = sheetOld.getRow(j);
                        XSSFCell cellOldSKU = rowOld.getCell(0);
                        int skuOld = Integer.parseInt(cellOldSKU.toString().replace(".0", ""));
                        if (skuNew == skuOld) {

                            XSSFCell cellOldPrice = rowOld.getCell(16);
                            cellOldPrice.setCellValue(String.valueOf(dataCellPriceWithDiscount));
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
        FileOutputStream outNew = new FileOutputStream(dataFile);
        FileOutputStream outOld = new FileOutputStream(productsFromSite);
        dataWorkbook.write(outNew);
        workbook.write(outOld);
        outNew.close();
        outOld.close();
    }
    public static void main(String[] args) throws IOException {
//        writeNewPriceToOldFile();
//        writeDescriptionToOldFile();
//        writePriceWithoutDiscountToNewFile();
        updatePriceAndDescriptionsFromDataFileToDataFile();
    }

    public static void updatePriceAndDescriptionsFromDataFileToDataFile() throws IOException {
        File dataFile = new File("C:/paradise/descriptions.xlsx");
        File productsFromSiteFile = new File("C:/paradise/old.xlsx");
        File productsFromCRMFile = new File("C:/paradise/crm.xlsx");
        // Read XSL file
        FileInputStream dataInputStream = new FileInputStream(dataFile);
        FileInputStream productsFromSiteInputStream = new FileInputStream(productsFromSiteFile);
        FileInputStream productsFromCRMInputStream = new FileInputStream(productsFromCRMFile);
        // Get the workbook instance for XLS file
        XSSFWorkbook dataWorkbook = new XSSFWorkbook(dataInputStream);
        XSSFWorkbook productsWorkbook = new XSSFWorkbook(productsFromSiteInputStream);
        XSSFWorkbook CRMWorkbook = new XSSFWorkbook(productsFromCRMInputStream);
        // Get first sheet from the workbook
        XSSFSheet dataSheet = dataWorkbook.getSheetAt(0);
        XSSFSheet productsSheet = productsWorkbook.getSheetAt(0);
        XSSFSheet specialsSheet = productsWorkbook.getSheetAt(2);
        XSSFSheet crmSheet = CRMWorkbook.getSheetAt(0);

        for (int i = 0; i < 2000; i++) {
            if (dataSheet.getRow(i) != null) {
                XSSFRow row = dataSheet.getRow(i);
                XSSFCell dataCellSKU = row.getCell(0);
                XSSFCell dataCellHashMap = row.getCell(3);

                int dataSKU = Integer.parseInt(dataCellSKU.toString().replace(".0", ""));

                Map<String, String> productQuantity = Splitter.on(", ")
                        .withKeyValueSeparator("=")
                        .split(dataCellHashMap.toString().replace("{", "").replace("}", ""));

                int sum = 0;
                int balloons = 0;
                StringBuilder description = new StringBuilder();
                description.append("<p>Композиція гелієвих кульок складається з:</p>\n");
                description.append("<ul class=\"product-description\">\n");
                for (String j : productQuantity.keySet()) {
                    Product product = getProductById(Integer.parseInt(j));
                    description.append(String.format("<li>%s — %s шт.</li>\n", getProductById(Integer.parseInt(j)).getFullName(), productQuantity.get(j)));
                    if (product.isBalloon()) {
                        balloons += Integer.parseInt(productQuantity.get(j));
                    }
                    sum += Integer.parseInt(productQuantity.get(j)) * product.getPrice();
                }

                description.append("</ul>");
                description.append("\n<p>Всього кульок у наборі: ").append(balloons).append(" шт.").append("</p>");

                description.append("\n\n<ul><br>");
                if (productQuantity.containsKey("20") || productQuantity.containsKey("21") || productQuantity.containsKey("34") || productQuantity.containsKey("39")) {
                    description.append("\n<li class=\"description_alert\">Цифри у наборі можна замінити на будь-які інші.</li>");
                }
                if (productQuantity.containsKey("14") || productQuantity.containsKey("15") || productQuantity.containsKey("16") || productQuantity.containsKey("32") || productQuantity.containsKey("8") || productQuantity.containsKey("17")) {
                    description.append("\n<li class=\"description_alert\">Написи на кульках можна змінити за вашим бажанням.</li>");
                }
                if (productQuantity.containsKey("9")) {
                    description.append("\n<li class=\"description_alert\">Напис на коробці можна змінити за вашим бажанням.</li>");
                }
                if (productQuantity.keySet().size() > 2 ) {
                    if (sum < 1700) {
                        description.append("\n<li class=\"description_delivery\">Для безкоштовної доставки цього набору потрібно додати товарів ще на ").append(1700 - sum).append(" грн.</li>");
                    } else {
                        description.append("\n<li class=\"description_delivery\">Можлива безкоштовна доставка цього набору. Додайте набір у кошик, щоб подивитись деталі.</li>");
                    }
                    description.append("\n<li class=\"description_flowers\">Додайте квіти до замовлення: <a href=\"/floristika-2/\">розділ «Флористика»</a></li>");
                }
                description.append("</ul>");

                System.out.println(dataSKU);
                System.out.println(description);
//
//                XSSFCell descriptionCell = row.getCell(1);
//                descriptionCell.setCellValue(description.toString());
                XSSFCell priceCell = row.getCell(2);
                priceCell.setCellValue(sum);
                XSSFCell priceWithoutDiscount = row.createCell(4);
                int min = 10;
                int max = 20;
                int diff = max - min;
                Random random = new Random();
                int discount = random.nextInt(diff + 1);
                discount += min;
                System.out.println(discount);
                long priceWithoutDiscountAmount = (Math.round(sum / (1 - discount * 0.01)) + 10) / 10 * 10;
                priceWithoutDiscount.setCellValue(priceWithoutDiscountAmount);

                // change price without discount and description
                for (int j = 1; j < 2000; j++) {
                    if (productsSheet.getRow(j) != null) {
                        XSSFRow productRow = productsSheet.getRow(j);
                        XSSFCell productCellSKU = productRow.getCell(0);
                        int productSKU = Integer.parseInt(productCellSKU.toString().replace(".0", ""));
                        if (dataSKU == productSKU) {

                            XSSFCell price = productRow.getCell(16);
                            price.setCellValue(String.valueOf(priceWithoutDiscountAmount));
                            XSSFCell descriptionProductCell = productRow.getCell(29);
                            descriptionProductCell.setCellValue(description.toString());
                        }
                    } else break;
                }

                for (int j = 1; j < 2000; j++) {
                    if (specialsSheet.getRow(j) != null) {
                        XSSFRow rowOld = specialsSheet.getRow(j);
                        XSSFCell productCellSKU = rowOld.getCell(0);
                        int productSKU = Integer.parseInt(productCellSKU.toString().replace(".0", ""));
                        if (dataSKU == productSKU) {

                            XSSFCell oldPrice = rowOld.getCell(3);
                            oldPrice.setCellValue(sum);
                        }
                    } else break;
                }

                for (int j = 1; j < 2000; j++) {
                    if (crmSheet.getRow(j) != null) {
                        XSSFRow rowCRM = crmSheet.getRow(j);
                        XSSFCell productCellSKU = rowCRM.getCell(11);
                        System.out.println("sku: " + productCellSKU.toString());
                        int productSKU = Integer.parseInt(productCellSKU.toString().replace(".0", ""));
                        if (dataSKU == productSKU) {

                            XSSFCell oldPrice = rowCRM.getCell(13);
                            oldPrice.setCellValue(sum);
                            XSSFCell oldDescription = rowCRM.getCell(2);
                            oldDescription.setCellValue(description.toString());
                        }
                    } else break;
                }

            } else {
                break;
            }
        }

        dataInputStream.close();
        productsFromSiteInputStream.close();
        productsFromCRMInputStream.close();

        // Write File
        FileOutputStream outNew = new FileOutputStream(dataFile);
        FileOutputStream outOld = new FileOutputStream(productsFromSiteFile);
        FileOutputStream crmOut = new FileOutputStream(productsFromCRMFile);

        dataWorkbook.write(outNew);
        productsWorkbook.write(outOld);
        CRMWorkbook.write(crmOut);

        outNew.close();
        outOld.close();
        crmOut.close();
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
