/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE TABLE IF NOT EXISTS `OCT_ACCOUNT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `passHash` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `OCT_CONTACT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `insertDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateDate` timestamp,
  `version` int(11) NOT NULL DEFAULT '1',
  `email` text,
  `name` text,
  `organizers` text,
  `system_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK785287C1AAA9D6D7` (`system_id`),
  CONSTRAINT `FK785287C1AAA9D6D7` FOREIGN KEY (`system_id`) REFERENCES `OCT_SYSTEM_PREFS` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `OCT_COUNTRY` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `OCT_COUNTRY` DISABLE KEYS */;
INSERT INTO `OCT_COUNTRY` (`id`, `code`, `name`) VALUES
	(1, 'pl', 'oct.country.pl'),
	(2, 'de', 'oct.country.de'),
	(3, 'uk', 'oct.country.uk'),
	(4, 'fr', 'oct.country.fr'),
	(5, 'be', 'oct.country.be'),
	(6, 'ro', 'oct.country.ro'),
	(8, 'at', 'oct.country.at'),
	(9, 'lv', 'oct.country.lv'),
	(10, 'bg', 'oct.country.bg'),
	(11, 'cy', 'oct.country.cy'),
	(12, 'lt', 'oct.country.lt'),
	(13, 'lu', 'oct.country.lu'),
	(14, 'mt', 'oct.country.mt'),
	(15, 'nl', 'oct.country.nl'),
	(16, 'pt', 'oct.country.pt'),
	(17, 'sk', 'oct.country.sk'),
	(18, 'si', 'oct.country.si'),
	(19, 'cz', 'oct.country.cz'),
	(20, 'dk', 'oct.country.dk'),
	(21, 'ee', 'oct.country.ee'),
	(22, 'fi', 'oct.country.fi'),
	(23, 'el', 'oct.country.el'),
	(24, 'es', 'oct.country.es'),
	(25, 'hu', 'oct.country.hu'),
	(26, 'ie', 'oct.country.ie'),
	(27, 'se', 'oct.country.se'),
	(28, 'it', 'oct.country.it');
/*!40000 ALTER TABLE `OCT_COUNTRY` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `OCT_COUNTRY_LANG` (
  `COUNTRY_ID` bigint(20) NOT NULL,
  `LANGUAGE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`COUNTRY_ID`,`LANGUAGE_ID`),
  KEY `FKA402F1F6C25D8AE4` (`COUNTRY_ID`),
  KEY `FKA402F1F6513A6710` (`LANGUAGE_ID`),
  CONSTRAINT `FKA402F1F6513A6710` FOREIGN KEY (`LANGUAGE_ID`) REFERENCES `OCT_LANG` (`id`),
  CONSTRAINT `FKA402F1F6C25D8AE4` FOREIGN KEY (`COUNTRY_ID`) REFERENCES `OCT_COUNTRY` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `OCT_COUNTRY_PROPERTY` (
  `id` bigint(20) NOT NULL,
  `required` tinyint(4) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `property_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK49CFF1DD86EFF898` (`property_id`),
  KEY `FK49CFF1DDC25D8AE4` (`country_id`),
  CONSTRAINT `FK49CFF1DDC25D8AE4` FOREIGN KEY (`country_id`) REFERENCES `OCT_COUNTRY` (`id`),
  CONSTRAINT `FK49CFF1DD86EFF898` FOREIGN KEY (`property_id`) REFERENCES `OCT_PROPERTY` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `OCT_COUNTRY_PROPERTY` DISABLE KEYS */;
