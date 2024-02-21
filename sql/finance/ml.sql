CREATE TABLE ML
(
  [ID] serial,
  [TRADE_DATE] date NOT NULL,
  [SETTLEMENT_DATE] date NOT NULL,
  [DESCRIPTION] varchar(64) NOT NULL,
  [ACTION] varchar(32) NOT NULL,
  [SYMBOL] varchar(8) NULL,
  [QUANTITY] numeric(15, 2) NULL,
  [PRICE] numeric(15, 2) NULL,
  [AMOUNT] numeric(15, 2) NOT NULL,
  PRIMARY KEY ([ID])
);