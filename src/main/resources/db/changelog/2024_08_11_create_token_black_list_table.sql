--liquibase formatted sql

--changeset sakhulbay:1
CREATE TABLE t_token_black_list
(
    id         BIGSERIAL PRIMARY KEY,
    token      TEXT      NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL
)

