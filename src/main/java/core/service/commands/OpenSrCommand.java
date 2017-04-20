package core.service.commands;

import core.service.maximo.GetSr;
import core.service.maximo.GetSrs;
import core.service.utils.*;
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
public class OpenSrCommand extends BotCommand {
    public OpenSrCommand() {
        super("opensr", "opensr");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage answer = new SendMessage();
        CleanHashMap.CleanInfo(String.valueOf(user.getId()));
        if (UsersMap.getUSERS().get(String.valueOf(user.getId())) != null) {
            try {
                answer.setChatId(chat.getId().toString());
                if (strings.length == 0) {
                    answer.setText(GetSrs.getDataFromMaximo());
                } else if (strings.length == 1) {
                    String test = strings[0];
                    int position = test.indexOf(" ");
                    answer.setText(GetSr.getSrDetail(test.trim().substring(position + 1)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
