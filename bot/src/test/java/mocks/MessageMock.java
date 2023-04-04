package mocks;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

public class MessageMock extends Message {

    private String text;
    private Chat chat;

    @Override
    public String text() {
        return text;
    }

    @Override
    public Chat chat() { return chat;}

    public MessageMock setText(String text, Chat chat) {
        this.text = text;
        this.chat = chat;
        return this;
    }
}
