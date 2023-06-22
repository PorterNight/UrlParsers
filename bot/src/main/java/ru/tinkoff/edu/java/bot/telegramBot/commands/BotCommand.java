package ru.tinkoff.edu.java.bot.telegramBot.commands;

public enum BotCommand {

    HELP("/help", " - get help"),
    START("/start", " - command is used for user registration"),
    TRACK("/track", " - command is used to start tracking link"),
    UNTRACK("/untrack", " - command is used to stop tracking link"),
    LIST("/list", " - get list of tracked links"),

    TRACK_SAVE("/enter link in reply to save", " - track reply command"),
    UNTRACK_SAVE("/enter link in reply to unsave", " - untrack reply command");

    private final String command;
    private final String description;

    BotCommand(String command, String description) {

        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }
    public String getDescription() {
        return description;
    }
}
