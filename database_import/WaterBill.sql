-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.6.0-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for billwater
CREATE DATABASE IF NOT EXISTS `billwater` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `billwater`;

-- Dumping structure for table billwater.bac_tien_ho_ngheo
CREATE TABLE IF NOT EXISTS `bac_tien_ho_ngheo` (
  `id` bigint(20) NOT NULL,
  `ten_bac` varchar(255) DEFAULT NULL,
  `gia_tri_bac` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.bac_tien_ho_ngheo: ~10 rows (approximately)
/*!40000 ALTER TABLE `bac_tien_ho_ngheo` DISABLE KEYS */;
/*!40000 ALTER TABLE `bac_tien_ho_ngheo` ENABLE KEYS */;

-- Dumping structure for table billwater.bac_tien_ho_thuong
CREATE TABLE IF NOT EXISTS `bac_tien_ho_thuong` (
  `id` bigint(20) NOT NULL,
  `ten_bac` varchar(255) DEFAULT NULL,
  `gia_tri_bac` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.bac_tien_ho_thuong: ~10 rows (approximately)
/*!40000 ALTER TABLE `bac_tien_ho_thuong` DISABLE KEYS */;
/*!40000 ALTER TABLE `bac_tien_ho_thuong` ENABLE KEYS */;

-- Dumping structure for table billwater.databasechangelog
CREATE TABLE IF NOT EXISTS `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.databasechangelog: ~16 rows (approximately)
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;

-- Dumping structure for table billwater.databasechangeloglock
CREATE TABLE IF NOT EXISTS `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.databasechangeloglock: ~1 rows (approximately)
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
	(1, b'0', NULL, NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;

-- Dumping structure for table billwater.hoa_don
CREATE TABLE IF NOT EXISTS `hoa_don` (
  `id` bigint(20) NOT NULL,
  `ten_chu_ho` varchar(255) DEFAULT NULL,
  `thang_su_dung` bigint(20) DEFAULT NULL,
  `chi_so_moi` bigint(20) DEFAULT NULL,
  `chi_so_cu` bigint(20) DEFAULT NULL,
  `so_nuoc` bigint(20) DEFAULT NULL,
  `thanh_tien` bigint(20) DEFAULT NULL,
  `tien_thue` bigint(20) DEFAULT NULL,
  `tong_tien` bigint(20) DEFAULT NULL,
  `ngay_thanh_toan` datetime(6),
  `trang_thai_thanh_toan` varchar(255) DEFAULT NULL,
  `thue_id` bigint(20) DEFAULT NULL,
  `bac_ho_ngheo_id` bigint(20) DEFAULT NULL,
  `bac_ho_thuong_id` bigint(20) DEFAULT NULL,
  `giadinh_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_hoa_don__thue_id` (`thue_id`),
  UNIQUE KEY `ux_hoa_don__bac_ho_ngheo_id` (`bac_ho_ngheo_id`),
  UNIQUE KEY `ux_hoa_don__bac_ho_thuong_id` (`bac_ho_thuong_id`),
  UNIQUE KEY `ux_hoa_don__giadinh_id` (`giadinh_id`),
  CONSTRAINT `fk_hoa_don__bac_ho_ngheo_id` FOREIGN KEY (`bac_ho_ngheo_id`) REFERENCES `bac_tien_ho_ngheo` (`id`),
  CONSTRAINT `fk_hoa_don__bac_ho_thuong_id` FOREIGN KEY (`bac_ho_thuong_id`) REFERENCES `bac_tien_ho_thuong` (`id`),
  CONSTRAINT `fk_hoa_don__giadinh_id` FOREIGN KEY (`giadinh_id`) REFERENCES `ho_gia_dinh` (`id`),
  CONSTRAINT `fk_hoa_don__thue_id` FOREIGN KEY (`thue_id`) REFERENCES `thue` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.hoa_don: ~10 rows (approximately)
/*!40000 ALTER TABLE `hoa_don` DISABLE KEYS */;
/*!40000 ALTER TABLE `hoa_don` ENABLE KEYS */;

-- Dumping structure for table billwater.ho_gia_dinh
CREATE TABLE IF NOT EXISTS `ho_gia_dinh` (
  `id` bigint(20) NOT NULL,
  `ten_chu_ho` varchar(255) DEFAULT NULL,
  `ma_ho` varchar(255) DEFAULT NULL,
  `so_can_cuoc` varchar(255) DEFAULT NULL,
  `loai_ho` varchar(255) DEFAULT NULL,
  `so_ho_ngheo` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sdt` varchar(255) DEFAULT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `taikhoan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_ho_gia_dinh__taikhoan_id` (`taikhoan_id`),
  CONSTRAINT `fk_ho_gia_dinh__taikhoan_id` FOREIGN KEY (`taikhoan_id`) REFERENCES `tai_khoan` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.ho_gia_dinh: ~10 rows (approximately)
/*!40000 ALTER TABLE `ho_gia_dinh` DISABLE KEYS */;
/*!40000 ALTER TABLE `ho_gia_dinh` ENABLE KEYS */;

-- Dumping structure for table billwater.jhi_authority
CREATE TABLE IF NOT EXISTS `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.jhi_authority: ~2 rows (approximately)
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` (`name`) VALUES
	('ROLE_ADMIN'),
	('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;

-- Dumping structure for table billwater.jhi_user
CREATE TABLE IF NOT EXISTS `jhi_user` (
  `id` bigint(20) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.jhi_user: ~2 rows (approximately)
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES
	(1, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', b'1', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL),
	(2, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', b'1', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL);
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;

-- Dumping structure for table billwater.jhi_user_authority
CREATE TABLE IF NOT EXISTS `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.jhi_user_authority: ~3 rows (approximately)
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES
	(1, 'ROLE_ADMIN'),
	(1, 'ROLE_USER'),
	(2, 'ROLE_USER');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;

-- Dumping structure for table billwater.sequence_generator
CREATE TABLE IF NOT EXISTS `sequence_generator` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) unsigned NOT NULL,
  `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1;

-- Dumping data for table billwater.sequence_generator: ~1 rows (approximately)
/*!40000 ALTER TABLE `sequence_generator` DISABLE KEYS */;
INSERT INTO `sequence_generator` (`next_not_cached_value`, `minimum_value`, `maximum_value`, `start_value`, `increment`, `cache_size`, `cycle_option`, `cycle_count`) VALUES
	(51050, 1, 9223372036854775806, 1050, 50, 1000, 0, 0);
/*!40000 ALTER TABLE `sequence_generator` ENABLE KEYS */;

-- Dumping structure for table billwater.tai_khoan
CREATE TABLE IF NOT EXISTS `tai_khoan` (
  `id` bigint(20) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.tai_khoan: ~10 rows (approximately)
/*!40000 ALTER TABLE `tai_khoan` DISABLE KEYS */;
/*!40000 ALTER TABLE `tai_khoan` ENABLE KEYS */;

-- Dumping structure for table billwater.thue
CREATE TABLE IF NOT EXISTS `thue` (
  `id` bigint(20) NOT NULL,
  `ten_thue` varchar(255) DEFAULT NULL,
  `gia_tri_thue` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table billwater.thue: ~10 rows (approximately)
/*!40000 ALTER TABLE `thue` DISABLE KEYS */;
/*!40000 ALTER TABLE `thue` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
