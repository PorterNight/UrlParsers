/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables;


import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.tinkoff.edu.java.scrapper.domain.jooq.DefaultSchema;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Keys;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinkChatRecord;


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
public class LinkChat extends TableImpl<LinkChatRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>LINK_CHAT</code>
     */
    public static final LinkChat LINK_CHAT = new LinkChat();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<LinkChatRecord> getRecordType() {
        return LinkChatRecord.class;
    }

    /**
     * The column <code>LINK_CHAT.LINK_ID</code>.
     */
    public final TableField<LinkChatRecord, Long> LINK_ID = createField(DSL.name("LINK_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>LINK_CHAT.CHAT_ID</code>.
     */
    public final TableField<LinkChatRecord, Long> CHAT_ID = createField(DSL.name("CHAT_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    private LinkChat(Name alias, Table<LinkChatRecord> aliased) {
        this(alias, aliased, null);
    }

    private LinkChat(Name alias, Table<LinkChatRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>LINK_CHAT</code> table reference
     */
    public LinkChat(String alias) {
        this(DSL.name(alias), LINK_CHAT);
    }

    /**
     * Create an aliased <code>LINK_CHAT</code> table reference
     */
    public LinkChat(Name alias) {
        this(alias, LINK_CHAT);
    }

    /**
     * Create a <code>LINK_CHAT</code> table reference
     */
    public LinkChat() {
        this(DSL.name("LINK_CHAT"), null);
    }

    public <O extends Record> LinkChat(Table<O> child, ForeignKey<O, LinkChatRecord> key) {
        super(child, key, LINK_CHAT);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public UniqueKey<LinkChatRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_470;
    }

    @Override
    @NotNull
    public List<ForeignKey<LinkChatRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_4, Keys.CONSTRAINT_47);
    }

    private transient Link _link;
    private transient Chat _chat;

    /**
     * Get the implicit join path to the <code>PUBLIC.LINK</code> table.
     */
    public Link link() {
        if (_link == null)
            _link = new Link(this, Keys.CONSTRAINT_4);

        return _link;
    }

    /**
     * Get the implicit join path to the <code>PUBLIC.CHAT</code> table.
     */
    public Chat chat() {
        if (_chat == null)
            _chat = new Chat(this, Keys.CONSTRAINT_47);

        return _chat;
    }

    @Override
    @NotNull
    public LinkChat as(String alias) {
        return new LinkChat(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public LinkChat as(Name alias) {
        return new LinkChat(alias, this);
    }

    @Override
    @NotNull
    public LinkChat as(Table<?> alias) {
        return new LinkChat(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinkChat rename(String name) {
        return new LinkChat(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinkChat rename(Name name) {
        return new LinkChat(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public LinkChat rename(Table<?> name) {
        return new LinkChat(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
