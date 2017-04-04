/**
 * Created by astegnienko on 26.01.2017.
 */


import java.io.IOException;

import core.service.handlers.CommandBot;
import core.service.maximo.GetUsers;
import core.service.utils.GetProperties;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class MainApp extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            GetUsers.getTelegramUsers();
            telegramBotsApi.registerBot(new CommandBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return GetProperties.properies("bot");
    }

    @Override
    public String getBotToken() {
        return GetProperties.properies("botkey");
    }

    @Override
    public void onUpdateReceived(Update update) {
    }
}
