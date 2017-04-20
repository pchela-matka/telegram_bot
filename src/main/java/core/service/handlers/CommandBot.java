package core.service.handlers;

import core.service.commands.CreateSrCommand;
import core.service.commands.HelpCommand;
import core.service.commands.OpenSrCommand;
import core.service.commands.StartCommand;
import core.service.maximo.AddPhoneMaximo;
import core.service.maximo.NewSr;
import core.service.utils.GetProperties;
import core.service.utils.SrMap;
import core.service.utils.UsersMap;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by astegnienko on 23.02.2017.
 */
public class CommandBot extends TelegramLongPollingCommandBot {

    public CommandBot() {
        register(new CreateSrCommand());
        register(new OpenSrCommand());
        register(new HelpCommand());
        register(new StartCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && UsersMap.getUSERS().get(String.valueOf(message.getFrom().getId())) != null) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(message.getChatId());
                if (SrMap.getMyHashMap().get(String.valueOf(message.getFrom().getId())) != null && SrMap.getMyHashMap().get(String.valueOf(message.getFrom().getId())).getDesc() == null) {
                    echoMessage.setText("Введите описание вашей заявки");
                    SrMap.getMyHashMap().get(String.valueOf(message.getFrom().getId())).setDesc(message.getText());
                } else if (SrMap.getMyHashMap().get(String.valueOf(message.getFrom().getId())) != null && SrMap.getMyHashMap().get(String.valueOf(message.getFrom().getId())).getDesc() != null) {
                    SrMap.getMyHashMap().get(String.valueOf(message.getFrom().getId())).setLongdesc(message.getText());
                    try {
                        echoMessage.setText(NewSr.createSR(SrMap.getMyHashMap().get(String.valueOf(message.getFrom().getId())).getDesc(),
                                SrMap.getMyHashMap().get(String.valueOf(message.getFrom().getId())).getLongdesc(), String.valueOf(message.getFrom().getId())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    echoMessage.setText("Мне не знакома команда:\n" + message.getText());
                }
                try {
                    sendMessage(echoMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.hasText() && UsersMap.getUSERS().get(String.valueOf(message.getFrom().getId())) == null) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(message.getChatId());
                echoMessage.setText("Мне не знакома команда:\n" + message.getText());
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                echoMessage.setReplyMarkup(replyKeyboardMarkup);
                replyKeyboardMarkup.setSelective(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                replyKeyboardMarkup.setOneTimeKeyboad(true);
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow keyboardFirstRow = new KeyboardRow();
                keyboardFirstRow.add(new KeyboardButton("Предоставить телефон для идентификации").setRequestContact(true));
                keyboard.add(keyboardFirstRow);
                replyKeyboardMarkup.setKeyboard(keyboard);
                try {
                    sendMessage(echoMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.getContact() != null) {
                try {
                    SendMessage echoMessage = new SendMessage();
                    echoMessage.setChatId(message.getChatId());
                    echoMessage.setText(AddPhoneMaximo.addPersonPhone(message.getContact().getPhoneNumber(), message.getFrom().getId()));
                    sendMessage(echoMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "CommandBot";
    }

    @Override
    public String getBotToken() {
        return GetProperties.properies("botkey");
    }
}
