--liquibase formatted sql

--changeset renat_gainutdinov:001_create_tables-1.0
CREATE TABLE link (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    new_event_created_at TIMESTAMPTZ
);

--changeset renat_gainutdinov:002_create_tables-1.0
CREATE TABLE chat (
    chat_id SERIAL PRIMARY KEY,
	updated_at TIMESTAMPTZ DEFAULT NOW()
);

--changeset renat_gainutdinov:003_create_tables-1.0
CREATE TABLE link_chat (
    link_id INTEGER REFERENCES link(id) ON DELETE CASCADE,
    chat_id INTEGER REFERENCES chat(chat_id) ON DELETE CASCADE,
    PRIMARY KEY (link_id, chat_id)
);

