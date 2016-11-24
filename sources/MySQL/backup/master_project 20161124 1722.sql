-- ������ ������������ Devart dbForge Studio for MySQL, ������ 6.1.166.0
-- �������� �������� ��������: http://www.devart.com/ru/dbforge/mysql/studio
-- ���� �������: 24.11.2016 17:22:58
-- ������ �������: 5.6.19
-- ������ �������: 4.1

-- 
-- ���������� ������� ������
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- ���������� ����� SQL (SQL mode)
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- ��������� ���� ������ �� ���������
--
USE master_project;

--
-- �������� ��� ������� electrical_station
--
DROP TABLE IF EXISTS electrical_station;
CREATE TABLE electrical_station (
  id INT(11) NOT NULL AUTO_INCREMENT,
  description_id INT(11) NOT NULL,
  type ENUM('"TERMAL"','"NUCLEAR"','"HYDRO"','"SOLAR"','"WIND"') NOT NULL,
  is_active TINYINT(1) NOT NULL,
  name VARCHAR(50) NOT NULL,
  region VARCHAR(50) DEFAULT NULL,
  area VARCHAR(50) DEFAULT NULL,
  location VARCHAR(100) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- �������� ��� ������� transformer
--
DROP TABLE IF EXISTS transformer;
CREATE TABLE transformer (
  id INT(11) NOT NULL AUTO_INCREMENT,
  parent_transformer_id INT(11) DEFAULT NULL,
  parent_electrical_station_id INT(11) DEFAULT NULL,
  region VARCHAR(50) DEFAULT NULL,
  area VARCHAR(50) DEFAULT NULL,
  location VARCHAR(100) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_transformer_electrical_station_id FOREIGN KEY (parent_electrical_station_id)
    REFERENCES electrical_station(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_transformer_transformer_id FOREIGN KEY (parent_transformer_id)
    REFERENCES transformer(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- �������� ��� ������� power_consumption
--
DROP TABLE IF EXISTS power_consumption;
CREATE TABLE power_consumption (
  id INT(11) NOT NULL AUTO_INCREMENT,
  timestamp CHAR(20) NOT NULL,
  losses DOUBLE DEFAULT NULL,
  electric_power INT(11) NOT NULL,
  transformer_id INT(11) NOT NULL,
  was_enabled TINYINT(1) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_power_consumption_transformer_id FOREIGN KEY (transformer_id)
    REFERENCES transformer(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

-- 
-- ����� ������ ��� ������� electrical_station
--

-- ������� master_project.electrical_station �� �������� ������

-- 
-- ����� ������ ��� ������� transformer
--

-- ������� master_project.transformer �� �������� ������

-- 
-- ����� ������ ��� ������� power_consumption
--

-- ������� master_project.power_consumption �� �������� ������

-- 
-- ������������ ���������� ����� SQL (SQL mode)
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- ��������� ������� ������
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;