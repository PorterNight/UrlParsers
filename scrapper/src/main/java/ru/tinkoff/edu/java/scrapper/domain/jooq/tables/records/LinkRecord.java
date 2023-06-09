/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;


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
public class LinkRecord extends UpdatableRecordImpl<LinkRecord> implements Record4<Long, String, OffsetDateTime, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINK.ID</code>.
     */
    public void setId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINK.ID</code>.
     */
    @NotNull
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINK.URL</code>.
     */
    public void setUrl(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINK.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LINK.UPDATED_AT</code>.
     */
    public void setUpdatedAt(@Nullable OffsetDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINK.UPDATED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getUpdatedAt() {
        return (OffsetDateTime) get(2);
    }

    /**
     * Setter for <code>LINK.NEW_EVENT_CREATED_AT</code>.
     */
    public void setNewEventCreatedAt(@Nullable OffsetDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>LINK.NEW_EVENT_CREATED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getNewEventCreatedAt() {
        return (OffsetDateTime) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row4<Long, String, OffsetDateTime, OffsetDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row4<Long, String, OffsetDateTime, OffsetDateTime> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Link.LINK.ID;
    }

    @Override
    @NotNull
    public Field<String> field2() {
        return Link.LINK.URL;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field3() {
        return Link.LINK.UPDATED_AT;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field4() {
        return Link.LINK.NEW_EVENT_CREATED_AT;
    }

    @Override
    @NotNull
    public Long component1() {
        return getId();
    }

    @Override
    @NotNull
    public String component2() {
        return getUrl();
    }

    @Override
    @Nullable
    public OffsetDateTime component3() {
        return getUpdatedAt();
    }

    @Override
    @Nullable
    public OffsetDateTime component4() {
        return getNewEventCreatedAt();
    }

    @Override
    @NotNull
    public Long value1() {
        return getId();
    }

    @Override
    @NotNull
    public String value2() {
        return getUrl();
    }

    @Override
    @Nullable
    public OffsetDateTime value3() {
        return getUpdatedAt();
    }

    @Override
    @Nullable
    public OffsetDateTime value4() {
        return getNewEventCreatedAt();
    }

    @Override
    @NotNull
    public LinkRecord value1(@NotNull Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value2(@NotNull String value) {
        setUrl(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value3(@Nullable OffsetDateTime value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value4(@Nullable OffsetDateTime value) {
        setNewEventCreatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord values(@NotNull Long value1, @NotNull String value2, @Nullable OffsetDateTime value3, @Nullable OffsetDateTime value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinkRecord
     */
    public LinkRecord() {
        super(Link.LINK);
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    @ConstructorProperties({ "id", "url", "updatedAt", "newEventCreatedAt" })
    public LinkRecord(@NotNull Long id, @NotNull String url, @Nullable OffsetDateTime updatedAt, @Nullable OffsetDateTime newEventCreatedAt) {
        super(Link.LINK);

        setId(id);
        setUrl(url);
        setUpdatedAt(updatedAt);
        setNewEventCreatedAt(newEventCreatedAt);
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    public LinkRecord(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Link value) {
        super(Link.LINK);

        if (value != null) {
            setId(value.getId());
            setUrl(value.getUrl());
            setUpdatedAt(value.getUpdatedAt());
            setNewEventCreatedAt(value.getNewEventCreatedAt());
        }
    }
}
