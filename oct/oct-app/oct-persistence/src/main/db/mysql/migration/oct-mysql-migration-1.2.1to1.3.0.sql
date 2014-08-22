/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


DROP TABLE IF EXISTS `OCT_GLOBAL_RULE_PARAM`;
DROP TABLE IF EXISTS `OCT_GLOBAL_RULE`;
DROP TABLE IF EXISTS `OCT_LOCAL_RULE_PARAM`;
DROP TABLE IF EXISTS `OCT_LOCAL_RULE`;


CREATE TABLE IF NOT EXISTS `OCT_GLOBAL_RULE` (
  `id` bigint(20) NOT NULL,
  `ruleType` varchar(255) DEFAULT NULL,
  `property_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7B76B6DB86EFF898` (`property_id`),
  CONSTRAINT `FK7B76B6DB86EFF898` FOREIGN KEY (`property_id`) REFERENCES `OCT_PROPERTY` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `OCT_GLOBAL_RULE` DISABLE KEYS */;
INSERT INTO `OCT_GLOBAL_RULE` (`id`, `ruleType`, `property_id`) VALUES
	(1, 'RANGE', 9),
	(2, 'JAVAEXP', 5);
/*!40000 ALTER TABLE `OCT_GLOBAL_RULE` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `OCT_GLOBAL_RULE_PARAM` (
  `id` bigint(20) NOT NULL,
  `parameterType` varchar(255) DEFAULT NULL,
  `value` text DEFAULT NULL,
  `rule_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK20AD6A09D523D828` (`rule_id`),
  CONSTRAINT `FK20AD6A09D523D828` FOREIGN KEY (`rule_id`) REFERENCES `OCT_GLOBAL_RULE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `OCT_GLOBAL_RULE_PARAM` DISABLE KEYS */;
INSERT INTO `OCT_GLOBAL_RULE_PARAM` (`id`, `parameterType`, `value`, `rule_id`) VALUES
	(1, 'MIN', '16y', 1),
	
	(2, 'JAVAEXP', 
'(bean.gp("country").equals("mt") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9a-z]*")) ||
(bean.gp("country").equals("ie") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9a-z]*")) ||
(bean.gp("country").equals("it") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9a-z]*")) ||
(bean.gp("country").equals("cz") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[1-7][0-9]{4}")) ||
(bean.gp("country").equals("bg") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9a-z]*")) ||
(bean.gp("country").equals("at") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{4}")) ||
(bean.gp("country").equals("de") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) ||
(bean.gp("country").equals("nl") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[1-9][0-9]{3}[a-z]{2}")) || 
(bean.gp("country").equals("pl") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) || 
(bean.gp("country").equals("ro") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{6}")) || 
(bean.gp("country").equals("sk") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[089][0-9]{4}")) || 
(bean.gp("country").equals("uk") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("(gir0aa)|((([a-z&&[^qvx]][0-9][0-9]?)|(([a-z&&[^qvx]][a-z&&[^ijz]][0-9][0-9]?)|(([a-z&&[^qvx]][0-9][a-hjkstuw])|([a-z&&[^qvx]][a-z&&[^ijz]][0-9][abehmnprvwxy]))))[0-9][a-z&&[^cikmov]]{2})")) || 
(bean.gp("country").equals("fr") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) || 
(bean.gp("country").equals("be") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{4}")) || 
(bean.gp("country").equals("cy") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{4}")) ||
(bean.gp("country").equals("dk") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{4}")) ||
(bean.gp("country").equals("ee") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) ||
(bean.gp("country").equals("es") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) ||
(bean.gp("country").equals("lv") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{4}")) ||
(bean.gp("country").equals("pt") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{7}")) ||
(bean.gp("country").equals("se") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) ||
(bean.gp("country").equals("si") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{4}")) ||
(bean.gp("country").equals("el") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) ||
(bean.gp("country").equals("fi") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) ||
(bean.gp("country").equals("hu") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{4}")) ||
(bean.gp("country").equals("lt") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) ||
(bean.gp("country").equals("lu") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{4}")) ||
(!bean.gp("country").matches("at|be|bg|cy|cz|de|dk|ee|el|es|fi|fr|hu|ie|it|lt|lu|lv|mt|nl|pl|pt|ro|se|si|sk|uk") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9a-z]*"))', 2);
/*!40000 ALTER TABLE `OCT_GLOBAL_RULE_PARAM` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `OCT_LOCAL_RULE` (
	`id` BIGINT(20) NOT NULL,
	`ruleType` VARCHAR(255) NULL DEFAULT NULL,
	`countryProperty_id` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK7B7AA6DB8C2F898` (`countryProperty_id`),
	CONSTRAINT `FK7B7AA6DB8C2F898` FOREIGN KEY (`countryProperty_id`) REFERENCES `OCT_COUNTRY_PROPERTY` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `OCT_LOCAL_RULE` DISABLE KEYS */;
INSERT INTO `OCT_LOCAL_RULE` (`id`, `ruleType`, `countryProperty_id`) VALUES
	(2, 'REGEXP', 26),
	(3, 'REGEXP', 27),
	(5, 'REGEXP', 83),
	(6, 'REGEXP', 82),
	(7, 'REGEXP', 84),
	(8, 'REGEXP', 106),
	(9, 'REGEXP', 108),
	(12, 'REGEXP', 6),
	(14, 'REGEXP', 15),
	(15, 'REGEXP', 16),
	(16, 'REGEXP', 17),
	(17, 'REGEXP', 14),
	(21, 'REGEXP', 64),
	(22, 'REGEXP', 65),
	(23, 'REGEXP', 66),
	(24, 'REGEXP', 74),
	(25, 'REGEXP', 155),
	(26, 'REGEXP', 156),
	(27, 'REGEXP', 157),
	(28, 'REGEXP', 158),
	(29, 'REGEXP', 159),
	(30, 'REGEXP', 160),
	(31, 'REGEXP', 161),
	(32, 'REGEXP', 162),
	(33, 'REGEXP', 164),
	
	(34, 'JAVAEXP', 150),
	(35, 'JAVAEXP', 89),
	(36, 'JAVAEXP', 116),
	
	(37, 'JAVAEXP', 241),
	(38, 'JAVAEXP', 242),
	(39, 'JAVAEXP', 237),
	(40, 'JAVAEXP', 225),
	(41, 'JAVAEXP', 240),
	(42, 'JAVAEXP', 222),
	
	(43, 'REGEXP', 38),
	(44, 'REGEXP', 101),
	(45, 'REGEXP', 100),
	(46, 'REGEXP', 105),
	(47, 'REGEXP', 120),
	(48, 'REGEXP', 122),
	(49, 'REGEXP', 121),
	(50, 'REGEXP', 144),
	(51, 'REGEXP', 134),
	(52, 'REGEXP', 133),
	
	(53, 'REGEXP', 80),
	(54, 'REGEXP', 79),
	(55, 'REGEXP', 81),
	(56, 'REGEXP', 36),
	(57, 'REGEXP', 37),
	(58, 'REGEXP', 39),
	(59, 'REGEXP', 40),
	(60, 'REGEXP', 141),
	(61, 'REGEXP', 145),
	(62, 'REGEXP', 163),
	(63, 'REGEXP', 110),
	(64, 'REGEXP', 13),
	(65, 'REGEXP', 140);
/*!40000 ALTER TABLE `OCT_LOCAL_RULE` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `OCT_LOCAL_RULE_PARAM` (
	`id` BIGINT(20) NOT NULL,
	`parameterType` VARCHAR(255) NULL DEFAULT NULL,
	`value` text NULL DEFAULT NULL,
	`rule_id` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK20ADDC09D5233828` (`rule_id`),
	CONSTRAINT `FK20ADDC09D5233828` FOREIGN KEY (`rule_id`) REFERENCES `OCT_LOCAL_RULE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `OCT_LOCAL_RULE_PARAM` DISABLE KEYS */;
INSERT INTO `OCT_LOCAL_RULE_PARAM` (`id`, `parameterType`, `value`, `rule_id`) VALUES
	(2, 'REGEXP', '[a-z][0-9]{7}', 2),
	(3, 'REGEXP', '[0-9]{7}|[0-9]{8}', 3),
	(5, 'REGEXP', '([0-9]{6}[a-z]{2})|([a-z]{2}[a-z][0-9]{6})|([a-z]{2}[a-z]{2}[0-9]{6})|([a-z]{2}[a-z]{3}[0-9]{6})|([a-z]{2}[0-9]{6})', 5),
	(6, 'REGEXP', '([a-z]{2}[0-9]{6})|([a-z]{2}[0-9]{7})', 6),
	(7, 'REGEXP', '[0-9][0-9]{6}[0-9]{4}', 7),
	(8, 'REGEXP', '[0-9]{11}', 8),
	(9, 'REGEXP', '[0-9]{11}|[0-9]{13}', 9),
	(12, 'REGEXP', '[0-9]{11}', 12),
	(14, 'REGEXP', '[0-9]{13}', 14),
	(15, 'REGEXP', '[0-9]{13}', 15),
	(16, 'REGEXP', '[0-9]{13}', 16),
	(17, 'REGEXP', '[a-z]{2}[0-9]{6}', 17),
	(21, 'REGEXP', '([0-9]){2}([a-z]){2}([0-9]){4}[0-9]', 21),
	(22, 'REGEXP', '[0-9]{7}|[0-9]{12}', 22),
	(23, 'REGEXP', '[0-9]{10}', 23),
	(24, 'REGEXP', '([a-z]){2}([0-9]){4}[0-9]', 24),
	(25, 'REGEXP', '([0-9]{4})|([0-9]{3}\\(((?=.)(?i)M*(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX]))\\))', 25),
	(26, 'REGEXP', '[0-9]{2}[0-9]{2}', 26),
	(27, 'REGEXP', '([0-9]{5})|([0-9]{7})', 27),
	(28, 'REGEXP', '[0-9]{9}', 28),
	(29, 'REGEXP', '[0-9]{6}', 29),
	(30, 'REGEXP', '[a-z0-9]*', 30),
	(31, 'REGEXP', '[a-z0-9]*', 31),
	(32, 'REGEXP', '[0-9]{6}', 32),
	(33, 'REGEXP', '([a-z0-9]{10,17})|[0-9]{10}', 33),
	
	(34, 'JAVAEXP', 'bean.gp("c").equals("uk") && bean.gp("country").equals("uk")', 34),
	(35, 'JAVAEXP', 'bean.gp("c").equals("ie") && bean.gp("country").equals("ie")', 35),
	(36, 'JAVAEXP', 'bean.gp("c").equals("nl") && bean.gp("country").equals("nl")', 36),
	
	(37, 'JAVAEXP', 'bean.gp("c").equals("ee") && (bean.gp("country").equals("ee") || bean.gp("nationality").equals("ee"))', 37),
	(38, 'JAVAEXP', 'bean.gp("c").equals("fi") && (bean.gp("country").equals("fi") || bean.gp("nationality").equals("fi"))', 38),
	(39, 'JAVAEXP', 'bean.gp("c").equals("sk") && (bean.gp("country").equals("sk") || bean.gp("nationality").equals("sk"))', 39),
	(40, 'JAVAEXP', 'bean.gp("c").equals("be") && (bean.gp("country").equals("be") || bean.gp("nationality").equals("be"))', 40),
	(41, 'JAVAEXP', 'bean.gp("c").equals("dk") && (bean.gp("country").equals("dk") || bean.gp("nationality").equals("dk"))', 41),
	(42, 'JAVAEXP', 'bean.gp("c").equals("de") && (bean.gp("country").equals("de") || bean.gp("nationality").equals("de"))', 42),
	
	(43, 'REGEXP', '[0-9]{1,10}', 43),
	(44, 'REGEXP', '([a-z]{2}[0-9]{7})|([0-9]{7}[a-z]{2})', 44),
	(45, 'REGEXP', '[a-z]{1,2}[0-9]{1,7}', 45),
	(46, 'REGEXP', '[0-9]{6}[0-9]{5}', 46),
	(47, 'REGEXP', '[a-z]{1}[0-9]{6}', 47),
	(48, 'REGEXP', '[0-9]{8}[a-z]{2}[0-9]{1}', 48),
	(49, 'REGEXP', '[0-9]{8}', 49),
	(50, 'REGEXP', '[0-9]{10}', 50),
	(51, 'REGEXP', '[0-9]{9}', 51),
	(52, 'REGEXP', '([a-z][0-9]{8})|([a-z]{2}[0-9]{7})', 52),
	
	(53, 'REGEXP', '[\\p{InGreek}a-z]{1,2}[0-9]{6}', 53),
	(54, 'REGEXP', '[a-z]{2}[0-9]{7}', 54),
	(55, 'REGEXP', '[0-9]{1,6}', 55),
	(56, 'REGEXP', '[0-9]{10}', 56),
	(57, 'REGEXP', '([bcej][0-9]{6})|(k[0-9]{8})|([ds]p[0-9]{7})', 57),
	(58, 'REGEXP', '[0-9]{7,8}', 58),
	(59, 'REGEXP', '([0-9]{9})|([0-9]{6}[a-z]{2}[0-9]{2})|([0-9]{6}[a-z]{2})|([a-z]{2}[0-9]{6})', 59),
	(60, 'REGEXP', '[0-9]{8}[a-z]', 60),
	(61, 'REGEXP', '[0-9]{10}', 61),
	(62, 'REGEXP', '[a-z0-9]*', 62),
	(63, 'REGEXP', '[a-z0-9]*', 63),
	(64, 'REGEXP', '[a-z0-9]*', 64),
	(65, 'REGEXP', '[a-z0-9]*', 65);
/*!40000 ALTER TABLE `OCT_LOCAL_RULE_PARAM` ENABLE KEYS */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
