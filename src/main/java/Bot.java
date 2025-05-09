import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    public static HashMap<Long, UserStatus> userStatus = new HashMap<>();
    public static HashMap<Integer, Integer> productQuantity = new HashMap<>();
    private static StringBuilder description = new StringBuilder();
    private static final List<Product> productList = new ArrayList<>();
    static {
        productList.add(new Product(1, "Латексна гелієва кулька без малюнка", "12д без мал", 85, true));
        productList.add(new Product(2, "Латексна гелієва кулька з малюнком/написом", "12д з мал", 90, true));
        productList.add(new Product(3, "Латексна гелієва кулька хром", "Хром", 100, true));
        productList.add(new Product(4, "Латексна гелієва кулька агат", "Агат", 110, true));
        productList.add(new Product(5, "Латексна гелієва кулька з конфетті", "Конфетті 12д", 100, true));
        productList.add(new Product(12, "Латексна гелієва кулька з індивідуальним написом", "12д з індивід", 85 + 40, true));
        productList.add(new Product(13, "Прозорий гігант з конфетті", "Гігант з конфетті без нап", 1100, true));
        productList.add(new Product(14, "Прозорий гігант з конфетті та написом", "Гігант з конфетті та нап", 1100 + 150, true));
        productList.add(new Product(15, "Латексний кольоровий гігант з написом", "Гігант з написом", 1000 + 150, true));
        productList.add(new Product(16, "Дзеркальний гігант з написом", "Дзеркальний з написом", 1100 + 150, true));
        productList.add(new Product(31, "Bubble з пір’ям або кульками без напису", "Bubble без напису", 900, true));
        productList.add(new Product(32, "Bubble з пір’ям або кульками з написом", "Bubble з написом", 900 + 100, true));
        productList.add(new Product(6, "Фольгована гелієва кулька 18д без малюнка", "18д без мал", 150, true));
        productList.add(new Product(7, "Фольгована гелієва кулька 18д з малюнком", "18д з мал", 240, true));
        productList.add(new Product(8, "Фольгована гелієва кулька 18д з індивідуальним написом", "18д з індивід", 240, true));
        productList.add(new Product(33, "Фольговане серце/зірка гігант без напису", "Фольга гігант без напису", 800, true));
        productList.add(new Product(17, "Фольговане серце/зірка гігант з написом", "Фольга гігант з написом", 800 + 150, true));
        productList.add(new Product(9, "Коробка-сюрприз з індивідуальним написом", "Коробка з написом", 1050 + 150, false));
        productList.add(new Product(30, "Коробка-сюрприз з кольоровою наліпкою", "Коробка з наклейкою", 1200 + 150, false));
        productList.add(new Product(20, "Фольгована цифра 96 см", "Цифра 96 см", 500, true));
        productList.add(new Product(21, "Фольгована цифра 70 см", "Цифра 70 см", 400, true));
        productList.add(new Product(34, "Цифра з повітрям на підставці", "Цифра з повітр", 550, true));
        productList.add(new Product(22, "Ходяча фольгована фігура Міккі/Мінні", "Ходячка Міккі/Мінні", 1800, true));
        productList.add(new Product(23, "Ходяча фольгована фігура", "Ходячки всі", 1800, true));
        productList.add(new Product(25, "Велика фольгована фігура", "Фігура за 390", 390, true));
        productList.add(new Product(26, "Велика фольгована фігура", "Фігура за 410", 410, true));
        productList.add(new Product(27, "Велика фольгована фігура", "Фігура за 420", 420, true));
        productList.add(new Product(28, "Велика фольгована фігура", "Фігура за 480", 480, true));
        productList.add(new Product(29, "Велика фольгована фігура", "Фігура за 520", 520, true));
        productList.add(new Product(51, "Велика фольгована фігура", "Фігура за 550", 550, true));
        productList.add(new Product(10, "Вантаж декоративний", "Груз", 20, false));
        productList.add(new Product(11, "Транспортувальний перфорований пакет", "Пакет", 40, false));
        productList.add(new Product(18, "Китиця тассел", "Китиця", 40, false));
        productList.add(new Product(19, "Спіраль тассел", "Спіраль", 100, false));
        productList.add(new Product(24, "Фольгована кулька сфера", "Сфера", 500, true));
        productList.add(new Product(35, "Гендерна кулька з кульками та конфетті всередині", "Гендерна", 1300, true));
        productList.add(new Product(36, "Повітряна композиція з місяцем та пупсом", "Повітряна комп", 600, true));
        productList.add(new Product(37, "Латексний кольоровий гігант", "Гігант без напису", 1000, true));
        productList.add(new Product(38, "Латексний кольоровий гігант у вигляді тваринки", "Гігант тваринка", 1200+100, true));
        productList.add(new Product(39, "Каркасна цифра аеромозаїка", "Аеромозаїка", 1500, false));
        productList.add(new Product(40, "Bubble з квітами", "Bubble з квітами", 2600, false));
        productList.add(new Product(41, "Коробка з трояндами та ведмідь", "Коробка+медведь", 2600+2000, false));
        productList.add(new Product(42, "Кульки з повітрям на підлогу", "На підлогу", 10, false));
        productList.add(new Product(43, "Індивідуальна гірлянда на кульку", "Гірлянда на кульку", 50, false));
        productList.add(new Product(44, "Індивідуальний дощик на кульку", "Дощик на кульку", 40, false));
        productList.add(new Product(45, "Дзеркальний гігант", "Дзеркальний без напису", 1100, true));
        productList.add(new Product(46, "Зірки з повітрям на підлогу", "Зірки на підлогу", 70, false));
        productList.add(new Product(47, "Корзина з повітряною кулею з кульок", "Шар с корзиной", 5500, false));
        productList.add(new Product(48, "Корзина з повітряною кулею з кульок (хром)", "Шар с корзиной (хром)", 6500, false));
        productList.add(new Product(49, "Фотозона — гірлянда кульок на банері з написом", "Фотозона дабл белый банер", 9500, false));
        productList.add(new Product(50, "Фольгована міні-ходяча фігура", "Мини ходячка", 750, false));
        productList.add(new Product(52, "Індивідуальний бантик на кулю", "Бантик", 15, false));
        productList.add(new Product(53, "Дзеркальна гелієва кулька 12д", "Дзеркальний латекс 12д", 120, true));
        productList.add(new Product(54, "Латексна гелієва кулька 18д", "Латекс 18д", 600, true));
    }

