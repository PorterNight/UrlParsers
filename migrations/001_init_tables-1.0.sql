--liquibase formatted sql

--changeset renat_gainutdinov:001_create_tables-1.0
CREATE TABLE link (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

--changeset renat_gainutdinov:002_create_tables-1.0
CREATE TABLE chat (
    id SERIAL PRIMARY KEY,
    chatid TEXT NOT NULL
);

--changeset renat_gainutdinov:003_create_tables-1.0
CREATE TABLE link_chat (
    link_id INTEGER REFERENCES link(id) ON DELETE CASCADE,
    chat_id INTEGER REFERENCES chat(id) ON DELETE CASCADE,
    PRIMARY KEY (link_id, chat_id)
);