INSERT INTO `OCT_COUNTRY_PROPERTY` (`id`, `required`, `country_id`, `property_id`) VALUES
	(1, 1, 1, 3),
	(3, 1, 1, 5),
	(4, 1, 1, 6),
	(5, 1, 1, 7),
	(6, 1, 1, 19),
	(7, 1, 6, 3),
	(9, 1, 6, 5),
	(10, 1, 6, 6),
	(11, 1, 6, 7),
	(12, 1, 6, 9),
	(13, 1, 6, 12),
	(14, 1, 6, 13),
	(15, 1, 6, 16),
	(16, 1, 6, 17),
	(17, 1, 6, 21),
	(19, 1, 8, 3),
	(21, 1, 8, 5),
	(22, 1, 8, 6),
	(23, 1, 8, 7),
	(24, 1, 8, 9),
	(25, 1, 8, 10),
	(26, 1, 8, 12),
	(27, 1, 8, 13),
	(28, 1, 5, 3),
	(30, 1, 5, 5),
	(31, 1, 5, 6),
	(32, 1, 5, 7),
	(33, 1, 5, 9),
	(34, 1, 5, 10),
	(35, 1, 10, 2),
	(36, 1, 10, 15),
	(37, 1, 11, 12),
	(38, 1, 11, 13),
	(39, 1, 19, 12),
	(40, 1, 19, 13),
	(41, 1, 20, 3),
	(43, 1, 20, 5),
	(44, 1, 20, 6),
	(45, 1, 20, 7),
	(46, 1, 20, 9),
	(47, 1, 20, 10),
	(48, 1, 21, 3),
	(50, 1, 21, 5),
	(51, 1, 21, 6),
	(52, 1, 21, 7),
	(53, 1, 21, 9),
	(54, 1, 21, 10),
	(55, 1, 22, 7),
	(56, 1, 22, 9),
	(58, 1, 4, 3),
	(60, 1, 4, 5),
	(61, 1, 4, 6),
	(62, 1, 4, 7),
	(63, 1, 4, 9),
	(64, 1, 4, 12),
	(65, 1, 4, 13),
	(66, 1, 4, 14),
	(67, 1, 2, 3),
	(69, 1, 2, 5),
	(70, 1, 2, 6),
	(71, 1, 2, 7),
	(72, 1, 2, 9),
	(73, 1, 2, 10),
	(74, 1, 4, 18),
	(76, 1, 23, 1),
	(77, 1, 23, 2),
	(78, 1, 23, 9),
	(79, 1, 23, 12),
	(80, 1, 23, 13),
	(81, 1, 23, 17),
	(82, 1, 25, 12),
	(83, 1, 25, 13),
	(84, 1, 25, 15),
	(85, 1, 26, 3),
	(87, 0, 26, 5),
	(88, 1, 26, 6),
	(89, 1, 26, 7),
	(92, 1, 28, 3),
	(94, 1, 28, 5),
	(95, 1, 28, 6),
	(96, 1, 28, 7),
	(97, 1, 28, 9),
	(98, 1, 28, 10),
	(99, 1, 28, 11),
	(100, 1, 28, 12),
	(101, 1, 28, 13),
	(102, 1, 9, 1),
	(103, 1, 9, 9),
	(104, 1, 9, 10),
	(105, 1, 9, 16),
	(106, 1, 12, 15),
	(107, 1, 13, 10),
	(108, 1, 13, 19),
	(109, 1, 14, 9),
	(110, 1, 14, 13),
	(111, 1, 15, 1),
	(112, 1, 15, 3),
	(114, 1, 15, 5),
	(115, 1, 15, 6),
	(116, 1, 15, 7),
	(117, 1, 15, 9),
	(118, 1, 15, 10),
	(119, 1, 16, 9),
	(120, 1, 16, 12),
	(121, 1, 16, 13),
	(122, 1, 16, 22),
	(123, 1, 17, 1),
	(124, 1, 17, 3),
	(126, 1, 17, 5),
	(127, 1, 17, 6),
	(128, 1, 17, 7),
	(129, 1, 17, 9),
	(130, 1, 17, 10),
	(131, 1, 18, 9),
	(132, 1, 18, 10),
	(133, 1, 18, 12),
	(134, 1, 18, 13),
	(135, 1, 24, 3),
	(137, 1, 24, 5),
	(138, 1, 24, 6),
	(139, 1, 24, 7),
	(140, 1, 24, 12),
	(141, 1, 24, 13),
	(142, 1, 27, 9),
	(143, 1, 27, 10),
	(144, 1, 27, 23),
	(145, 1, 27, 24),
	(146, 1, 3, 3),
	(148, 1, 3, 5),
	(149, 1, 3, 6),
	(150, 1, 3, 7),
	(151, 1, 3, 9),
	(153, 1, 26, 9),
	(154, 1, 26, 10),
	(155, 1, 4, 26),
	(156, 1, 4, 27),
	(157, 1, 4, 28),
	(158, 1, 4, 29),
	(159, 1, 4, 30),
	(160, 1, 4, 31),
	(161, 1, 4, 32),
	(162, 1, 4, 33),
	(163, 1, 4, 34),
	(164, 1, 4, 35),
	(165, 1, 1, 37),
	(166, 1, 2, 37),
	(167, 1, 3, 37),
	(168, 1, 4, 37),
	(169, 1, 5, 37),
	(170, 1, 6, 37),
	(172, 1, 8, 37),
	(173, 1, 9, 37),
	(174, 1, 10, 37),
	(175, 1, 11, 37),
	(176, 1, 12, 37),
	(177, 1, 13, 37),
	(178, 1, 14, 37),
	(179, 1, 15, 37),
	(180, 1, 16, 37),
	(181, 1, 17, 37),
	(182, 1, 18, 37),
	(183, 1, 19, 37),
	(184, 1, 20, 37),
	(185, 1, 21, 37),
	(186, 1, 22, 37),
	(187, 1, 23, 37),
	(188, 1, 24, 37),
	(189, 1, 25, 37),
	(190, 1, 26, 37),
	(191, 1, 27, 37),
	(192, 1, 28, 37),
	(193, 1, 1, 38),
	(194, 1, 2, 38),
	(195, 1, 3, 38),
	(196, 1, 4, 38),
	(197, 1, 5, 38),
	(198, 1, 6, 38),
	(200, 1, 8, 38),
	(201, 1, 9, 38),
	(202, 1, 10, 38),
	(203, 1, 11, 38),
	(204, 1, 12, 38),
	(205, 1, 13, 38),
	(206, 1, 14, 38),
	(207, 1, 15, 38),
	(208, 1, 16, 38),
	(209, 1, 17, 38),
	(210, 1, 18, 38),
	(211, 1, 19, 38),
	(212, 1, 20, 38),
	(213, 1, 21, 38),
	(214, 1, 22, 38),
	(215, 1, 23, 38),
	(216, 1, 24, 38),
	(217, 1, 25, 38),
	(218, 1, 26, 38),
	(219, 1, 27, 38),
	(220, 1, 28, 38),
	(221, 1, 1, 39),
	(222, 1, 2, 39),
	(223, 1, 3, 39),
	(224, 1, 4, 39),
	(225, 1, 5, 39),
	(226, 1, 6, 39),
	(228, 1, 8, 39),
	(229, 1, 9, 39),
	(230, 1, 10, 39),
	(231, 1, 11, 39),
	(232, 1, 12, 39),
	(233, 1, 13, 39),
	(234, 1, 14, 39),
	(235, 1, 15, 39),
	(236, 1, 16, 39),
	(237, 1, 17, 39),
	(238, 1, 18, 39),
	(239, 1, 19, 39),
	(240, 1, 20, 39),
	(241, 1, 21, 39),
	(242, 1, 22, 39),
	(243, 1, 23, 39),
	(244, 1, 24, 39),
	(245, 1, 25, 39),
	(246, 1, 26, 39),
	(247, 1, 27, 39),
	(248, 1, 28, 39);
