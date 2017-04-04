package core.service.commands;

import core.service.MaximoService;
import core.service.utils.MyEntity;
import core.service.utils.ReplyCommandUtils;
import core.service.utils.SrMap;
import core.service.utils.UsersMap;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Created by astegnienko on 23.02.2017.
 */
public class CreateSrCommand extends BotCommand {
    public CreateSrCommand() {

        super("createsr", "Command to create new sr");
    }

    @Override
//    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
//        SendMessage answer = new SendMessage();
//        if (UsersMap.getUSERS().get(String.valueOf(user.getId())) != null) {
//            try {
//                answer.setChatId(chat.getId().toString());
//                answer.setText(MaximoService.createSR(strings[0], strings[1], UsersMap.getUSERS().get(String.valueOf(user.getId()))));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (UsersMap.getUSERS().get(String.valueOf(user.getId())) == null) {
//            answer.setReplyMarkup(ReplyCommandUtils.getReplyKeyboard());
//        }
//        try {
//            absSender.sendMessage(answer);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage answer = new SendMessage();
        if (UsersMap.getUSERS().get(String.valueOf(user.getId())) != null) {
            answer.setChatId(chat.getId().toString());
            SrMap.myHashMap.put(String.valueOf(user.getId()), new MyEntity());
            answer.setText("Введите тему вашей заявки");
        } else if (UsersMap.getUSERS().get(String.valueOf(user.getId())) == null) {
            answer.setReplyMarkup(ReplyCommandUtils.getReplyKeyboard());
        }
        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
