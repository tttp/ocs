UPDATE OCT_LOCAL_RULE_PARAM SET value='([a-z]{2}[0-9]{6,8})|([0-9]{7}[a-z]{2})' WHERE  id=44;

UPDATE OCT_LOCAL_RULE_PARAM SET value='([a-z]{2}[0-9]{7})|([a-z][0-9]{6})|([0-9]{6}[a-z])' WHERE  id=45;

UPDATE OCT_LOCAL_RULE_PARAM SET value='[a-z0-9]*' WHERE  id=24;

UPDATE OCT_LOCAL_RULE_PARAM SET value='[a-z0-9]{7}|[0-9a-z]{12}' WHERE  id=22;