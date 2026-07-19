--liquibase formatted sql

--changeset adel:1
CREATE TABLE orders (
id UUID PRIMARY KEY,
user_id UUID NOT NULL,
price INTEGER NOT NULL,
product_id UUID NOT NULL,
product_quantity INTEGER NOT NULL,
status VARCHAR(50) NOT NULL,
created_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
updated_at      TIMESTAMP    NOT NULL DEFAULT NOW()
)