/*!40000 ALTER TABLE `OCT_COUNTRY_PROPERTY` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `OCT_INITIATIVE_DESC` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `insertDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateDate` timestamp,
  `version` int(11) NOT NULL DEFAULT '1',
  `objectives` text,
  `subjectMatter` text,
  `title` text NOT NULL,
  `url` varchar(100) DEFAULT NULL,
  `language_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `language_id` (`language_id`),
  KEY `FK136443A5513A6710` (`language_id`),
  CONSTRAINT `FK136443A5513A6710` FOREIGN KEY (`language_id`) REFERENCES `OCT_LANG` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `OCT_LANG` (
  `id` bigint(20) NOT NULL,
  `code` varchar(3) NOT NULL,
  `name` varchar(64) NOT NULL,
  `display_order` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `display_order` (`display_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `OCT_LANG` DISABLE KEYS */;
INSERT INTO `OCT_LANG` (`id`, `code`, `name`, `display_order`) VALUES
	(1, 'cs', 'oct.lang.Czech',2),
	(2, 'da', 'oct.lang.Danish',3),
	(3, 'de', 'oct.lang.German',4),
	(4, 'et', 'oct.lang.Estonian',5),
	(5, 'el', 'oct.lang.Greek',6),
	(6, 'en', 'oct.lang.English',7),
	(7, 'es', 'oct.lang.Spanish',8),
	(8, 'fr', 'oct.lang.French',9),
	(9, 'it', 'oct.lang.Italian',11),
	(10, 'lv', 'oct.lang.Latvian',12),
	(11, 'lt', 'oct.lang.Lithuanian',13),
	(12, 'ga', 'oct.lang.Gaelic',10),
	(13, 'hu', 'oct.lang.Hungarian',14),
	(14, 'mt', 'oct.lang.Maltese',15),
	(15, 'nl', 'oct.lang.Dutch',16),
	(16, 'pl', 'oct.lang.Polish',17),
	(17, 'pt', 'oct.lang.Portuguese',18),
	(18, 'ro', 'oct.lang.Romanian',19),
	(19, 'sk', 'oct.lang.Slovak',20),
	(20, 'sl', 'oct.lang.Slovenian',21),
	(21, 'fi', 'oct.lang.Finnish',22),
	(22, 'sv', 'oct.lang.Swedish',23),
	(23, 'bg', 'oct.lang.Bulgarian',1);
