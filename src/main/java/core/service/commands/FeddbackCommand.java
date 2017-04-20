package core.service.commands;

import core.service.utils.*;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by astegnienko on 04.04.2017.
 */
public class FeddbackCommand extends BotCommand {
    public FeddbackCommand(String commandIdentifier, String description) {
        super("feedback", "Command to create new feedback");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage answer = new SendMessage();
        CleanHashMap.CleanInfo(String.valueOf(user.getId()));
        if (UsersMap.getUSERS().get(String.valueOf(user.getId())) != null) {
            answer.setChatId(chat.getId().toString());
            FeedbackMap.FEEDBACK.put(String.valueOf(user.getId()), "");
            answer.setText("Введите ваше предложение по улучшению сервиса");
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
