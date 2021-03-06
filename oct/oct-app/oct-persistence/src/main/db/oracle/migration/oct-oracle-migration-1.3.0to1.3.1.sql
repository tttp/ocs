SET SCAN OFF;


DROP TABLE "OCT_GLOBAL_RULE" cascade constraints;
DROP TABLE "OCT_GLOBAL_RULE_PARAM" cascade constraints;
DROP TABLE "OCT_LOCAL_RULE" cascade constraints;
DROP TABLE "OCT_LOCAL_RULE_PARAM" cascade constraints;

  CREATE TABLE "OCT_GLOBAL_RULE" 
   (	"ID" NUMBER(19,0), 
	"RULETYPE" VARCHAR2(255 CHAR), 
	"PROPERTY_ID" NUMBER(19,0)
   ) ;

  CREATE TABLE "OCT_GLOBAL_RULE_PARAM" 
   (	"ID" NUMBER(19,0), 
	"PARAMETERTYPE" VARCHAR2(255 CHAR), 
	"VALUE" VARCHAR2(4000 CHAR), 
	"RULE_ID" NUMBER(19,0)
   ) ;

  CREATE TABLE "OCT_LOCAL_RULE" 
   (	"ID" NUMBER(19,0), 
	"RULETYPE" VARCHAR2(255 CHAR), 
	"COUNTRYPROPERTY_ID" NUMBER(19,0)
   ) ;
   
  CREATE TABLE "OCT_LOCAL_RULE_PARAM" 
   (	"ID" NUMBER(19,0), 
	"PARAMETERTYPE" VARCHAR2(255 CHAR), 
	"VALUE" VARCHAR2(4000 CHAR), 
	"RULE_ID" NUMBER(19,0)
   ) ;


