--liquibase formatted sql

--changeset sakhulbay:1
ALTER TABLE t_image
    ADD COLUMN type VARCHAR(64);

