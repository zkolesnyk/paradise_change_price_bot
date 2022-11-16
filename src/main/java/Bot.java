import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    public static HashMap<Long, Integer> userStatus = new HashMap<>();
    public static HashMap<Integer, Integer> productQuantity = new HashMap<>();
    private static StringBuilder description;
    private static String tempProductName;
    private static int tempProductId;
    private static int tempProductQuantity;
    private static int tempProductSKU;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (userStatus.getOrDefault(update.getMessage().getChatId(), 0) == BotStatus.DEFAULT.get()) {
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
                            userStatus.put(message.getChatId(), BotStatus.GET_SKU.get());
                            break;
                        default:
                            break;
                    }
                }
            } else if (userStatus.get(update.getMessage().getChatId()) == BotStatus.GET_SKU.get()) {
                tempProductSKU = Integer.parseInt(message.getText());
                userStatus.put(message.getChatId(), BotStatus.GET_NAME.get());
                try {
                    execute(sendProducts(message));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (userStatus.get(update.getMessage().getChatId()) == BotStatus.GET_QUANTITY.get()) {
                tempProductQuantity = Integer.parseInt(message.getText());
            }
        }

        if (update.hasCallbackQuery()) {
            if (userStatus.get(update.getCallbackQuery().getMessage().getChatId()) == BotStatus.GET_NAME.get()) {
                if (update.getCallbackQuery().getData().contains("AddProduct")) {
                    tempProductId = Integer.parseInt(update.getCallbackQuery().getData().replaceAll("\\D", ""));
                    userStatus.put(update.getCallbackQuery().getMessage().getChatId(), BotStatus.GET_QUANTITY.get());
                    try {
                        execute(sendSomething(update.getCallbackQuery().getMessage(), "Введіть кількість:"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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

    private static InlineKeyboardMarkup productsKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        for (Product product : Product.values()) {
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(product.getName());
            inlineKeyboardButton.setCallbackData("AddProduct+" + product.getValue());
            buttonsRow.add(inlineKeyboardButton);
            rowsList.add(buttonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowsList);

        return inlineKeyboardMarkup;
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
