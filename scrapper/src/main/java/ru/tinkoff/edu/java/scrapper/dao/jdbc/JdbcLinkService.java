package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.dao.LinkService;

import java.net.URI;
import java.sql.*;
import java.util.Collection;

@Repository
public class JdbcLinkService implements LinkService {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/scrapper";
    private static final String USER_NAME = "renat";
    private static final String USER_PASSWD = "passwd";

    private static Connection connection;

    {
        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LinkDao add(long tgChatId, URI url) throws SQLException {
        Statement statement = connection.createStatement();

        PreparedStatement linkInsertStatement = connection.prepareStatement("INSERT INTO link(url) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
        linkInsertStatement.setString(1, url.toString());
        linkInsertStatement.executeUpdate();

        ResultSet generatedKeys = linkInsertStatement.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Creating link failed, no ID obtained.");
        }
        int linkId = generatedKeys.getInt(1);

        // check if the chat already exists
        PreparedStatement chatCheckStatement = connection.prepareStatement("SELECT chat_id FROM chat WHERE chat_id = ?");
        chatCheckStatement.setLong(1, tgChatId);
        ResultSet chatCheckResult = chatCheckStatement.executeQuery();

        if (!chatCheckResult.next()) {
            // if the chat does not exist, insert chat_id
            PreparedStatement chatInsertStatement = connection.prepareStatement("INSERT INTO chat(chat_id) SELECT ? WHERE NOT EXISTS (SELECT 1 FROM chat WHERE chat_id = ?)", Statement.RETURN_GENERATED_KEYS);
            chatInsertStatement.setLong(1, tgChatId);
            chatInsertStatement.setLong(2, tgChatId);
            chatInsertStatement.executeUpdate();

            generatedKeys = chatInsertStatement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException("Creating chat failed, no ID obtained.");
            }
        }

        int chatId = chatCheckResult.getInt(1);

        PreparedStatement linkChatInsertStatement = connection.prepareStatement("INSERT INTO link_chat(link_id, chat_id) VALUES(?, ?)");
        linkChatInsertStatement.setInt(1, linkId);
        linkChatInsertStatement.setInt(2, chatId);
        linkChatInsertStatement.executeUpdate();

        return new LinkDao(chatId, url);    }

    @Override
    public LinkDao remove(long tgChatId, URI url) {
        return null;
    }

    @Override
    public Collection<LinkDao> listAll(long tgChatId) {
        return null;
    }
}