/*!40000 ALTER TABLE `OCT_LANG` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `OCT_PROPERTY` (
  `id` bigint(20) NOT NULL,
  `name` varchar(64) NOT NULL,
  `priority` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FK789EEE34CBC45847` (`group_id`),
  CONSTRAINT `FK789EEE34CBC45847` FOREIGN KEY (`group_id`) REFERENCES `OCT_PROPERTY_GROUP` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `OCT_PROPERTY` DISABLE KEYS */;
INSERT INTO `OCT_PROPERTY` (`id`, `name`, `priority`, `type`, `group_id`) VALUES
	(1, 'oct.property.name.at.birth', 96, 'ALPHANUMERIC', 1),
	(2, 'oct.property.fathers.name', 97, 'ALPHANUMERIC', 1),
	(3, 'oct.property.address', 89, 'LARGETEXT', 2),
	(5, 'oct.property.postal.code', 88, 'ALPHANUMERIC', 2),
	(6, 'oct.property.city', 87, 'ALPHANUMERIC', 2),
	(7, 'oct.property.country', 86, 'COUNTRY', 2),
	(8, 'oct.property.state', 0, 'ALPHANUMERIC', 2),
	(9, 'oct.property.date.of.birth', 94, 'DATE', 1),
	(10, 'oct.property.place.of.birth', 93, 'ALPHANUMERIC', 1),
	(11, 'oct.property.issuing.authority', 0, 'ALPHANUMERIC', 1),
	(12, 'oct.property.passport', 0, 'ALPHANUMERIC', 3),
	(13, 'oct.property.id.card', 0, 'ALPHANUMERIC', 3),
	(14, 'oct.property.residence.permit', 0, 'ALPHANUMERIC', 3),
	(15, 'oct.property.personal.number', 0, 'ALPHANUMERIC', 3),
	(16, 'oct.property.personal.id', 0, 'ALPHANUMERIC', 3),
	(17, 'oct.property.permanent.residence', 0, 'ALPHANUMERIC', 3),
	(18, 'oct.property.driving.license', 0, 'ALPHANUMERIC', 3),
	(19, 'oct.property.national.id.number', 0, 'ALPHANUMERIC', 3),
	(20, 'oct.property.social.security.id', 0, 'ALPHANUMERIC', 3),
	(21, 'oct.property.registration.certificate', 0, 'ALPHANUMERIC', 3),
	(22, 'oct.property.citizens.card', 0, 'ALPHANUMERIC', 3),
	(23, 'oct.property.personal.no.in.passport', 0, 'ALPHANUMERIC', 3),
	(24, 'oct.property.personal.no.in.id.card', 0, 'ALPHANUMERIC', 3),
	(26, 'oct.property.other.1', 0, 'ALPHANUMERIC', 3),
	(27, 'oct.property.other.2', 0, 'ALPHANUMERIC', 3),
	(28, 'oct.property.other.3', 0, 'ALPHANUMERIC', 3),
	(29, 'oct.property.other.4', 0, 'ALPHANUMERIC', 3),
	(30, 'oct.property.other.5', 0, 'ALPHANUMERIC', 3),
	(31, 'oct.property.other.6', 0, 'ALPHANUMERIC', 3),
	(32, 'oct.property.other.7', 0, 'ALPHANUMERIC', 3),
	(33, 'oct.property.other.8', 0, 'ALPHANUMERIC', 3),
	(34, 'oct.property.other.9', 0, 'ALPHANUMERIC', 3),
	(35, 'oct.property.other.10', 0, 'ALPHANUMERIC', 3),
	(36, 'oct.property.date.of.birth.at', 94, 'DATE', 1),
	(37, 'oct.property.firstname', 99, 'ALPHANUMERIC', 1),
	(38, 'oct.property.lastname', 98, 'ALPHANUMERIC', 1),
	(39, 'oct.property.nationality', 92, 'NATIONALITY', 1);
/*!40000 ALTER TABLE `OCT_PROPERTY` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `OCT_PROPERTY_GROUP` (
  `id` bigint(20) NOT NULL,
  `multichoice` tinyint(4) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `priority` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `OCT_PROPERTY_GROUP` DISABLE KEYS */;
