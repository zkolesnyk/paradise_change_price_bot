import com.google.common.base.Splitter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangePrice {
    static {
        // Single-line, readable log format: 2026-06-13 12:00:00 [INFO] message
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT [%4$s] %5$s%6$s%n");
    }

    private static final Logger log = Logger.getLogger(ChangePrice.class.getName());

    private static final String BASE_DIR = "/Users/yevheniikolesnyk/Мій диск/paradise/";

    // SKU column indexes differ per file.
    private static final int DATA_SKU_COLUMN = 0;
    private static final int SITE_SKU_COLUMN = 0;
    private static final int CRM_SKU_COLUMN = 11;

    private static final List<Product> productList = Bot.getProductList();

    public static void main(String[] args) {
//        writeNewPriceToOldFile();
//        writeDescriptionToOldFile();
//        writePriceWithoutDiscountToNewFile();
        try {
            updatePriceAndDescriptionsFromDataFileToDataFile();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Критична помилка, виконання припинено", e);
            System.exit(1);
        }
    }

    public static void updatePriceAndDescriptionsFromDataFileToDataFile() throws IOException {
        File dataFile = new File(BASE_DIR + "descriptions.xlsx");
        File productsFromSiteFile = new File(BASE_DIR + "site.xlsx");
        File productsFromCRMFile = new File(BASE_DIR + "crm.xlsx");

        requireFile(dataFile);
        requireFile(productsFromSiteFile);
        requireFile(productsFromCRMFile);

        log.info("Старт оновлення цін та описів. Базова тека: " + BASE_DIR);

        XSSFWorkbook dataWorkbook;
        XSSFWorkbook productsWorkbook;
        XSSFWorkbook CRMWorkbook;
        try (FileInputStream dataInputStream = new FileInputStream(dataFile);
             FileInputStream productsFromSiteInputStream = new FileInputStream(productsFromSiteFile);
             FileInputStream productsFromCRMInputStream = new FileInputStream(productsFromCRMFile)) {
            dataWorkbook = new XSSFWorkbook(dataInputStream);
            productsWorkbook = new XSSFWorkbook(productsFromSiteInputStream);
            CRMWorkbook = new XSSFWorkbook(productsFromCRMInputStream);
        }

        XSSFSheet dataSheet = dataWorkbook.getSheetAt(0);
        XSSFSheet productsSheet = productsWorkbook.getSheetAt(0);
        XSSFSheet crmSheet = CRMWorkbook.getSheetAt(0);

        // Index target sheets by SKU once, so matching is O(1) per data row instead of a full re-scan.
        Map<String, List<XSSFRow>> siteIndex = indexBySku(productsSheet, SITE_SKU_COLUMN);
        Map<String, List<XSSFRow>> crmIndex = indexBySku(crmSheet, CRM_SKU_COLUMN);
        log.info(String.format("Проіндексовано: сайт — %d SKU, CRM — %d SKU", siteIndex.size(), crmIndex.size()));

        Random random = new Random();

        int processed = 0;
        int errors = 0;
        int siteHits = 0;
        int crmHits = 0;
        int siteDescriptionsUpdated = 0;
        int crmDescriptionsUpdated = 0;
        int skippedNowhere = 0;
        List<String> missingOnSite = new ArrayList<>();
        List<String> missingInCrm = new ArrayList<>();

        for (int i = 0; i <= dataSheet.getLastRowNum(); i++) {
            XSSFRow row = dataSheet.getRow(i);
            if (row == null) {
                continue;
            }

            XSSFCell dataCellSKU = row.getCell(DATA_SKU_COLUMN);
            XSSFCell dataCellHashMap = row.getCell(3);
            if (dataCellSKU == null || dataCellHashMap == null) {
                log.warning("Рядок " + (i + 1) + ": пропущено (немає SKU або складу набору)");
                continue;
            }

            String dataSKU = normalizeSku(dataCellSKU);

            // If the set exists only in descriptions.xlsx (not on the site and not in CRM),
            // there is nothing to update — skip it quietly, it is not an error.
            List<XSSFRow> siteRows = siteIndex.getOrDefault(dataSKU, List.of());
            List<XSSFRow> crmRows = crmIndex.getOrDefault(dataSKU, List.of());
            if (siteRows.isEmpty() && crmRows.isEmpty()) {
                skippedNowhere++;
                log.fine("SKU " + dataSKU + ": немає ні на сайті, ні в CRM — пропущено");
                continue;
            }

            // One bad row must not kill the whole batch — log it and move on.
            try {
                Map<String, String> productQuantity = Splitter.on(", ")
                        .withKeyValueSeparator("=")
                        .split(dataCellHashMap.toString().replace("{", "").replace("}", ""));

                int sum = 0;
                int balloons = 0;
                StringBuilder description = new StringBuilder();
                description.append("<p>Композиція гелієвих кульок складається з:</p>\n");
                description.append("<ul class=\"product-description\">\n");
                for (String j : productQuantity.keySet()) {
                    int productId = Integer.parseInt(j.trim());
                    Product product = getProductById(productId);
                    if (product == null) {
                        throw new IllegalStateException(
                                "Невідомий id товару " + productId + " — його немає у прайсі (Bot.productList)");
                    }
                    int quantity = Integer.parseInt(productQuantity.get(j).trim());
                    description.append(String.format("<li>%s — %d шт.</li>\n", product.getFullName(), quantity));
                    if (product.isBalloon()) {
                        balloons += quantity;
                    }
                    sum += quantity * product.getPrice();
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
                    if (sum < 3000) {
                        description.append("\n<li class=\"description_delivery\">Для безкоштовної доставки цього набору потрібно додати товарів ще на ").append(3000 - sum).append(" грн.</li>");
                    } else {
                        description.append("\n<li class=\"description_delivery\">Можлива безкоштовна доставка цього набору. Додайте набір у кошик, щоб подивитись деталі.</li>");
                    }
                    description.append("\n<li class=\"description_flowers\">Додайте квіти до замовлення: <a href=\"/floristika-2/\">розділ «Флористика»</a></li>");
                }
                description.append("</ul>");

                // update price and "price without discount" in the data file itself
                getOrCreateCell(row, 2).setCellValue(sum);

                int min = 10;
                int max = 20;
                int diff = max - min;
                int discount = random.nextInt(diff + 1) + min;
                long priceWithoutDiscountAmount = (Math.round(sum / (1 - discount * 0.01)) + 10) / 10 * 10;
                getOrCreateCell(row, 4).setCellValue(priceWithoutDiscountAmount);

                // change price and description on the site export (matched by SKU)
                if (siteRows.isEmpty()) {
                    missingOnSite.add(dataSKU);
                    log.warning("SKU " + dataSKU + ": є в CRM, але не знайдено на сайті (site.xlsx) — на сайті не оновлено");
                }
                for (XSSFRow productRow : siteRows) {
                    setString(productRow, 16, String.valueOf(sum));
                    setString(productRow, 29, description.toString());
                    siteHits++;
                    siteDescriptionsUpdated++;
                }

                // change price and description on the CRM export (matched by SKU)
                if (crmRows.isEmpty()) {
                    missingInCrm.add(dataSKU);
                    log.warning("SKU " + dataSKU + ": є на сайті, але не знайдено в CRM (crm.xlsx) — у CRM не оновлено");
                }
                String plainDescription = htmlToPlainText(description.toString());
                for (XSSFRow rowCRM : crmRows) {
                    getOrCreateCell(rowCRM, 14).setCellValue(sum);
                    setString(rowCRM, 2, plainDescription);
                    crmHits++;
                    crmDescriptionsUpdated++;
                }

                processed++;
                log.info(String.format("SKU %s: ціна %d грн, кульок %d (сайт: %d, CRM: %d)",
                        dataSKU, sum, balloons, siteRows.size(), crmRows.size()));
                log.fine("SKU " + dataSKU + " опис:\n" + description);

            } catch (Exception e) {
                errors++;
                log.log(Level.SEVERE, "Помилка обробки набору SKU=" + dataSKU
                        + " (рядок " + (i + 1) + "), склад=" + dataCellHashMap + " — рядок пропущено", e);
            }
        }

        // Write files
        try (FileOutputStream outNew = new FileOutputStream(dataFile);
             FileOutputStream outOld = new FileOutputStream(productsFromSiteFile);
             FileOutputStream crmOut = new FileOutputStream(productsFromCRMFile)) {
            dataWorkbook.write(outNew);
            productsWorkbook.write(outOld);
            CRMWorkbook.write(crmOut);
        }

        dataWorkbook.close();
        productsWorkbook.close();
        CRMWorkbook.close();

        log.info(String.format(
                "Готово. Оброблено наборів: %d, пропущено (немає ні на сайті, ні в CRM): %d, помилок: %d.",
                processed, skippedNowhere, errors));
        log.info(String.format(
                "Сайт (site.xlsx): оновлено рядків %d, з них описів %d. Не знайдено: %d.",
                siteHits, siteDescriptionsUpdated, missingOnSite.size()));
        log.info(String.format(
                "CRM (crm.xlsx): оновлено рядків %d, з них описів %d. Не знайдено: %d.",
                crmHits, crmDescriptionsUpdated, missingInCrm.size()));

        if (!missingOnSite.isEmpty()) {
            log.warning("Не знайдено на сайті (" + missingOnSite.size() + "): "
                    + String.join(", ", missingOnSite));
        }
        if (!missingInCrm.isEmpty()) {
            log.warning("Не знайдено в CRM (" + missingInCrm.size() + "): "
                    + String.join(", ", missingInCrm));
        }
        if (errors > 0) {
            log.warning("Завершено з помилками (" + errors + "). Перевірте записи SEVERE вище.");
        }
    }

    private static void requireFile(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("Файл не знайдено: " + file.getAbsolutePath());
        }
    }

    /**
     * Builds an index "normalized SKU -> rows" for the given sheet, skipping the header row (index 0).
     * Several rows may share the same SKU, so each key maps to a list.
     */
    private static Map<String, List<XSSFRow>> indexBySku(XSSFSheet sheet, int skuColumn) {
        Map<String, List<XSSFRow>> index = new HashMap<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            XSSFCell skuCell = row.getCell(skuColumn);
            if (skuCell == null) {
                continue;
            }
            String sku = normalizeSku(skuCell);
            if (sku.isEmpty()) {
                continue;
            }
            index.computeIfAbsent(sku, k -> new ArrayList<>()).add(row);
        }
        return index;
    }

    private static String normalizeSku(XSSFCell cell) {
        return cell.toString().trim().replace(".0", "");
    }

    /**
     * Converts the HTML description into plain text for CRM (CRM stores descriptions without tags).
     * The markup already contains its own line breaks, so we only strip the tags and tidy whitespace:
     * sections stay separated by a blank line, list items stay one per line.
     */
    private static String htmlToPlainText(String html) {
        String text = html.replaceAll("(?i)<[^>]+>", "");  // strip every tag (<p>, <ul>, <li>, <br>, <a> ...)
        text = text.replaceAll("[ \t]+\n", "\n");           // trailing spaces on a line
        text = text.replaceAll("\n{3,}", "\n\n");           // no more than one blank line in a row
        return text.trim();
    }

    private static XSSFCell getOrCreateCell(XSSFRow row, int index) {
        XSSFCell cell = row.getCell(index);
        return cell != null ? cell : row.createCell(index);
    }

    /**
     * Writes a String value into a cell, forcing a clean STRING cell.
     * <p>
     * The CRM export stores descriptions as inline strings ({@code t="inlineStr"}). Calling
     * {@code setCellValue(String)} on such a cell leaves the stale {@code <is>} element in place,
     * so spec-compliant readers (Excel, the CRM import) keep showing the OLD text. Recreating the
     * cell guarantees POI writes a normal shared-string cell. The original style is preserved.
     */
    private static void setString(XSSFRow row, int index, String value) {
        XSSFCell existing = row.getCell(index);
        CellStyle style = (existing != null) ? existing.getCellStyle() : null;
        if (existing != null) {
            row.removeCell(existing);
        }
        XSSFCell cell = row.createCell(index, CellType.STRING);
        if (style != null) {
            cell.setCellStyle(style);
        }
        cell.setCellValue(value);
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
