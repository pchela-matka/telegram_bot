/**
 * Created by astegnienko on 26.01.2017.
 */


import java.io.IOException;

import core.service.handlers.CommandBot;
import core.service.MaximoService;
import core.service.utils.MyEntity;
import core.service.utils.SrMap;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class MainApp extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
//            telegramBotsApi.registerBot(new MainApp());
            MaximoService.getTelegramUsers();
            telegramBotsApi.registerBot(new CommandBot());
//            SrMap.myHashMap.put("2",new MyEntity("2","3"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "test";
    }

    @Override
    public String getBotToken() {
        return "test:test";
    }

    @Override
    public void onUpdateReceived(Update update) {
//        Message message = update.getMessage();
//        try {
//            if (MaximoService.getPersonByID(message.getFrom().getId())) {
//                if (message.getContact() == null && message.getText().equals("/start")){
//                sendMsgButton(message,"Добрый день!" + "\n"+
//                        "Для продолжения работы с ботом необходимо предоставить телефон для идентификации - кнопка ниже.");}
//                else{
//                sendMsg(message, MaximoService.addPersonPhone(message.getContact().getPhoneNumber(),message.getFrom().getId()));}
//            } else if (message != null && message.hasText()){
//                if (message.getText().equals("/help"))
//                    sendMsg(message, "Для получения списка открытых заявок необходимо набрать /sropen;" +
//                            "\n" +
//                            "Для получения деталей по заявке необходимо набрать /номер заявки;");
//                else if (message.getText().equals("/sropen"))
//                    sendMsg(message, MaximoService.getDataFromMaximo());
//                else if (message.getText().matches("^/\\d\\d\\d\\d"))
//                    sendMsg(message, MaximoService.getSrDetail(message.getText().substring(1)));
//                else if (message.getText().equals("/start"))
//                    sendMsg(message, "Для получения списка открытых заявок необходимо набрать /sropen;" +
//                            "\n" +
//                            "Для получения деталей по заявке необходимо набрать /номер заявки;");
//                else
//                    sendMsg(message, "Я не знаю такой команды(");
//
//            } else
//            {
//                sendMsg(message, "Для получения списка открытых заявок необходимо набрать /sropen;");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private void sendMsg(Message message, String text) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setText(text);
//        try {
//            sendMessage(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }

    private void sendMsgButton(Message message, String text) {
//        SendMessage sendMessage = new SendMessage();
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        sendMessage.enableNotification();
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboad(true);
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        keyboardFirstRow.add(new KeyboardButton("Предоставить телефон для идентификации").setRequestContact(true));
//        keyboard.add(keyboardFirstRow);
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        replyKeyboardMarkup.setOneTimeKeyboad(false);
//        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setText(text);
//        try {
//            sendMessage(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }
}
