--liquibase formatted sql

--changeset adel:2
CREATE TABLE orders_history (
id UUID PRIMARY KEY,
order_id UUID NOT NULL,
status VARCHAR(50) NOT NULL,
created_at      TIMESTAMP    NOT NULL DEFAULT NOW()
)