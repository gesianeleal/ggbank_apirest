USE gg;

CREATE TABLE `account` (
`account_id` INT NOT NULL AUTO_INCREMENT,
`document_number` BIGINT not NULL,
	PRIMARY KEY (`account_id`)
);

CREATE TABLE operationtype (
`operationtype_id` INT NOT NULL AUTO_INCREMENT,
`description` VARCHAR(50) NOT NULL,
PRIMARY KEY (`operationtype_id`)
);

CREATE TABLE `transaction` (
`transaction_id` INT NOT NULL AUTO_INCREMENT,
`account_id` INTEGER NOT NULL , 
`operationtype_id` INTEGER NOT NULL, 
`amount` DECIMAL(16,4), 
`event_datetime` TIMESTAMP,
PRIMARY KEY (`transaction_id`), 
	CONSTRAINT `fk_transaction_account_id`
		FOREIGN KEY (`account_id`)
		REFERENCES `account` (`account_id`)
		ON DELETE NO ACTION
		ON UPDATE CASCADE, 
	CONSTRAINT `fk_transaction_operationtype_id`
		FOREIGN KEY (`operationtype_id`)
		REFERENCES `operationtype` (`operationtype_id`)
		ON DELETE NO ACTION
		ON UPDATE CASCADE
);

-- index (`transaction_id`, `account_id`, `operationtype_id`), 

INSERT INTO `gg`.`account`
(`account_id`,
`document_number`)
VALUES
(1, 12345678900);

INSERT INTO `gg`.`operationtype`
(`operationtype_id`,
`description`)
VALUES
(1, 'COMPRA A VISTA'),
(2, 'COMPRA PARCELADA'),
(3, 'SAQUE'),
(4, 'PAGAMENTO');


INSERT INTO `gg`.`transaction`
(`transaction_id`,
`account_id`,
`operationtype_id`,
`amount`,
`event_datetime`)
VALUES
(1, 1, 1, -50.0, '2020-01-01T10:32:07.7199222'),
(2, 1, 1, -23.5, '2020-01-01T10:48:12.2135875'),
(3, 1, 1, -18.7, '2020-01-02T19:01:23.1458543'),
(4, 1, 4, 60.0, '2020-01-05T09:34:18.5893223');