// Just for testing

CREATE TABLE IF NOT EXISTS main_storage.power_consumption_row (
	transformer_id int,
	timestamp text,
	losses double,
	electric_power int,
	was_enabled boolean,
	log_id int,
	PRIMARY KEY (transformer_id, timestamp)
);



INSERT INTO main_storage.power_consumption_row (transformer_id, timestamp, losses, electric_power, was_enabled, log_id)
VALUES (1, '2016-10-25T12:30:30', 0.01, 30000, true, null);


SELECT * FROM main_storage.power_consumption_row;
TRUNCATE main_storage.power_consumption_row;


