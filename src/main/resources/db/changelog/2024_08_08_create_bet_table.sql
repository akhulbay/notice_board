--liquibase formatted sql

--changeset sakhulbay:1
CREATE TABLE t_bet
(
    id         BIGINT PRIMARY KEY,
    amount     BIGINT NOT NULL,
    expires_at DATE   NOT NULL,
    user_id    BIGINT REFERENCES t_user (id)
);

