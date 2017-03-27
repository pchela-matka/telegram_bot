package core.service.commands;

import core.service.utils.ReplyCommandUtils;
import core.service.utils.UsersMap;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by astegnienko on 23.02.2017.
 */
public class HelpCommand extends BotCommand {
    public HelpCommand() {
        super("help", "help");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        if (UsersMap.getUSERS().get(String.valueOf(user.getId())) != null) {
            answer.setText("Для получения информации необходимо использовать следующие команды:" + "\n" +
                    "/opensr - перечень ваших открытых заявок" + "\n" +
                    "/opensr \"номер заявки\" - детальная информация по заявке. Пример команды - /opensr 1000" + "\n" +
                    "/createsr \"тема описание(без пробелов)\" - детальная информация по заявке. Пример команды - /createsr Тест Заявка_для_тестирования_телеграма" + "\n" +
                    "/help - перечень доступных команд");
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
