package mocks;

import com.pengrad.telegrambot.model.Chat;

public class ChatMock extends Chat {

    private Long id;

    @Override
    public Long id() {
        return this.id;
    }

    public ChatMock setId(Long id) {
        this.id = id;
        return this;
    }
}