INSERT INTO `OCT_PROPERTY_GROUP` (`id`, `multichoice`, `name`, `priority`) VALUES
	(1, 0, 'oct.group.general', 2),
	(2, 0, 'oct.group.address', 3),
	(3, 1, 'oct.group.id', 1);
/*!40000 ALTER TABLE `OCT_PROPERTY_GROUP` ENABLE KEYS */;


CREATE TABLE IF NOT EXISTS `OCT_PROPERTY_VALUE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` text,
  `property_id` bigint(20) DEFAULT NULL,
  `signature_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7AEF73861BC05E92` (`property_id`),
  KEY `FK7AEF73861D72D746` (`signature_id`),
  CONSTRAINT `FK7AEF73861BC05E92` FOREIGN KEY (`property_id`) REFERENCES `OCT_COUNTRY_PROPERTY` (`id`),
  CONSTRAINT `FK7AEF73861D72D746` FOREIGN KEY (`signature_id`) REFERENCES `OCT_SIGNATURE` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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
	(21, 'REGEXP', '([0-9]){2}([a-z] ){2}([0-9]){4}[0-9]', 21),
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
	
	(53, 'REGEXP', '[\p{InGreek}a-z]{1,2}[0-9]{6}', 53),
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


CREATE TABLE IF NOT EXISTS `OCT_SIGNATURE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `insertDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateDate` timestamp,
  `version` int(11) NOT NULL DEFAULT '1',
  `dateOfSignature` datetime NOT NULL,
  `fingerprint` varchar(255) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `countryToSignFor_id` bigint(20) NOT NULL,
  `description_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fingerprint` (`fingerprint`),  
  KEY `FK64E94196933581F` (`countryToSignFor_id`),
  KEY `FK64E94198EFFF971` (`description_id`),
  CONSTRAINT `FK64E94196933581F` FOREIGN KEY (`countryToSignFor_id`) REFERENCES `OCT_COUNTRY` (`id`),
  CONSTRAINT `FK64E94198EFFF971` FOREIGN KEY (`description_id`) REFERENCES `OCT_INITIATIVE_DESC` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `OCT_SYSTEM_PREFS` (
  `id` bigint(20) NOT NULL,
  `insertDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateDate` timestamp,
  `version` int(11) NOT NULL DEFAULT 1,
  `CERT_CONTENT_TYPE` varchar(255) DEFAULT NULL,
  `CERT_FILE_NAME` varchar(255) DEFAULT NULL,
  `collecting` tinyint(4) DEFAULT NULL,
  `commissionRegisterUrl` longtext,
  `eciDataTimestamp` datetime DEFAULT NULL,
  `FILE_STORE` varchar(255) DEFAULT NULL,
  `publicKey` text,
  `registrationDate` datetime DEFAULT NULL,
  `registrationNumber` varchar(64) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `defaultDescription_id` bigint(20) DEFAULT NULL,
  `defaultLanguage_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `CERT_CONTENT_TYPE` (`CERT_CONTENT_TYPE`),
  UNIQUE KEY `CERT_FILE_NAME` (`CERT_FILE_NAME`),
  UNIQUE KEY `registrationDate` (`registrationDate`),
  UNIQUE KEY `registrationNumber` (`registrationNumber`),
  KEY `FK6BCFAD5F95B49F2` (`defaultDescription_id`),
  KEY `FK6BCFAD5F2F9FCE2F` (`defaultLanguage_id`),
  CONSTRAINT `FK6BCFAD5F2F9FCE2F` FOREIGN KEY (`defaultLanguage_id`) REFERENCES `OCT_LANG` (`id`),
  CONSTRAINT `FK6BCFAD5F95B49F2` FOREIGN KEY (`defaultDescription_id`) REFERENCES `OCT_INITIATIVE_DESC` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TRIGGER system_prefs_ts BEFORE INSERT ON OCT_SYSTEM_PREFS
FOR EACH ROW set new.updateDate = now();

/*!40000 ALTER TABLE `OCT_SYSTEM_PREFS` DISABLE KEYS */;
INSERT INTO `OCT_SYSTEM_PREFS` (`id`, `CERT_CONTENT_TYPE`, `CERT_FILE_NAME`, `collecting`, `commissionRegisterUrl`, `eciDataTimestamp`, `FILE_STORE`, `publicKey`, `registrationDate`, `registrationNumber`, `state`, `defaultDescription_id`, `defaultLanguage_id`) VALUES
	(1, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 'DEPLOYED', NULL, 6);
/*!40000 ALTER TABLE `OCT_SYSTEM_PREFS` ENABLE KEYS */;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
