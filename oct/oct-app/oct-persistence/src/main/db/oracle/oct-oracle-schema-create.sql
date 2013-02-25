--------------------------------------------------------
--  File created - Tuesday-December-13-2011   
--------------------------------------------------------

--------------------------------------------------------
--  DDL for Sequence OCT_ACTSEQ
--------------------------------------------------------

   CREATE SEQUENCE  "OCT_ACTSEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence OCT_CTSEQ
--------------------------------------------------------

   CREATE SEQUENCE  "OCT_CTSEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence OCT_INDSSEQ
--------------------------------------------------------

   CREATE SEQUENCE  "OCT_INDSSEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence OCT_PRVLSEQ
--------------------------------------------------------

   CREATE SEQUENCE  "OCT_PRVLSEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence OCT_SGNTSEQ
--------------------------------------------------------

   CREATE SEQUENCE  "OCT_SGNTSEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table OCT_ACCOUNT
--------------------------------------------------------

  CREATE TABLE "OCT_ACCOUNT" 
   (	"ID" NUMBER(19,0), 
	"PASSHASH" VARCHAR2(255 CHAR), 
	"USERNAME" VARCHAR2(255 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_SYSTEM_PREFS
--------------------------------------------------------

  CREATE TABLE "OCT_SYSTEM_PREFS" 
   (	"ID" NUMBER(19,0), 
	"INSERTDATE" TIMESTAMP (6) default sysdate, 
	"UPDATEDATE" TIMESTAMP (6) default sysdate, 
	"VERSION" NUMBER(10,0) default 1, 
	"CERT_CONTENT_TYPE" VARCHAR2(255 CHAR), 
	"CERT_FILE_NAME" VARCHAR2(255 CHAR), 
	"COLLECTING" NUMBER(3,0), 
	"COMMISSIONREGISTERURL" VARCHAR2(500 CHAR), 
	"ECIDATATIMESTAMP" TIMESTAMP (6), 
	"FILE_STORE" VARCHAR2(255 CHAR), 
	"PUBLICKEY" VARCHAR2(1024 CHAR), 
	"REGISTRATIONDATE" TIMESTAMP (6), 
	"REGISTRATIONNUMBER" VARCHAR2(64 CHAR), 
	"STATE" VARCHAR2(255 CHAR), 
	"DEFAULTDESCRIPTION_ID" NUMBER(19,0), 
	"DEFAULTLANGUAGE_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_CONTACT
--------------------------------------------------------

  CREATE TABLE "OCT_CONTACT" 
   (	"ID" NUMBER(19,0), 
	"INSERTDATE" TIMESTAMP (6) default sysdate, 
	"UPDATEDATE" TIMESTAMP (6) default sysdate, 
	"VERSION" NUMBER(10,0) default 1, 
	"EMAIL" VARCHAR2(2000 CHAR), 
	"NAME" VARCHAR2(2000 CHAR), 
	"ORGANIZERS" VARCHAR2(2000 CHAR), 
	"SYSTEM_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_COUNTRY
--------------------------------------------------------

  CREATE TABLE "OCT_COUNTRY" 
   (	"ID" NUMBER(19,0), 
	"CODE" VARCHAR2(255 CHAR), 
	"NAME" VARCHAR2(255 CHAR)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_LANG
--------------------------------------------------------

  CREATE TABLE "OCT_LANG" 
   (	"ID" NUMBER(19,0), 
	"CODE" VARCHAR2(3 CHAR), 
	"NAME" VARCHAR2(64 CHAR),
	"DISPLAY_ORDER" NUMBER(19,0)
   ) ;
   
--------------------------------------------------------
--  DDL for Table OCT_COUNTRY_LANG
--------------------------------------------------------

  CREATE TABLE "OCT_COUNTRY_LANG" 
   (	"COUNTRY_ID" NUMBER(19,0), 
	"LANGUAGE_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_PROPERTY
--------------------------------------------------------

  CREATE TABLE "OCT_PROPERTY" 
   (	"ID" NUMBER(19,0), 
	"NAME" VARCHAR2(64 CHAR), 
	"PRIORITY" NUMBER(10,0), 
	"TYPE" VARCHAR2(255 CHAR), 
	"GROUP_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_COUNTRY_PROPERTY
--------------------------------------------------------

  CREATE TABLE "OCT_COUNTRY_PROPERTY" 
   (	"ID" NUMBER(19,0), 
	"REQUIRED" NUMBER(3,0), 
	"COUNTRY_ID" NUMBER(19,0), 
	"PROPERTY_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_PROPERTY_GROUP
--------------------------------------------------------

  CREATE TABLE "OCT_PROPERTY_GROUP" 
   (	"ID" NUMBER(19,0), 
	"MULTICHOICE" NUMBER(3,0), 
	"NAME" VARCHAR2(64 CHAR), 
	"PRIORITY" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_INITIATIVE_DESC
--------------------------------------------------------

  CREATE TABLE "OCT_INITIATIVE_DESC" 
   (	"ID" NUMBER(19,0), 
	"INSERTDATE" TIMESTAMP (6) default sysdate, 
	"UPDATEDATE" TIMESTAMP (6) default sysdate, 
	"VERSION" NUMBER(10,0) default 1, 
	"OBJECTIVES" VARCHAR2(4000 CHAR), 
	"SUBJECTMATTER" VARCHAR2(2000 CHAR), 
	"TITLE" VARCHAR2(1000 CHAR), 
	"URL" VARCHAR2(100 CHAR), 
	"LANGUAGE_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_SIGNATURE
--------------------------------------------------------

  CREATE TABLE "OCT_SIGNATURE" 
   (	"ID" NUMBER(19,0), 
	"INSERTDATE" TIMESTAMP (6) default sysdate, 
	"UPDATEDATE" TIMESTAMP (6) default sysdate, 
	"VERSION" NUMBER(10,0) default 1, 
	"DATEOFSIGNATURE" TIMESTAMP (6), 
	"FINGERPRINT" VARCHAR2(2048 CHAR), 
	"UUID" VARCHAR2(255 CHAR), 
	"COUNTRYTOSIGNFOR_ID" NUMBER(19,0), 
	"DESCRIPTION_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_PROPERTY_VALUE
--------------------------------------------------------

  CREATE TABLE "OCT_PROPERTY_VALUE" 
   (	"ID" NUMBER(19,0), 
	"VALUE" VARCHAR2(2048 CHAR), 
	"PROPERTY_ID" NUMBER(19,0), 
	"SIGNATURE_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_RULE
--------------------------------------------------------

  CREATE TABLE "OCT_RULE" 
   (	"ID" NUMBER(19,0), 
	"RULETYPE" VARCHAR2(255 CHAR), 
	"PROPERTY_ID" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table OCT_RULE_PARAM
--------------------------------------------------------

  CREATE TABLE "OCT_RULE_PARAM" 
   (	"ID" NUMBER(19,0), 
	"PARAMETERTYPE" VARCHAR2(255 CHAR), 
	"VALUE" VARCHAR2(255 CHAR), 
	"RULE_ID" NUMBER(19,0)
   ) ;


---------------------------------------------------
--   END DATA FOR TABLE OCT_ACCOUNT
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE OCT_COUNTRY_PROPERTY
--   FILTER = none used
---------------------------------------------------
REM INSERTING into OCT_COUNTRY_PROPERTY
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (1,1,1,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (3,1,1,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (4,1,1,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (5,1,1,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (6,1,1,19);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (7,1,6,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (9,1,6,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (10,1,6,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (11,1,6,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (12,1,6,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (13,1,6,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (14,1,6,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (15,1,6,16);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (16,1,6,17);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (17,1,6,21);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (19,1,8,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (21,1,8,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (22,1,8,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (23,1,8,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (24,1,8,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (25,1,8,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (26,1,8,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (27,1,8,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (28,1,5,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (30,1,5,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (31,1,5,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (32,1,5,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (33,1,5,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (34,1,5,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (35,1,10,2);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (36,1,10,15);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (37,1,11,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (38,1,11,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (39,1,19,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (40,1,19,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (41,1,20,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (43,1,20,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (44,1,20,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (45,1,20,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (46,1,20,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (47,1,20,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (48,1,21,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (50,1,21,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (51,1,21,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (52,1,21,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (53,1,21,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (54,1,21,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (55,1,22,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (56,1,22,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (58,1,4,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (60,1,4,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (61,1,4,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (62,1,4,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (63,1,4,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (64,1,4,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (65,1,4,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (66,1,4,14);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (67,1,2,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (69,1,2,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (70,1,2,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (71,1,2,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (72,1,2,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (73,1,2,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (74,1,4,18);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (76,1,23,1);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (77,1,23,2);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (78,1,23,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (79,1,23,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (80,1,23,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (81,1,23,17);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (82,1,25,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (83,1,25,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (84,1,25,15);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (85,1,26,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (87,0,26,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (88,1,26,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (89,1,26,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (92,1,28,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (94,1,28,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (95,1,28,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (96,1,28,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (97,1,28,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (98,1,28,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (99,1,28,11);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (100,1,28,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (101,1,28,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (102,1,9,1);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (103,1,9,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (104,1,9,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (105,1,9,16);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (106,1,12,15);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (107,1,13,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (108,1,13,19);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (109,1,14,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (110,1,14,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (111,1,15,1);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (112,1,15,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (114,1,15,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (115,1,15,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (116,1,15,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (117,1,15,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (118,1,15,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (119,1,16,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (120,1,16,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (121,1,16,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (122,1,16,22);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (123,1,17,1);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (124,1,17,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (126,1,17,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (127,1,17,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (128,1,17,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (129,1,17,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (130,1,17,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (131,1,18,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (132,1,18,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (133,1,18,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (134,1,18,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (135,1,24,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (137,1,24,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (138,1,24,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (139,1,24,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (140,1,24,12);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (141,1,24,13);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (142,1,27,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (143,1,27,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (144,1,27,23);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (145,1,27,24);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (146,1,3,3);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (148,1,3,5);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (149,1,3,6);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (150,1,3,7);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (151,1,3,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (153,1,26,9);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (154,1,26,10);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (155,1,4,26);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (156,1,4,27);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (157,1,4,28);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (158,1,4,29);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (159,1,4,30);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (160,1,4,31);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (161,1,4,32);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (162,1,4,33);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (163,1,4,34);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (164,1,4,35);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (165,1,1,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (166,1,2,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (167,1,3,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (168,1,4,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (169,1,5,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (170,1,6,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (172,1,8,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (173,1,9,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (174,1,10,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (175,1,11,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (176,1,12,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (177,1,13,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (178,1,14,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (179,1,15,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (180,1,16,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (181,1,17,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (182,1,18,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (183,1,19,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (184,1,20,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (185,1,21,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (186,1,22,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (187,1,23,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (188,1,24,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (189,1,25,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (190,1,26,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (191,1,27,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (192,1,28,37);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (193,1,1,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (194,1,2,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (195,1,3,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (196,1,4,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (197,1,5,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (198,1,6,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (200,1,8,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (201,1,9,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (202,1,10,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (203,1,11,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (204,1,12,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (205,1,13,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (206,1,14,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (207,1,15,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (208,1,16,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (209,1,17,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (210,1,18,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (211,1,19,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (212,1,20,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (213,1,21,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (214,1,22,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (215,1,23,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (216,1,24,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (217,1,25,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (218,1,26,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (219,1,27,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (220,1,28,38);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (221,1,1,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (222,1,2,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (223,1,3,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (224,1,4,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (225,1,5,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (226,1,6,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (228,1,8,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (229,1,9,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (230,1,10,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (231,1,11,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (232,1,12,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (233,1,13,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (234,1,14,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (235,1,15,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (236,1,16,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (237,1,17,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (238,1,18,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (239,1,19,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (240,1,20,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (241,1,21,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (242,1,22,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (243,1,23,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (244,1,24,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (245,1,25,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (246,1,26,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (247,1,27,39);
Insert into OCT_COUNTRY_PROPERTY (ID,REQUIRED,COUNTRY_ID,PROPERTY_ID) values (248,1,28,39);

---------------------------------------------------
--   END DATA FOR TABLE OCT_COUNTRY_PROPERTY
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE OCT_RULE_PARAM
--   FILTER = none used
---------------------------------------------------
REM INSERTING into OCT_RULE_PARAM
Insert into OCT_RULE_PARAM (ID,PARAMETERTYPE,VALUE,RULE_ID) values (1,'MIN','16y',1);

---------------------------------------------------
--   END DATA FOR TABLE OCT_RULE_PARAM
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE OCT_PROPERTY_GROUP
--   FILTER = none used
---------------------------------------------------
REM INSERTING into OCT_PROPERTY_GROUP
Insert into OCT_PROPERTY_GROUP (ID,MULTICHOICE,NAME,PRIORITY) values (1,0,'oct.group.general',2);
Insert into OCT_PROPERTY_GROUP (ID,MULTICHOICE,NAME,PRIORITY) values (2,0,'oct.group.address',3);
Insert into OCT_PROPERTY_GROUP (ID,MULTICHOICE,NAME,PRIORITY) values (3,1,'oct.group.id',1);

---------------------------------------------------
--   END DATA FOR TABLE OCT_PROPERTY_GROUP
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE OCT_COUNTRY_LANG
--   FILTER = none used
---------------------------------------------------
REM INSERTING into OCT_COUNTRY_LANG
Insert into OCT_COUNTRY_LANG (COUNTRY_ID,LANGUAGE_ID) values (1,1);
Insert into OCT_COUNTRY_LANG (COUNTRY_ID,LANGUAGE_ID) values (2,2);
Insert into OCT_COUNTRY_LANG (COUNTRY_ID,LANGUAGE_ID) values (3,3);
Insert into OCT_COUNTRY_LANG (COUNTRY_ID,LANGUAGE_ID) values (4,4);
Insert into OCT_COUNTRY_LANG (COUNTRY_ID,LANGUAGE_ID) values (5,4);
Insert into OCT_COUNTRY_LANG (COUNTRY_ID,LANGUAGE_ID) values (5,5);
Insert into OCT_COUNTRY_LANG (COUNTRY_ID,LANGUAGE_ID) values (6,6);

---------------------------------------------------
--   END DATA FOR TABLE OCT_COUNTRY_LANG
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE OCT_RULE
--   FILTER = none used
---------------------------------------------------
REM INSERTING into OCT_RULE
Insert into OCT_RULE (ID,RULETYPE,PROPERTY_ID) values (1,'RANGE',9);

---------------------------------------------------
--   END DATA FOR TABLE OCT_RULE
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE OCT_COUNTRY
--   FILTER = none used
---------------------------------------------------
REM INSERTING into OCT_COUNTRY
Insert into OCT_COUNTRY (ID,CODE,NAME) values (1,'pl','oct.country.pl');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (2,'de','oct.country.de');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (3,'uk','oct.country.uk');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (4,'fr','oct.country.fr');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (5,'be','oct.country.be');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (6,'ro','oct.country.ro');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (8,'at','oct.country.at');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (9,'lv','oct.country.lv');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (10,'bg','oct.country.bg');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (11,'cy','oct.country.cy');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (12,'lt','oct.country.lt');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (13,'lu','oct.country.lu');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (14,'mt','oct.country.mt');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (15,'nl','oct.country.nl');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (16,'pt','oct.country.pt');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (17,'sk','oct.country.sk');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (18,'si','oct.country.si');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (19,'cz','oct.country.cz');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (20,'dk','oct.country.dk');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (21,'ee','oct.country.ee');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (22,'fi','oct.country.fi');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (23,'el','oct.country.el');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (24,'es','oct.country.es');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (25,'hu','oct.country.hu');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (26,'ie','oct.country.ie');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (27,'se','oct.country.se');
Insert into OCT_COUNTRY (ID,CODE,NAME) values (28,'it','oct.country.it');

---------------------------------------------------
--   END DATA FOR TABLE OCT_COUNTRY
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE OCT_PROPERTY
--   FILTER = none used
---------------------------------------------------
REM INSERTING into OCT_PROPERTY
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (1,'oct.property.name.at.birth',96,'ALPHANUMERIC',1);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (2,'oct.property.fathers.name',97,'ALPHANUMERIC',1);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (3,'oct.property.address',89,'LARGETEXT',2);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (5,'oct.property.postal.code',88,'ALPHANUMERIC',2);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (6,'oct.property.city',87,'ALPHANUMERIC',2);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (7,'oct.property.country',86,'COUNTRY',2);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (8,'oct.property.state',0,'ALPHANUMERIC',2);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (9,'oct.property.date.of.birth',94,'DATE',1);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (10,'oct.property.place.of.birth',93,'ALPHANUMERIC',1);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (11,'oct.property.issuing.authority',0,'ALPHANUMERIC',1);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (12,'oct.property.passport',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (13,'oct.property.id.card',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (14,'oct.property.residence.permit',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (15,'oct.property.personal.number',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (16,'oct.property.personal.id',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (17,'oct.property.permanent.residence',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (18,'oct.property.driving.license',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (19,'oct.property.national.id.number',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (20,'oct.property.social.security.id',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (21,'oct.property.registration.certificate',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (22,'oct.property.citizens.card',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (23,'oct.property.personal.no.in.passport',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (24,'oct.property.personal.no.in.id.card',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (26,'oct.property.other.1',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (27,'oct.property.other.2',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (28,'oct.property.other.3',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (29,'oct.property.other.4',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (30,'oct.property.other.5',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (31,'oct.property.other.6',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (32,'oct.property.other.7',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (33,'oct.property.other.8',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (34,'oct.property.other.9',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (35,'oct.property.other.10',0,'ALPHANUMERIC',3);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (36,'oct.property.date.of.birth.at',94,'DATE',1);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (37,'oct.property.firstname',99,'ALPHANUMERIC',1);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (38,'oct.property.lastname',98,'ALPHANUMERIC',1);
Insert into OCT_PROPERTY (ID,NAME,PRIORITY,TYPE,GROUP_ID) values (39,'oct.property.nationality',92,'NATIONALITY',1);

---------------------------------------------------
--   END DATA FOR TABLE OCT_PROPERTY
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE OCT_LANG
--   FILTER = none used
---------------------------------------------------
REM INSERTING into OCT_LANG
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (1,'cs','oct.lang.Czech',2);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (2,'da','oct.lang.Danish',3);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (3,'de','oct.lang.German',4);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (4,'et','oct.lang.Estonian',5);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (5,'el','oct.lang.Greek',6);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (6,'en','oct.lang.English',7);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (7,'es','oct.lang.Spanish',8);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (8,'fr','oct.lang.French',9);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (9,'it','oct.lang.Italian',11);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (10,'lv','oct.lang.Latvian',12);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (11,'lt','oct.lang.Lithuanian',13);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (12,'ga','oct.lang.Gaelic',10);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (13,'hu','oct.lang.Hungarian',14);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (14,'mt','oct.lang.Maltese',15);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (15,'nl','oct.lang.Dutch',16);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (16,'pl','oct.lang.Polish',17);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (17,'pt','oct.lang.Portuguese',18);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (18,'ro','oct.lang.Romanian',19);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (19,'sk','oct.lang.Slovak',20);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (20,'sl','oct.lang.Slovenian',21);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (21,'fi','oct.lang.Finnish',22);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (22,'sv','oct.lang.Swedish',23);
Insert into OCT_LANG (ID,CODE,NAME,DISPLAY_ORDER) values (23,'bg','oct.lang.Bulgarian',1);

---------------------------------------------------
--   END DATA FOR TABLE OCT_LANG
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE OCT_SYSTEM_PREFS
--   FILTER = none used
---------------------------------------------------
REM INSERTING into OCT_SYSTEM_PREFS
Insert into OCT_SYSTEM_PREFS (ID,CERT_CONTENT_TYPE,CERT_FILE_NAME,COLLECTING,COMMISSIONREGISTERURL,ECIDATATIMESTAMP,FILE_STORE,PUBLICKEY,REGISTRATIONDATE,REGISTRATIONNUMBER,STATE,DEFAULTDESCRIPTION_ID,DEFAULTLANGUAGE_ID) values (1,null,null,0,null,null,null,null,null,null,'DEPLOYED',null,6);

---------------------------------------------------
--   END DATA FOR TABLE OCT_SYSTEM_PREFS
---------------------------------------------------
--------------------------------------------------------
--  Constraints for Table OCT_ACCOUNT
--------------------------------------------------------

  ALTER TABLE "OCT_ACCOUNT" ADD UNIQUE ("USERNAME") ENABLE;
  ALTER TABLE "OCT_ACCOUNT" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_ACCOUNT" MODIFY ("USERNAME" NOT NULL ENABLE);
  ALTER TABLE "OCT_ACCOUNT" MODIFY ("PASSHASH" NOT NULL ENABLE);
  ALTER TABLE "OCT_ACCOUNT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_CONTACT
--------------------------------------------------------

  ALTER TABLE "OCT_CONTACT" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_CONTACT" MODIFY ("VERSION" NOT NULL ENABLE);
  ALTER TABLE "OCT_CONTACT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_COUNTRY
--------------------------------------------------------

  ALTER TABLE "OCT_COUNTRY" ADD UNIQUE ("NAME") ENABLE;
  ALTER TABLE "OCT_COUNTRY" ADD UNIQUE ("CODE") ENABLE;
  ALTER TABLE "OCT_COUNTRY" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_COUNTRY" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "OCT_COUNTRY" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "OCT_COUNTRY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_COUNTRY_LANG
--------------------------------------------------------

  ALTER TABLE "OCT_COUNTRY_LANG" ADD PRIMARY KEY ("COUNTRY_ID", "LANGUAGE_ID") ENABLE;
  ALTER TABLE "OCT_COUNTRY_LANG" MODIFY ("LANGUAGE_ID" NOT NULL ENABLE);
  ALTER TABLE "OCT_COUNTRY_LANG" MODIFY ("COUNTRY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_COUNTRY_PROPERTY
--------------------------------------------------------

  ALTER TABLE "OCT_COUNTRY_PROPERTY" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_COUNTRY_PROPERTY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_INITIATIVE_DESC
--------------------------------------------------------

  ALTER TABLE "OCT_INITIATIVE_DESC" ADD UNIQUE ("LANGUAGE_ID") ENABLE;
  ALTER TABLE "OCT_INITIATIVE_DESC" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_INITIATIVE_DESC" MODIFY ("LANGUAGE_ID" NOT NULL ENABLE);
  ALTER TABLE "OCT_INITIATIVE_DESC" MODIFY ("TITLE" NOT NULL ENABLE);
  ALTER TABLE "OCT_INITIATIVE_DESC" MODIFY ("VERSION" NOT NULL ENABLE);
  ALTER TABLE "OCT_INITIATIVE_DESC" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_LANG
--------------------------------------------------------

  ALTER TABLE "OCT_LANG" ADD UNIQUE ("NAME") ENABLE;
  ALTER TABLE "OCT_LANG" ADD UNIQUE ("CODE") ENABLE;
  ALTER TABLE "OCT_LANG" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_LANG" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "OCT_LANG" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "OCT_LANG" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_PROPERTY
--------------------------------------------------------

  ALTER TABLE "OCT_PROPERTY" ADD UNIQUE ("NAME") ENABLE;
  ALTER TABLE "OCT_PROPERTY" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_PROPERTY" ADD UNIQUE ("PRIORITY") ENABLE;
  ALTER TABLE "OCT_PROPERTY" MODIFY ("PRIORITY" NOT NULL ENABLE);
  ALTER TABLE "OCT_PROPERTY" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "OCT_PROPERTY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_PROPERTY_GROUP
--------------------------------------------------------

  ALTER TABLE "OCT_PROPERTY_GROUP" ADD UNIQUE ("NAME") ENABLE;
  ALTER TABLE "OCT_PROPERTY_GROUP" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_PROPERTY_GROUP" MODIFY ("PRIORITY" NOT NULL ENABLE);
  ALTER TABLE "OCT_PROPERTY_GROUP" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "OCT_PROPERTY_GROUP" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_PROPERTY_VALUE
--------------------------------------------------------

  ALTER TABLE "OCT_PROPERTY_VALUE" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_PROPERTY_VALUE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_RULE
--------------------------------------------------------

  ALTER TABLE "OCT_RULE" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_RULE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_RULE_PARAM
--------------------------------------------------------

  ALTER TABLE "OCT_RULE_PARAM" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_RULE_PARAM" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_SIGNATURE
--------------------------------------------------------

  ALTER TABLE "OCT_SIGNATURE" ADD UNIQUE ("FINGERPRINT") ENABLE;
  ALTER TABLE "OCT_SIGNATURE" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_SIGNATURE" MODIFY ("DESCRIPTION_ID" NOT NULL ENABLE);
  ALTER TABLE "OCT_SIGNATURE" MODIFY ("COUNTRYTOSIGNFOR_ID" NOT NULL ENABLE);
  ALTER TABLE "OCT_SIGNATURE" MODIFY ("UUID" NOT NULL ENABLE);
  ALTER TABLE "OCT_SIGNATURE" MODIFY ("FINGERPRINT" NOT NULL ENABLE);
  ALTER TABLE "OCT_SIGNATURE" MODIFY ("DATEOFSIGNATURE" NOT NULL ENABLE);
  ALTER TABLE "OCT_SIGNATURE" MODIFY ("VERSION" NOT NULL ENABLE);
  ALTER TABLE "OCT_SIGNATURE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OCT_SYSTEM_PREFS
--------------------------------------------------------

  ALTER TABLE "OCT_SYSTEM_PREFS" ADD UNIQUE ("REGISTRATIONNUMBER") ENABLE;
  ALTER TABLE "OCT_SYSTEM_PREFS" ADD UNIQUE ("REGISTRATIONDATE") ENABLE;
  ALTER TABLE "OCT_SYSTEM_PREFS" ADD UNIQUE ("CERT_FILE_NAME") ENABLE;
  ALTER TABLE "OCT_SYSTEM_PREFS" ADD UNIQUE ("CERT_CONTENT_TYPE") ENABLE;
  ALTER TABLE "OCT_SYSTEM_PREFS" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "OCT_SYSTEM_PREFS" MODIFY ("VERSION" NOT NULL ENABLE);
  ALTER TABLE "OCT_SYSTEM_PREFS" MODIFY ("ID" NOT NULL ENABLE);

--------------------------------------------------------
--  Ref Constraints for Table OCT_CONTACT
--------------------------------------------------------

  ALTER TABLE "OCT_CONTACT" ADD CONSTRAINT "FK785287C1AAA9D6D7" FOREIGN KEY ("SYSTEM_ID")
	  REFERENCES "OCT_SYSTEM_PREFS" ("ID") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table OCT_COUNTRY_LANG
--------------------------------------------------------

  ALTER TABLE "OCT_COUNTRY_LANG" ADD CONSTRAINT "FKA402F1F6513A6710" FOREIGN KEY ("LANGUAGE_ID")
	  REFERENCES "OCT_LANG" ("ID") ENABLE;
  ALTER TABLE "OCT_COUNTRY_LANG" ADD CONSTRAINT "FKA402F1F6C25D8AE4" FOREIGN KEY ("COUNTRY_ID")
	  REFERENCES "OCT_COUNTRY" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table OCT_COUNTRY_PROPERTY
--------------------------------------------------------

  ALTER TABLE "OCT_COUNTRY_PROPERTY" ADD CONSTRAINT "FK49CFF1DD86EFF898" FOREIGN KEY ("PROPERTY_ID")
	  REFERENCES "OCT_PROPERTY" ("ID") ENABLE;
  ALTER TABLE "OCT_COUNTRY_PROPERTY" ADD CONSTRAINT "FK49CFF1DDC25D8AE4" FOREIGN KEY ("COUNTRY_ID")
	  REFERENCES "OCT_COUNTRY" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table OCT_INITIATIVE_DESC
--------------------------------------------------------

  ALTER TABLE "OCT_INITIATIVE_DESC" ADD CONSTRAINT "FK136443A5513A6710" FOREIGN KEY ("LANGUAGE_ID")
	  REFERENCES "OCT_LANG" ("ID") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table OCT_PROPERTY
--------------------------------------------------------

  ALTER TABLE "OCT_PROPERTY" ADD CONSTRAINT "FK789EEE34CBC45847" FOREIGN KEY ("GROUP_ID")
	  REFERENCES "OCT_PROPERTY_GROUP" ("ID") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table OCT_PROPERTY_VALUE
--------------------------------------------------------

  ALTER TABLE "OCT_PROPERTY_VALUE" ADD CONSTRAINT "FK7AEF73861BC05E92" FOREIGN KEY ("PROPERTY_ID")
	  REFERENCES "OCT_COUNTRY_PROPERTY" ("ID") ENABLE;
  ALTER TABLE "OCT_PROPERTY_VALUE" ADD CONSTRAINT "FK7AEF73861D72D746" FOREIGN KEY ("SIGNATURE_ID")
	  REFERENCES "OCT_SIGNATURE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table OCT_RULE
--------------------------------------------------------

  ALTER TABLE "OCT_RULE" ADD CONSTRAINT "FK7B76B6DB86EFF898" FOREIGN KEY ("PROPERTY_ID")
	  REFERENCES "OCT_PROPERTY" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table OCT_RULE_PARAM
--------------------------------------------------------

  ALTER TABLE "OCT_RULE_PARAM" ADD CONSTRAINT "FK20AD6A09D523D828" FOREIGN KEY ("RULE_ID")
	  REFERENCES "OCT_RULE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table OCT_SIGNATURE
--------------------------------------------------------

  ALTER TABLE "OCT_SIGNATURE" ADD CONSTRAINT "FK64E94196933581F" FOREIGN KEY ("COUNTRYTOSIGNFOR_ID")
	  REFERENCES "OCT_COUNTRY" ("ID") ENABLE;
  ALTER TABLE "OCT_SIGNATURE" ADD CONSTRAINT "FK64E94198EFFF971" FOREIGN KEY ("DESCRIPTION_ID")
	  REFERENCES "OCT_INITIATIVE_DESC" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table OCT_SYSTEM_PREFS
--------------------------------------------------------

  ALTER TABLE "OCT_SYSTEM_PREFS" ADD CONSTRAINT "FK6BCFAD5F2F9FCE2F" FOREIGN KEY ("DEFAULTLANGUAGE_ID")
	  REFERENCES "OCT_LANG" ("ID") ENABLE;
  ALTER TABLE "OCT_SYSTEM_PREFS" ADD CONSTRAINT "FK6BCFAD5F95B49F2" FOREIGN KEY ("DEFAULTDESCRIPTION_ID")
	  REFERENCES "OCT_INITIATIVE_DESC" ("ID") ENABLE;
