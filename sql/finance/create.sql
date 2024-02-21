-- Database: finance

-- DROP DATABASE IF EXISTS finance;

CREATE DATABASE finance
    WITH
    OWNER = orri
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8';


-- DROP TABLE IF EXISTS public.ml;

CREATE TABLE IF NOT EXISTS public.ml
(
    id serial,
    trade_date date NOT NULL,
    settlement_date date NOT NULL,
    description character varying(64) NOT NULL,
    action character varying(32) NOT NULL,
    symbol character varying(8) NULL,
    quantity numeric(15,2) NULL,
    price numeric(15,2) NULL,
    amount numeric(15,2) NOT NULL,
    CONSTRAINT ml_pkey PRIMARY KEY (id)
);
