CREATE KEYSPACE main_storage
WITH replication = {
	'class' : 'SimpleStrategy',
	'replication_factor' : 1
};

USE main_storage;

CREATE TABLE IF NOT EXISTS main_storage.power_consumption_row (
	transformer_id int,
	timestamp text,
	losses double,
	electric_power int,
	was_enabled boolean,
	log_id int,
	PRIMARY KEY (transformer_id, timestamp)
);






