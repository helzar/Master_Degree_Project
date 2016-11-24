-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 6.1.166.0
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 25.11.2016 0:28:42
-- Версия сервера: 5.6.19
-- Версия клиента: 4.1

-- 
-- Отключение внешних ключей
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Установить режим SQL (SQL mode)
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- Установка кодировки, с использованием которой клиент будет посылать запросы на сервер
--
SET NAMES 'utf8';

-- 
-- Установка базы данных по умолчанию
--
USE master_project;

--
-- Описание для таблицы station_type
--
DROP TABLE IF EXISTS station_type;
CREATE TABLE station_type (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 6
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы electrical_station
--
DROP TABLE IF EXISTS electrical_station;
CREATE TABLE electrical_station (
  id INT(11) NOT NULL AUTO_INCREMENT,
  type_id INT(11) DEFAULT NULL,
  is_active TINYINT(1) NOT NULL,
  name VARCHAR(50) NOT NULL,
  region VARCHAR(50) DEFAULT NULL,
  location VARCHAR(100) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_electrical_station_station_type_id FOREIGN KEY (type_id)
    REFERENCES station_type(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 7
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы transformer
--
DROP TABLE IF EXISTS transformer;
CREATE TABLE transformer (
  id INT(11) NOT NULL AUTO_INCREMENT,
  parent_transformer_id INT(11) DEFAULT NULL,
  parent_electrical_station_id INT(11) DEFAULT NULL,
  region VARCHAR(50) DEFAULT NULL,
  location VARCHAR(100) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_transformer_electrical_station_id FOREIGN KEY (parent_electrical_station_id)
    REFERENCES electrical_station(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_transformer_transformer_id FOREIGN KEY (parent_transformer_id)
    REFERENCES transformer(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 25
AVG_ROW_LENGTH = 682
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы power_consumption
--
DROP TABLE IF EXISTS power_consumption;
CREATE TABLE power_consumption (
  id INT(11) NOT NULL AUTO_INCREMENT,
  transformer_id INT(11) NOT NULL,
  timestamp CHAR(20) NOT NULL,
  losses DOUBLE DEFAULT NULL,
  electric_power INT(11) NOT NULL,
  was_enabled TINYINT(1) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_power_consumption_transformer_id FOREIGN KEY (transformer_id)
    REFERENCES transformer(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 61
AVG_ROW_LENGTH = 273
CHARACTER SET utf8
COLLATE utf8_general_ci;

-- 
-- Вывод данных для таблицы station_type
--
INSERT INTO station_type VALUES
(1, 'TERMAL'),
(2, 'NUCLEAR'),
(3, 'HYDRO'),
(4, 'SOLAR'),
(5, 'WIND');

-- 
-- Вывод данных для таблицы electrical_station
--
INSERT INTO electrical_station VALUES
(1, 1, 1, 'Добротвірська ТЕС', 'Львівська область', 'м. Добротвір', NULL),
(2, 1, 1, 'Запорізька ТЕС', 'Запорізька область', 'м. Енергодар', NULL),
(3, 2, 1, 'Рівненська АЕС', 'Рівненська область', 'м. Вараш', NULL);

-- 
-- Вывод данных для таблицы transformer
--
INSERT INTO transformer VALUES
(1, NULL, 1, 'Львівська область', 'м. Добротвір', NULL),
(2, NULL, 1, 'Львівська область', 'м. Добротвір', NULL),
(3, NULL, 1, 'Львівська область', 'м. Добротвір', NULL),
(4, 2, NULL, 'Львівська область', 'м. Добротвір', NULL),
(5, 2, NULL, 'Львівська область', 'м. Добротвір', NULL),
(6, 3, NULL, 'Львівська область', 'м. Добротвір', NULL),
(7, 3, NULL, 'Львівська область', 'м. Добротвір', NULL),
(8, 3, NULL, 'Львівська область', 'м. Добротвір', NULL),
(9, NULL, 2, 'Запорізька область', 'м. Енергодар', NULL),
(10, NULL, 2, 'Запорізька область', 'м. Енергодар', NULL),
(11, NULL, 2, 'Запорізька область', 'м. Енергодар', NULL),
(12, 10, NULL, 'Запорізька область', 'м. Енергодар', NULL),
(13, 10, NULL, 'Запорізька область', 'м. Енергодар', NULL),
(14, 11, NULL, 'Запорізька область', 'м. Енергодар', NULL),
(15, 11, NULL, 'Запорізька область', 'м. Енергодар', NULL),
(16, 11, NULL, 'Запорізька область', 'м. Енергодар', NULL),
(17, NULL, 3, 'Рівненська область', 'м. Вараш', NULL),
(18, NULL, 3, 'Рівненська область', 'м. Вараш', NULL),
(19, NULL, 3, 'Рівненська область', 'м. Вараш', NULL),
(20, 18, NULL, 'Рівненська область', 'м. Вараш', NULL),
(21, 18, NULL, 'Рівненська область', 'м. Вараш', NULL),
(22, 19, NULL, 'Рівненська область', 'м. Вараш', NULL),
(23, 19, NULL, 'Рівненська область', 'м. Вараш', NULL),
(24, 19, NULL, 'Рівненська область', 'м. Вараш', NULL);

-- 
-- Вывод данных для таблицы power_consumption
--
INSERT INTO power_consumption VALUES
(1, 1, '10-10-2016T14:10:00', 0.01, 22000, 1),
(2, 1, '10-10-2016T15:10:00', 0.01, 26000, 1),
(3, 1, '10-10-2016T16:10:00', 0.01, 28000, 1),
(4, 1, '10-10-2016T17:10:00', 0.02, 35000, 1),
(5, 1, '10-10-2016T18:10:00', 0.03, 40000, 1),
(6, 2, '10-10-2016T14:10:00', 0.1, 20000, 1),
(7, 2, '10-10-2016T15:10:00', 0.1, 22000, 1),
(8, 3, '10-10-2016T14:10:00', 0.01, 20000, 1),
(9, 3, '10-10-2016T15:10:00', 0.02, 31000, 1),
(10, 3, '10-10-2016T16:10:00', 0.01, 21000, 1),
(11, 6, '10-10-2016T13:10:00', 0.01, 21000, 1),
(12, 6, '10-10-2016T14:10:00', 0.01, 29000, 1),
(13, 7, '10-10-2016T13:10:00', 0.01, 28000, 1),
(14, 7, '10-10-2016T14:10:00', 0.01, 26000, 1),
(15, 8, '10-10-2016T13:10:00', 0.01, 24000, 1),
(16, 8, '10-10-2016T14:10:00', 0.01, 21000, 1),
(17, 4, '10-10-2016T13:10:00', 0.01, 24000, 1),
(18, 4, '10-10-2016T14:10:00', 0.01, 26000, 1),
(19, 5, '10-10-2016T13:10:00', 0.01, 24000, 1),
(20, 5, '10-10-2016T14:10:00', 0.01, 24000, 1),
(21, 9, '10-10-2016T14:10:00', 0.01, 22000, 1),
(22, 9, '10-10-2016T15:10:00', 0.01, 26000, 1),
(23, 9, '10-10-2016T16:10:00', 0.01, 28000, 1),
(24, 9, '10-10-2016T17:10:00', 0.02, 35000, 1),
(25, 9, '10-10-2016T18:10:00', 0.03, 40000, 1),
(26, 10, '10-10-2016T14:10:00', 0.1, 20000, 1),
(27, 10, '10-10-2016T15:10:00', 0.1, 22000, 1),
(28, 11, '10-10-2016T14:10:00', 0.01, 20000, 1),
(29, 11, '10-10-2016T15:10:00', 0.02, 31000, 1),
(30, 11, '10-10-2016T16:10:00', 0.01, 21000, 1),
(31, 14, '10-10-2016T13:10:00', 0.01, 21000, 1),
(32, 14, '10-10-2016T14:10:00', 0.01, 29000, 1),
(33, 15, '10-10-2016T13:10:00', 0.01, 28000, 1),
(34, 15, '10-10-2016T14:10:00', 0.01, 26000, 1),
(35, 16, '10-10-2016T13:10:00', 0.01, 24000, 1),
(36, 16, '10-10-2016T14:10:00', 0.01, 21000, 1),
(37, 12, '10-10-2016T13:10:00', 0.01, 24000, 1),
(38, 12, '10-10-2016T14:10:00', 0.01, 26000, 1),
(39, 13, '10-10-2016T13:10:00', 0.01, 24000, 1),
(40, 13, '10-10-2016T14:10:00', 0.01, 24000, 1),
(41, 17, '10-10-2016T14:10:00', 0.01, 22000, 1),
(42, 17, '10-10-2016T15:10:00', 0.01, 26000, 1),
(43, 17, '10-10-2016T16:10:00', 0.01, 28000, 1),
(44, 17, '10-10-2016T17:10:00', 0.02, 35000, 1),
(45, 17, '10-10-2016T18:10:00', 0.03, 40000, 1),
(46, 18, '10-10-2016T14:10:00', 0.1, 20000, 1),
(47, 18, '10-10-2016T15:10:00', 0.1, 22000, 1),
(48, 19, '10-10-2016T14:10:00', 0.01, 20000, 1),
(49, 19, '10-10-2016T15:10:00', 0.02, 31000, 1),
(50, 19, '10-10-2016T16:10:00', 0.01, 21000, 1),
(51, 22, '10-10-2016T13:10:00', 0.01, 21000, 1),
(52, 22, '10-10-2016T14:10:00', 0.01, 29000, 1),
(53, 23, '10-10-2016T13:10:00', 0.01, 28000, 1),
(54, 23, '10-10-2016T14:10:00', 0.01, 26000, 1),
(55, 24, '10-10-2016T13:10:00', 0.01, 24000, 1),
(56, 24, '10-10-2016T14:10:00', 0.01, 21000, 1),
(57, 20, '10-10-2016T13:10:00', 0.01, 24000, 1),
(58, 20, '10-10-2016T14:10:00', 0.01, 26000, 1),
(59, 21, '10-10-2016T13:10:00', 0.01, 24000, 1),
(60, 21, '10-10-2016T14:10:00', 0.01, 24000, 1);

-- 
-- Восстановить предыдущий режим SQL (SQL mode)
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;