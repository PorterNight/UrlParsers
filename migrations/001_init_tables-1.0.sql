--liquibase formatted sql

--changeset renat_gainutdinov:001_create_tables-1.0
CREATE TABLE link (
    id BIGSERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    new_event_created_at TIMESTAMP WITH TIME ZONE
);

--changeset renat_gainutdinov:002_create_tables-1.0
CREATE TABLE chat (
    chat_id BIGSERIAL PRIMARY KEY,
	updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

--changeset renat_gainutdinov:003_create_tables-1.0
CREATE TABLE link_chat (
    link_id BIGINT REFERENCES link(id) ON DELETE CASCADE,
    chat_id BIGINT REFERENCES chat(chat_id) ON DELETE CASCADE,
    PRIMARY KEY (link_id, chat_id)
);

