import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static {
        // Single-line, readable log format: 2026-06-13 12:00:00 [INFO] message
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT [%4$s] %5$s%6$s%n");
    }

    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Config.load();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
            log.info("Бот запущено: " + Config.BOT_NAME);
        } catch (TelegramApiException e) {
            log.log(Level.SEVERE, "Не вдалося запустити бота", e);
        }
    }
}