Insert into OCT_GLOBAL_RULE_PARAM (ID,PARAMETERTYPE,VALUE,RULE_ID) values (1,'MIN','16y',1);
Insert into OCT_GLOBAL_RULE_PARAM (ID,PARAMETERTYPE,VALUE,RULE_ID) values 	(2, 'JAVAEXP', 
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


INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (2, 'REGEXP', '[a-z][0-9]{7}', 2);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (3, 'REGEXP', '[0-9]{7}|[0-9]{8}', 3);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (5, 'REGEXP', '([0-9]{6}[a-z]{2})|([a-z]{2}[a-z][0-9]{6})|([a-z]{2}[a-z]{2}[0-9]{6})|([a-z]{2}[a-z]{3}[0-9]{6})|([a-z]{2}[0-9]{6})', 5);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (6, 'REGEXP', '([a-z]{2}[0-9]{6})|([a-z]{2}[0-9]{7})', 6);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (7, 'REGEXP', '[0-9][0-9]{6}[0-9]{4}', 7);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (8, 'REGEXP', '[0-9]{11}', 8);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (9, 'REGEXP', '[0-9]{11}|[0-9]{13}', 9);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (12, 'REGEXP', '[0-9]{11}', 12);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (14, 'REGEXP', '[0-9]{13}', 14);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (15, 'REGEXP', '[0-9]{13}', 15);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (16, 'REGEXP', '[0-9]{13}', 16);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (17, 'REGEXP', '[a-z]{2}[0-9]{6}', 17);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (21, 'REGEXP', '([0-9]){2}([a-z]){2}([0-9]){4}[0-9]', 21);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (22, 'REGEXP', '[0-9]{7}|[0-9]{12}', 22);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (23, 'REGEXP', '[0-9]{10}', 23);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (24, 'REGEXP', '([a-z]){2}([0-9]){4}[0-9]', 24);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (25, 'REGEXP', '([0-9]{4})|([0-9]{3}\(((?=.)(?i)M*(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX]))\))', 25);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (26, 'REGEXP', '[0-9]{2}[0-9]{2}', 26);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (27, 'REGEXP', '([0-9]{5})|([0-9]{7})', 27);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (28, 'REGEXP', '[0-9]{9}', 28);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (29, 'REGEXP', '[0-9]{6}', 29);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (30, 'REGEXP', '[a-z0-9]*', 30);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (31, 'REGEXP', '[a-z0-9]*', 31);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (32, 'REGEXP', '[0-9]{6}', 32);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (33, 'REGEXP', '([a-z0-9]{10,17})|[0-9]{10}', 33);

INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (34, 'JAVAEXP', 'bean.gp("c").equals("uk") && bean.gp("country").equals("uk")', 34);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (35, 'JAVAEXP', 'bean.gp("c").equals("ie") && bean.gp("country").equals("ie")', 35);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (36, 'JAVAEXP', 'bean.gp("c").equals("nl") && bean.gp("country").equals("nl")', 36);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (37, 'JAVAEXP', 'bean.gp("c").equals("ee") && (bean.gp("country").equals("ee") || bean.gp("nationality").equals("ee"))', 37);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (38, 'JAVAEXP', 'bean.gp("c").equals("fi") && (bean.gp("country").equals("fi") || bean.gp("nationality").equals("fi"))', 38);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (39, 'JAVAEXP', 'bean.gp("c").equals("sk") && (bean.gp("country").equals("sk") || bean.gp("nationality").equals("sk"))', 39);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (40, 'JAVAEXP', 'bean.gp("c").equals("be") && (bean.gp("country").equals("be") || bean.gp("nationality").equals("be"))', 40);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (41, 'JAVAEXP', 'bean.gp("c").equals("dk") && (bean.gp("country").equals("dk") || bean.gp("nationality").equals("dk"))', 41);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (42, 'JAVAEXP', 'bean.gp("c").equals("de") && (bean.gp("country").equals("de") || bean.gp("nationality").equals("de"))', 42);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (43, 'REGEXP', '[0-9]{1,10}', 43);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (44, 'REGEXP', '([a-z]{2}[0-9]{7})|([0-9]{7}[a-z]{2})', 44);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (45, 'REGEXP', '[a-z]{1,2}[0-9]{1,7}', 45);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (46, 'REGEXP', '[0-9]{6}[0-9]{5}', 46);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (47, 'REGEXP', '[a-z]{1}[0-9]{6}', 47);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (48, 'REGEXP', '[0-9]{8}[a-z]{2}[0-9]{1}', 48);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (49, 'REGEXP', '[0-9]{8}', 49);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (50, 'REGEXP', '[0-9]{12}', 50);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (51, 'REGEXP', '[0-9]{9}', 51);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (52, 'REGEXP', '([a-z][0-9]{8})|([a-z]{2}[0-9]{7})', 52);

INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (53, 'REGEXP', '[\p{InGreek}a-z]{1,2}[0-9]{6}', 53);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (54, 'REGEXP', '[a-z]{2}[0-9]{7}', 54);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (55, 'REGEXP', '[0-9]{1,6}', 55);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (56, 'REGEXP', '[0-9]{10}', 56);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (57, 'REGEXP', '([bcej][0-9]{6})|(k[0-9]{8})|([ds]p[0-9]{7})', 57);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (58, 'REGEXP', '[0-9]{7,8}', 58);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (59, 'REGEXP', '([0-9]{9})|([0-9]{6}[a-z]{2}[0-9]{2})|([0-9]{6}[a-z]{2})|([a-z]{2}[0-9]{6})', 59);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (60, 'REGEXP', '[0-9]{8}[a-z]', 60);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (61, 'REGEXP', '[0-9]{12}', 61);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (62, 'REGEXP', '[a-z0-9]*', 62);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (63, 'REGEXP', '[a-z0-9]*', 63);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (64, 'REGEXP', '[a-z0-9]*', 64);
INSERT INTO OCT_LOCAL_RULE_PARAM (ID, PARAMETERTYPE, VALUE, RULE_ID) VALUES  (65, 'REGEXP', '[a-z0-9]*', 65);


Insert into OCT_GLOBAL_RULE (ID,RULETYPE,PROPERTY_ID) values (1,'RANGE',9);
Insert into OCT_GLOBAL_RULE (ID,RULETYPE,PROPERTY_ID) values (2, 'JAVAEXP', 5);


INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(2, 'REGEXP', 26);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(3, 'REGEXP', 27);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(5, 'REGEXP', 83);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(6, 'REGEXP', 82);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(7, 'REGEXP', 84);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(8, 'REGEXP', 106);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(9, 'REGEXP', 108);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(12, 'REGEXP', 6);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(14, 'REGEXP', 15);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(15, 'REGEXP', 16);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(16, 'REGEXP', 17);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(17, 'REGEXP', 14);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(21, 'REGEXP', 64);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(22, 'REGEXP', 65);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(23, 'REGEXP', 66);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(24, 'REGEXP', 74);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(25, 'REGEXP', 155);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(26, 'REGEXP', 156);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(27, 'REGEXP', 157);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(28, 'REGEXP', 158);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(29, 'REGEXP', 159);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(30, 'REGEXP', 160);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(31, 'REGEXP', 161);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(32, 'REGEXP', 162);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(33, 'REGEXP', 164);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(34, 'JAVAEXP', 150);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(35, 'JAVAEXP', 89);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(36, 'JAVAEXP', 116);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES(37, 'JAVAEXP', 241);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (38, 'JAVAEXP', 242);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (39, 'JAVAEXP', 237);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (40, 'JAVAEXP', 225);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (41, 'JAVAEXP', 240);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (42, 'JAVAEXP', 222);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (43, 'REGEXP', 38);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (44, 'REGEXP', 101);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (45, 'REGEXP', 100);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (46, 'REGEXP', 105);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (47, 'REGEXP', 120);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (48, 'REGEXP', 122);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (49, 'REGEXP', 121);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (50, 'REGEXP', 144);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (51, 'REGEXP', 134);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (52, 'REGEXP', 133);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (53, 'REGEXP', 80);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (54, 'REGEXP', 79);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (55, 'REGEXP', 81);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (56, 'REGEXP', 36);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (57, 'REGEXP', 37);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (58, 'REGEXP', 39);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (59, 'REGEXP', 40);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (60, 'REGEXP', 141);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (61, 'REGEXP', 145);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (62, 'REGEXP', 163);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (63, 'REGEXP', 110);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (64, 'REGEXP', 13);
INSERT INTO OCT_LOCAL_RULE (ID, RULETYPE, COUNTRYPROPERTY_ID) VALUES (65, 'REGEXP', 140);

   
SET SCAN ON;