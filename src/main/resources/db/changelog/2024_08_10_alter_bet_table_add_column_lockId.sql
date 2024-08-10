--liquibase formatted sql

--changeset sakhulbay:1
ALTER TABLE t_bet
    ADD COLUMN lock_id VARCHAR(255);

