// Just for testing

CREATE TABLE IF NOT EXISTS main_storage.power_consumption_row (
	transformer_id int,
	generation_timestamp text,
	losses double,
	electric_power int,
	was_enabled boolean,
	log_id int,
	insertion_timestamp text,
	PRIMARY KEY (transformer_id, generation_timestamp)
);



INSERT INTO main_storage.power_consumption_row (transformer_id, generation_timestamp, losses, electric_power, was_enabled, log_id, insertion_timestamp)
VALUES (1, '2016-10-25T12:30:30', 0.01, 30000, true, null, '2016-10-25T12:30:40');


SELECT * FROM main_storage.power_consumption_row;
TRUNCATE main_storage.power_consumption_row;


