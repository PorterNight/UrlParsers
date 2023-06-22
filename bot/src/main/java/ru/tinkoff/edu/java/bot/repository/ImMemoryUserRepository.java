package ru.tinkoff.edu.java.bot.repository;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ImMemoryUserRepository implements UserRepository {

    private static Map<Long, Boolean> users = new ConcurrentHashMap<>();

    @Override
    public boolean registerUser(long chatId) {

        if (users.get(chatId) == null) {
            users.put(chatId, true);
            return true;
        }
        return false;
    }
}
