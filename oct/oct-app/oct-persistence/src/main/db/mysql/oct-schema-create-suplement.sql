UPDATE OCT_SYSTEM_PREFS 
SET
FILE_STORE='/ec/local/weblogic/app/eci_oct/',
PUBLICKEY='30820122300d06092a864886f70d01010105000382010f003082010a0282010100828077dc9cdc487ea15a33668dc6731650796407a824b2f477591be3bcf4a69a08b63d7aa7c144cce6b1cc8003f5358c53bb21ebbdfbaa6fa0ab3d547d824f940f7e79b37d8f04959b23910a689363c229df018e1daf653c12407667af62e0022eba6225e5858f098fd766f881cc19f6a04606f5d10654a5a2472ab7ac00f2b0d7e61585f3866870afdda1ea00f20395477c3e5cafcbf1e7cb31525ef4454e3ab107c48338a6c7f1597e9fb757174b7c7950038e2083174c72e585e04fb63847a88295303b0df9d6bbf35fb25f0e518f44ffbc0fa44e4fa7721e427be2b31cb5b8a9bb8f17986618ce99f199a5b360ca2027b56d7cd712e45d2db48d16f3d84d0203010001'
WHERE ID=1;

Insert into OCT_ACCOUNT (ID,PASSHASH,USERNAME) values (1,'8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','admin');




