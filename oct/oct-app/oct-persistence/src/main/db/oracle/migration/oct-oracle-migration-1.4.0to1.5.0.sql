SET SCAN OFF;


ALTER TABLE OCT_SIGNATURE ADD (
  ANNEXREVISION NUMBER(2, 0) DEFAULT 0
);

ALTER TABLE OCT_COUNTRY_PROPERTY ADD (
  MARKEDASDELETED NUMBER(2, 0) DEFAULT 0
);


UPDATE OCT_GLOBAL_RULE_PARAM SET VALUE = 
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
(bean.gp("country").equals("hr") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9]{5}")) ||
(!bean.gp("country").matches("at|be|bg|cy|cz|de|dk|ee|el|es|fi|fr|hu|ie|it|lt|lu|lv|mt|nl|pl|pt|ro|se|si|sk|uk|hr") && bean.gp("postal.code").replaceAll(pattern, "").toLowerCase().matches("[0-9a-z]*"))'  where id = 2;


INSERT INTO OCT_LOCAL_RULE (id, ruleType, countryProperty_id, errorMessage, canBeSkipped) VALUES (66, 'REGEXP', 256, NULL, 1);
INSERT INTO OCT_LOCAL_RULE_PARAM (id, parameterType, value, rule_id) VALUES (66, 'REGEXP', '[0-9]{11}', 66);


INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (257, 1, 13, 3);
INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (258, 1, 13, 5);
INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (259, 1, 13, 6);
INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (260, 1, 13, 7);
INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (261, 1, 13, 9);

DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id = 9;
DELETE FROM OCT_LOCAL_RULE WHERE id = 9; 
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id = 108;

INSERT INTO OCT_LOCAL_RULE (id, ruleType, countryProperty_id, errorMessage, canBeSkipped) VALUES (67, 'JAVAEXP', 233, 'oct.property.form.error.nationality', 0);
INSERT INTO OCT_LOCAL_RULE_PARAM (id, parameterType, value, rule_id) VALUES (67, 'JAVAEXP', 'bean.gp("c").equals("lu") && (bean.gp("country").equals("lu") || bean.gp("nationality").equals("lu"));', 67);

UPDATE OCT_LOCAL_RULE SET errorMessage='oct.property.form.error.nationality' WHERE id=36;
UPDATE OCT_LOCAL_RULE_PARAM SET value='bean.gp("c").equals("nl") && (bean.gp("country").equals("nl") || bean.gp("nationality").equals("nl"))' WHERE id=36;


UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=135;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=137;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=138;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=139;

INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (262, 1, 24, 9);
INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (263, 1, 24, 16);

INSERT INTO OCT_LOCAL_RULE (id, ruleType, countryProperty_id, errorMessage, canBeSkipped) VALUES (68, 'REGEXP', 263, NULL, 1);
INSERT INTO OCT_LOCAL_RULE_PARAM (id, parameterType, value, rule_id) VALUES (68, 'REGEXP', '[xyz][0-9]{7}[a-z]', 68);


DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=23;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=24;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=25;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=26;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=27;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=28;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=29;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=30;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=31;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=32;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=33;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=62;

DELETE FROM OCT_LOCAL_RULE WHERE id=23;
DELETE FROM OCT_LOCAL_RULE WHERE id=24;
DELETE FROM OCT_LOCAL_RULE WHERE id=25;
DELETE FROM OCT_LOCAL_RULE WHERE id=26;
DELETE FROM OCT_LOCAL_RULE WHERE id=27;
DELETE FROM OCT_LOCAL_RULE WHERE id=28;
DELETE FROM OCT_LOCAL_RULE WHERE id=29;
DELETE FROM OCT_LOCAL_RULE WHERE id=30;
DELETE FROM OCT_LOCAL_RULE WHERE id=31;
DELETE FROM OCT_LOCAL_RULE WHERE id=32;
DELETE FROM OCT_LOCAL_RULE WHERE id=33;
DELETE FROM OCT_LOCAL_RULE WHERE id=62;

UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=66;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=74;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=155;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=156;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=157;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=158;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=159;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=160;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=161;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=162;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=163;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=164;

INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (264, 1, 4, 10);


UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=154;


DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=51;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=52;

DELETE FROM OCT_LOCAL_RULE WHERE id=52;
DELETE FROM OCT_LOCAL_RULE WHERE id=51;

UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=133;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=134;

INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (265, 1, 18, 15);

INSERT INTO OCT_LOCAL_RULE (id, ruleType, countryProperty_id, errorMessage, canBeSkipped) VALUES (69, 'REGEXP', 265, NULL, 1);

INSERT INTO OCT_LOCAL_RULE_PARAM (id, parameterType, value, rule_id) VALUES (69, 'REGEXP', '[0-9]{13}', 69);


DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=50;
DELETE FROM OCT_LOCAL_RULE_PARAM WHERE id=61;

DELETE FROM OCT_LOCAL_RULE WHERE id=50;
DELETE FROM OCT_LOCAL_RULE WHERE id=61;

UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=144;
UPDATE OCT_COUNTRY_PROPERTY SET MARKEDASDELETED = 1  WHERE id=145;

INSERT INTO OCT_COUNTRY_PROPERTY (id, required, country_id, property_id) VALUES (266, 1, 27, 15);

INSERT INTO OCT_LOCAL_RULE (id, ruleType, countryProperty_id, errorMessage, canBeSkipped) VALUES (70, 'REGEXP', 266, 'oct.property.form.error.id.number.se', 1);

INSERT INTO OCT_LOCAL_RULE_PARAM (id, parameterType, value, rule_id) VALUES (70, 'REGEXP', '[0-9]{12}', 70);



CREATE UNIQUE INDEX OCT_PV_IDX2107 ON OCT_PROPERTY_VALUE (PROPERTY_ID, SIGNATURE_ID);

CREATE UNIQUE INDEX OCT_CP_IDX2603 ON OCT_COUNTRY_PROPERTY (COUNTRY_ID, PROPERTY_ID);

CREATE INDEX OCT_GR_IDX0604 ON OCT_GLOBAL_RULE (PROPERTY_ID);

CREATE INDEX OCT_GRP_IDX2206 ON OCT_GLOBAL_RULE_PARAM (RULE_ID);

CREATE INDEX OCT_LR_IDX0904 ON OCT_LOCAL_RULE (COUNTRYPROPERTY_ID);

CREATE INDEX OCT_LRP_IDX2811 ON OCT_LOCAL_RULE_PARAM (RULE_ID);

CREATE INDEX OCT_PROP_IDX2107 ON OCT_PROPERTY (GROUP_ID);

CREATE INDEX OCT_SIG_IDX2107 ON OCT_SIGNATURE (COUNTRYTOSIGNFOR_ID, DESCRIPTION_ID);

CREATE INDEX OCT_SYSP_IDX2107 ON OCT_SYSTEM_PREFS (DEFAULTDESCRIPTION_ID, DEFAULTLANGUAGE_ID);



SET SCAN ON;