//    static {
//        for (Product product : productList) {
//            System.out.println(product.getFullName() + " — " + product.getPrice() + " грн.");
//        }
//        System.exit(0);
//    }
    private static int tempProductId;
    private static int tempProductQuantity;
    private static int tempProductSKU;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (userStatus.getOrDefault(update.getMessage().getChatId(), UserStatus.DEFAULT) == UserStatus.DEFAULT) {
                System.out.println(update);
                if (update.getMessage().hasText()) {
                    switch (message.getText()) {
                        case "/start":
                            try {
                                execute(startMessage(update.getMessage()));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "/create_bundle":
                            try {
                                execute(sendSomething(message, "Введіть артикул набору:"));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            userStatus.put(message.getChatId(), UserStatus.GET_SKU);
                            break;
                        default:
                            break;
                    }
                }
            } else if (userStatus.get(update.getMessage().getChatId()) == UserStatus.GET_SKU) {
                tempProductSKU = Integer.parseInt(message.getText());
                userStatus.put(message.getChatId(), UserStatus.GET_NAME);
                try {
                    execute(sendProducts(message));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (userStatus.get(update.getMessage().getChatId()) == UserStatus.GET_QUANTITY) {
                tempProductQuantity = Integer.parseInt(message.getText());

                if (tempProductQuantity != 0) {
                    productQuantity.put(tempProductId, productQuantity.getOrDefault(tempProductId, 0) + tempProductQuantity);
                }

                userStatus.put(message.getChatId(), UserStatus.GET_NAME);
                try {
                    execute(sendSomething(message, getProductById(tempProductId).getFullName() + " — " + tempProductQuantity + " шт."));
                    execute(sendProducts(message));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        }

        if (update.hasCallbackQuery()) {
            if (userStatus.get(update.getCallbackQuery().getMessage().getChatId()) == UserStatus.GET_NAME) {
                if (update.getCallbackQuery().getData().contains("AddProduct")) {
                    tempProductId = Integer.parseInt(update.getCallbackQuery().getData().replaceAll("\\D", ""));
                    userStatus.put(update.getCallbackQuery().getMessage().getChatId(), UserStatus.GET_QUANTITY);
                    try {
                        execute(defaultAnswerCallbackQuery(update.getCallbackQuery()));
                        execute(sendSomething(update.getCallbackQuery().getMessage(), "Введіть кількість:"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }

                if (update.getCallbackQuery().getData().contains("Create")) {
                    try {
                        execute(defaultAnswerCallbackQuery(update.getCallbackQuery()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    userStatus.put(update.getCallbackQuery().getMessage().getChatId(), UserStatus.CREATE);
                    System.out.println(productQuantity.toString());
                    System.out.println();
                    int sum = 0;
                    int balloons = 0;
                    description = new StringBuilder();
                    description.append("<p>Композиція гелієвих кульок складається з:</p>\n");
                    for (int i : productQuantity.keySet()) {
                        description.append(String.format("<p>%s — %d шт.</p>\n", getProductById(i).getFullName(), productQuantity.get(i)));
                        sum += getProductById(i).getPrice() * productQuantity.get(i);
                        if (getProductById(i).isBalloon()) {
                            balloons += productQuantity.get(i);
                        }
                    }

                    description.append("\n<p>Всього кульок у наборі: ").append(balloons).append(" шт.").append("</p>");

                    try {
                        UpdateExcel.createRow(tempProductSKU, description.toString(), sum, productQuantity.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        execute(sendSomething(update.getCallbackQuery().getMessage(), "Набір №" + tempProductSKU + "\n" + description.toString() + "\n" + "Вартість набору: " + sum + " грн."));
                        execute(oneMore(update.getCallbackQuery().getMessage()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    userStatus.put(update.getCallbackQuery().getMessage().getChatId(), UserStatus.DEFAULT);
                    productQuantity.clear();
                    description = null;
                }
                if (update.getCallbackQuery().getData().contains("Cancel")) {
                    productQuantity.clear();
                    try {
                        execute(defaultAnswerCallbackQuery(update.getCallbackQuery()));
                        execute(sendSomething(update.getCallbackQuery().getMessage(), "Скасовано."));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    userStatus.put(update.getCallbackQuery().getMessage().getChatId(), UserStatus.DEFAULT);
                    description = null;
                }
            }

            if (userStatus.getOrDefault(update.getCallbackQuery().getMessage().getChatId(), UserStatus.DEFAULT) == UserStatus.DEFAULT) {
                if (update.getCallbackQuery().getData().contains("OneMore")) {
                    try {
                        execute(defaultAnswerCallbackQuery(update.getCallbackQuery()));
                        execute(sendSomething(update.getCallbackQuery().getMessage(), "Введіть артикул набору:"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    userStatus.put(update.getCallbackQuery().getMessage().getChatId(), UserStatus.GET_SKU);
                }
            }
        }
    }

    public static AnswerCallbackQuery defaultAnswerCallbackQuery(CallbackQuery callbackQuery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
        return answerCallbackQuery;
    }

    private SendMessage oneMore(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Створити ще один набір?");
        sendMessage.setChatId(message.getChatId());

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Створити");
        inlineKeyboardButton.setCallbackData("OneMore");

        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        keyboardRow.add(inlineKeyboardButton);
        rowList.add(keyboardRow);
        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }

    private static SendMessage startMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Ну, що ж, почнемо.\nСкористайтеся меню для вибору команди");

        return sendMessage;
    }

    private static SendMessage sendSomething(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private static SendMessage sendProducts(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Оберіть потрібний товар");
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyMarkup(productsKeyboard());

        return sendMessage;
    }

    public static Product getProductById(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    private static InlineKeyboardMarkup productsKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
            Product first = productList.get(i);
            InlineKeyboardButton firstButton = new InlineKeyboardButton();
            firstButton.setText(first.getShortName() + " — " + productQuantity.getOrDefault(first.getId(), 0));
            firstButton.setCallbackData("AddProduct+" + first.getId());
            buttonsRow.add(firstButton);
            if (productList.listIterator(i).hasNext()) {
                Product second = productList.get(i + 1);
                InlineKeyboardButton secondButton = new InlineKeyboardButton();
                secondButton.setText(second.getShortName() + " — " + productQuantity.getOrDefault(second.getId(), 0));
                secondButton.setCallbackData("AddProduct+" + second.getId());
                buttonsRow.add(secondButton);
            }
            rowsList.add(buttonsRow);
            i++;
        }
//        for (Product product : productList) {
//            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
//            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//            inlineKeyboardButton.setText(product.getShortName() + " — " + productQuantity.getOrDefault(product.getId(), 0));
//            inlineKeyboardButton.setCallbackData("AddProduct+" + product.getId());
//            buttonsRow.add(inlineKeyboardButton);
//            rowsList.add(buttonsRow);
//        }

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Створити набір");
        inlineKeyboardButton.setCallbackData("Create");
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        buttonsRow.add(inlineKeyboardButton);

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Скасувати");
        inlineKeyboardButton2.setCallbackData("Cancel");
        buttonsRow.add(inlineKeyboardButton2);
        rowsList.add(buttonsRow);

        inlineKeyboardMarkup.setKeyboard(rowsList);

        return inlineKeyboardMarkup;
    }

    public static List<Product> getProductList() {
        return productList;
    }

    @Override
    public String getBotUsername() {
        return Config.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
    }
}
