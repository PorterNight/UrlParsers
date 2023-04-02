
package ru.tinkoff.edu.java.bot.telegramBot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.telegramBot.commands.HelpCommand;
import ru.tinkoff.edu.java.bot.telegramBot.commands.StartCommand;
import ru.tinkoff.edu.java.bot.telegramBot.commands.TrackCommand;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.Command;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.UserMessageProcessor;

import java.util.List;

public class UserMessageProcessImpl implements UserMessageProcessor {

    private final List<? extends Command> commands;

    private static FSMStates state = FSMStates.IDLE;
    private static FSMStates nextState = FSMStates.IDLE;

    private static Command cmd, nextCmd;

    public UserMessageProcessImpl() {
        commands = List.of(
                new StartCommand(),
                new HelpCommand(),
                new TrackCommand()
        );
//                new UntrackCommand(),
//                new ListCommand());
    }


    @Override
    public List<? extends Command> commands() {
        return commands;
    }


    @Override
    public SendMessage process(Update update) {

        long chatId = update.message().chat().id();

        if (update.message() == null) {
            return null;
        }

        String text = update.message().text();

        String msg = "Неизвестная команда";

        switch (state) {
            case IDLE:

                cmd = commands.get(0);  // Start
                if (cmd.supports(update) && text.equalsIgnoreCase(String.valueOf(cmd.command()))) {

                    msg = "Введите имя пользователя :";
                    nextState = FSMStates.START_REGISTER;
                    nextCmd = cmd;
                }

                cmd = commands.get(1);  // Help
                if (cmd.supports(update) && text.equalsIgnoreCase(String.valueOf(cmd.command()))) {

                    msg = cmd.handle(update, this);
                    nextState = FSMStates.IDLE;
                    nextCmd = cmd;
                }

                cmd = commands.get(2);  // Track
                if (cmd.supports(update) && text.equalsIgnoreCase(String.valueOf(cmd.command()))) {

                    msg = "Введите ссылку :";
                    nextState = FSMStates.TRACK_LINK;
                    nextCmd = cmd;
                }

                break;

            case START_REGISTER, TRACK_LINK:

                msg = cmd.handle(update, this);
                nextState = FSMStates.IDLE;

        }

        state = nextState;
        cmd = nextCmd;
        return new SendMessage(chatId, msg);
    }

    enum FSMStates {
        IDLE,
        START_REGISTER,
        TRACK_LINK,
        UNTRACK_LINK
    }

}