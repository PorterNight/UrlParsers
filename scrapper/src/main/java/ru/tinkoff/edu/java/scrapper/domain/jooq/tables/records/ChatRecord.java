/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records;


import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ChatRecord extends UpdatableRecordImpl<ChatRecord> implements Record2<Integer, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>CHAT.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>CHAT.CHAT_ID</code>.
     */
    @NotNull
    public Integer getChatId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>CHAT.UPDATED_AT</code>.
     */
    public void setUpdatedAt(@Nullable OffsetDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>CHAT.UPDATED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getUpdatedAt() {
        return (OffsetDateTime) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Integer, OffsetDateTime> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row2<Integer, OffsetDateTime> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Integer> field1() {
        return Chat.CHAT.CHAT_ID;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field2() {
        return Chat.CHAT.UPDATED_AT;
    }

    @Override
    @NotNull
    public Integer component1() {
        return getChatId();
    }

    @Override
    @Nullable
    public OffsetDateTime component2() {
        return getUpdatedAt();
    }

    @Override
    @NotNull
    public Integer value1() {
        return getChatId();
    }

    @Override
    @Nullable
    public OffsetDateTime value2() {
        return getUpdatedAt();
    }

    @Override
    @NotNull
    public ChatRecord value1(@NotNull Integer value) {
        setChatId(value);
        return this;
    }

    @Override
    @NotNull
    public ChatRecord value2(@Nullable OffsetDateTime value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public ChatRecord values(@NotNull Integer value1, @Nullable OffsetDateTime value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChatRecord
     */
    public ChatRecord() {
        super(Chat.CHAT);
    }

    /**
     * Create a detached, initialised ChatRecord
     */
    @ConstructorProperties({ "chatId", "updatedAt" })
    public ChatRecord(@NotNull Integer chatId, @Nullable OffsetDateTime updatedAt) {
        super(Chat.CHAT);

        setChatId(chatId);
        setUpdatedAt(updatedAt);
    }

    /**
     * Create a detached, initialised ChatRecord
     */
    public ChatRecord(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Chat value) {
        super(Chat.CHAT);

        if (value != null) {
            setChatId(value.getChatId());
            setUpdatedAt(value.getUpdatedAt());
        }
    }
}
