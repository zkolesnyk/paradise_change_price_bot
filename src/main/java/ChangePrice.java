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
    private static final List<Product> productList = Bot.getProductList();

//        for (Product product : productList) {
//            System.out.println(product.getFullName() + " — " + product.getPrice() + " грн.");
//        }
//    public static void writeDescriptionToOldFile() throws IOException{
//        File dataFile = new File("/Users/yevheniikolesnyk/Мой диск/paradise/descriptions.xlsx");
//        File productsFromSite = new File("/Users/yevheniikolesnyk/Мой диск/paradise/site.xlsx");
//        // Read XSL file
//        FileInputStream inputStreamNew = new FileInputStream(dataFile);
//        FileInputStream inputStreamOld = new FileInputStream(productsFromSite);
//
//        // Get the workbook instance for XLS file
//        XSSFWorkbook dataWorkbook = new XSSFWorkbook(inputStreamNew);
//        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamOld);
//
//        // Get first sheet from the workbook
//        XSSFSheet dataSheet = dataWorkbook.getSheetAt(0);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//
//        for (int i = 0; i < 2000; i++) {
//            if (dataSheet.getRow(i) != null) {
//                XSSFRow row = dataSheet.getRow(i);
//                XSSFCell dataCellSKU = row.getCell(0);
//                XSSFCell dataCellHashMap = row.getCell(3);
//
//                int dataSKU = Integer.parseInt(dataCellSKU.toString().replace(".0", ""));
//
//                Map<String, String> productQuantity = Splitter.on(", ")
//                        .withKeyValueSeparator("=")
//                        .split(dataCellHashMap.toString().replace("{", "").replace("}", ""));
//
//                int sum = 0;
//                int balloons = 0;
//                StringBuilder description = new StringBuilder();
//                description.append("<p>Композиція гелієвих кульок складається з:</p>\n");
//                description.append("<ul class=\"product-description\">\n");
//                for (String j : productQuantity.keySet()) {
//                    Product product = getProductById(Integer.parseInt(j));
//                    description.append(String.format("<li>%s — %s шт.</li>\n", getProductById(Integer.parseInt(j)).getFullName(), productQuantity.get(j)));
//                    if (product.isBalloon()) {
//                        balloons += Integer.parseInt(productQuantity.get(j));
//                    }
//                    sum += Integer.parseInt(productQuantity.get(j)) * product.getPrice();
//                }
//
//                description.append("</ul>");
//                description.append("\n<p>Всього кульок у наборі: ").append(balloons).append(" шт.").append("</p>");
//
//                description.append("\n\n<ul><br>");
//                if (productQuantity.containsKey("20") || productQuantity.containsKey("21") || productQuantity.containsKey("34") || productQuantity.containsKey("39")) {
//                    description.append("\n<li class=\"description_alert\">Цифри у наборі можна замінити на будь-які інші.</li>");
//                }
//                if (productQuantity.containsKey("14") || productQuantity.containsKey("15") || productQuantity.containsKey("16") || productQuantity.containsKey("32") || productQuantity.containsKey("8") || productQuantity.containsKey("17")) {
//                    description.append("\n<li class=\"description_alert\">Написи на кульках можна змінити за вашим бажанням.</li>");
//                }
//                if (productQuantity.containsKey("9")) {
//                    description.append("\n<li class=\"description_alert\">Напис на коробці можна змінити за вашим бажанням.</li>");
//                }
//                if (productQuantity.keySet().size() > 2 ) {
//                    if (sum < 2000) {
//                        description.append("\n<li class=\"description_delivery\">Для безкоштовної доставки цього набору потрібно додати товарів ще на ").append(2000 - sum).append(" грн.</li>");
//                    } else {
//                        description.append("\n<li class=\"description_delivery\">Можлива безкоштовна доставка цього набору. Додайте набір у кошик, щоб подивитись деталі.</li>");
//                    }
//                    description.append("\n<li class=\"description_flowers\">Додайте квіти до замовлення: <a href=\"/floristika-2/\">розділ «Флористика»</a></li>");
//                }
//                description.append("</ul>");
//
//                System.out.println(dataSKU);
//                System.out.println(description);
//
//
//                for (int j = 1; j < 2000; j++) {
//                    if (sheet.getRow(j) != null) {
//                        XSSFRow rowProductsFile = sheet.getRow(j);
//                        XSSFCell cellOldSKU = rowProductsFile.getCell(0);
//                        int SKU = Integer.parseInt(cellOldSKU.toString().replace(".0", ""));
//                        if (dataSKU == SKU) {
//
//                            XSSFCell cellDescription = rowProductsFile.getCell(29);
//                            cellDescription.setCellValue(description.toString());
//                        }
//                    } else break;
//                }
//            } else {
//                break;
//            }
//        }
//
//        inputStreamNew.close();
//        inputStreamOld.close();
//
//        // Write File
//        FileOutputStream outNew = new FileOutputStream(dataFile);
//        FileOutputStream outOld = new FileOutputStream(productsFromSite);
//        dataWorkbook.write(outNew);
//        workbook.write(outOld);
//        outNew.close();
//        outOld.close();
//    }
//
//    public static void writePriceWithoutDiscountToNewFile() throws IOException{
//        File dataFile = new File("/Users/yevheniikolesnyk/Мой диск/paradise/descriptions.xlsx");
//        File productsFromSite = new File("/Users/yevheniikolesnyk/Мой диск/paradise/site.xlsx");
//        // Read XSL file
//        FileInputStream inputStreamNew = new FileInputStream(dataFile);
//        FileInputStream inputStreamOld = new FileInputStream(productsFromSite);
//
//        // Get the workbook instance for XLS file
//        XSSFWorkbook dataWorkbook = new XSSFWorkbook(inputStreamNew);
//        XSSFWorkbook workbook = new XSSFWorkbook(inputStreamOld);
//
//        // Get first sheet from the workbook
//        XSSFSheet sheetNew = dataWorkbook.getSheetAt(0);
//        XSSFSheet sheetOld = workbook.getSheetAt(0);
//
//
//        for (int i = 0; i < 2000; i++) {
//            if (sheetNew.getRow(i) != null) {
//                XSSFRow dataRow = sheetNew.getRow(i);
//                XSSFCell dataCellSKU = dataRow.getCell(0);
//                XSSFCell dataCellPriceWithDiscount = dataRow.getCell(9);
//                int skuNew = Integer.parseInt(dataCellSKU.toString().replace(".0", ""));
//
//                for (int j = 1; j < 2000; j++) {
//                    if (sheetOld.getRow(j) != null) {
//                        XSSFRow rowOld = sheetOld.getRow(j);
//                        XSSFCell cellOldSKU = rowOld.getCell(0);
//                        int skuOld = Integer.parseInt(cellOldSKU.toString().replace(".0", ""));
//                        if (skuNew == skuOld) {
//
//                            XSSFCell cellOldPrice = rowOld.getCell(16);
//                            cellOldPrice.setCellValue(String.valueOf(dataCellPriceWithDiscount));
//                        }
//                    } else break;
//                }
//            } else {
//                break;
//            }
//        }
//
//        inputStreamNew.close();
//        inputStreamOld.close();
//
//        // Write File
//        FileOutputStream outNew = new FileOutputStream(dataFile);
//        FileOutputStream outOld = new FileOutputStream(productsFromSite);
//        dataWorkbook.write(outNew);
//        workbook.write(outOld);
//        outNew.close();
//        outOld.close();
//    }
    public static void main(String[] args) throws IOException {
//        writeNewPriceToOldFile();
//        writeDescriptionToOldFile();
//        writePriceWithoutDiscountToNewFile();
        updatePriceAndDescriptionsFromDataFileToDataFile();
    }

    public static void updatePriceAndDescriptionsFromDataFileToDataFile() throws IOException {
        File dataFile = new File("/Users/yevheniikolesnyk/Мой диск/paradise/descriptions.xlsx");
        File productsFromSiteFile = new File("/Users/yevheniikolesnyk/Мой диск/paradise/site.xlsx");
        File productsFromCRMFile = new File("/Users/yevheniikolesnyk/Мой диск/paradise/crm.xlsx");
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
//        XSSFSheet specialsSheet = productsWorkbook.getSheetAt(2);
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
                    if (sum < 2000) {
                        description.append("\n<li class=\"description_delivery\">Для безкоштовної доставки цього набору потрібно додати товарів ще на ").append(2000 - sum).append(" грн.</li>");
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
                            price.setCellValue(String.valueOf(sum));
//                            price.setCellValue(String.valueOf(priceWithoutDiscountAmount));
                            XSSFCell descriptionProductCell = productRow.getCell(29);
                            descriptionProductCell.setCellValue(description.toString());
                        }
                    } else break;
                }

//                for (int j = 1; j < 2000; j++) {
//                    if (specialsSheet.getRow(j) != null) {
//                        XSSFRow rowOld = specialsSheet.getRow(j);
//                        XSSFCell productCellSKU = rowOld.getCell(0);
//                        int productSKU = Integer.parseInt(productCellSKU.toString().replace(".0", ""));
//                        if (dataSKU == productSKU) {
//
//                            XSSFCell oldPrice = rowOld.getCell(3);
//                            oldPrice.setCellValue(sum);
//                        }
//                    } else break;
//                }

                for (int j = 1; j < 2000; j++) {
                    if (crmSheet.getRow(j) != null) {
                        XSSFRow rowCRM = crmSheet.getRow(j);
                        XSSFCell productCellSKU = rowCRM.getCell(11);
                        System.out.println("sku: " + productCellSKU.toString());
                        int productSKU = Integer.parseInt(productCellSKU.toString().replace(".0", ""));
                        if (dataSKU == productSKU) {

                            XSSFCell oldPrice = rowCRM.getCell(14);
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
