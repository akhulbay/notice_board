--liquibase formatted sql

--changeset sakhulbay:1
CREATE TABLE t_user
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(32)  NOT NULL
);

--changeset sakhulbay:2
CREATE TABLE t_advertisement
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    min_price   BIGINT       NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    status      VARCHAR(32)  NOT NULL,
    user_id     BIGINT REFERENCES t_user (id)
);

--changeset sakhulbay:3
CREATE TABLE t_image
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    content          BYTEA,
    advertisement_id BIGINT REFERENCES t_advertisement (id)
);


