# ====================================================
# This script creates a Weblogic domain configuration
# with all JEE items required for running the
# Online Collection Software.
#
# Underlying database engine: Oracle or MySQL.
#
# Tested on Weblogic 10.3.4.
# 
# Prerequisites:
#
# - a running installation of the Weblogic application
#   server
#
# Usage:
#
# - customise the section below
# - launch WLST (WL_HOME/common/bin/wlst.cmd or .sh)
# - execute this script:
#   > execfile('<PATH_TO_THIS_SCRIPT>')
# - exit the WLST console:
#   > exit()
# ====================================================

# === CUSTOMISE START ===

# The following values should be adjusted to match the
# configuration parameters of the Weblogic server and the
# underlying database.

# Weblogic server details
SERVER_HOST 	= 'localhost'
SERVER_PORT 	= '7001'
WL_ADMIN_USER	= 'weblogic'
WL_ADMIN_PASS   = 'weblogic1'
WL_INSTANCE		= 'AdminServer'

# Database details
DB_DIALECT		= 'ORACLE' # supported values: ORACLE, MYSQL

DB_HOST			= 'HOST'
DB_PORT			= 'PORT'
DB_NAME			= 'DB' # DB service on ORACLE, DB on MYSQL, etc.
DB_USER			= 'USER'
DB_PASS			= 'PASS'

# === CUSTOMISE END ===

DB_DETAILS = {
	'ORACLE' : {
		'driver' : 'oracle.jdbc.xa.client.OracleXADataSource',
		'url'	 : 'jdbc:oracle:thin:@' + DB_HOST + ':' + DB_PORT + ':' + DB_NAME,
		'testsql': 'SQL SELECT 1 FROM DUAL'
	},
	'MYSQL' : {
		'driver' : 'com.mysql.jdbc.jdbc2.optional.MysqlXADataSource',
		'url'	 : 'jdbc:mysql://' + DB_HOST + ':' + DB_PORT + '/' + DB_NAME,
		'testsql': 'SQL SELECT 1'
	}
}

# connect

connect(WL_ADMIN_USER, WL_ADMIN_PASS, "t3://" + SERVER_HOST + ":" + SERVER_PORT)
edit()
startEdit()

# FileStore

cd('/')
cmo.createFileStore('OctFileStore')

cd('/FileStores/OctFileStore')
cmo.setDirectory('oct_filestore')
set('Targets',jarray.array([ObjectName('com.bea:Name=' + WL_INSTANCE + ',Type=Server')], ObjectName))

# JMS Server

cd('/')
cmo.createJMSServer('OctJMSServer')

cd('/Deployments/OctJMSServer')
cmo.setPersistentStore(getMBean('/FileStores/OctFileStore'))
set('Targets',jarray.array([ObjectName('com.bea:Name=' + WL_INSTANCE + ',Type=Server')], ObjectName))

# JMS SystemModule

cd('/')
cmo.createJMSSystemResource('OctJMSSystemModule')

cd('/SystemResources/OctJMSSystemModule')
set('Targets',jarray.array([ObjectName('com.bea:Name=' + WL_INSTANCE + ',Type=Server')], ObjectName))

# JMS SubDeployment

cmo.createSubDeployment('OctJMSSubDeployment')

cd('/SystemResources/OctJMSSystemModule/SubDeployments/OctJMSSubDeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=OctJMSServer,Type=JMSServer')], ObjectName))

# JMS ConnectionFactory

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule')
cmo.createConnectionFactory('OctExportQueueConnectionFactory')

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/ConnectionFactories/OctExportQueueConnectionFactory')
cmo.setJNDIName('OctExportQueueConnectionFactory')

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/ConnectionFactories/OctExportQueueConnectionFactory/SecurityParams/OctExportQueueConnectionFactory')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/ConnectionFactories/OctExportQueueConnectionFactory/ClientParams/OctExportQueueConnectionFactory')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/ConnectionFactories/OctExportQueueConnectionFactory/TransactionParams/OctExportQueueConnectionFactory')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/ConnectionFactories/OctExportQueueConnectionFactory')
cmo.setDefaultTargetingEnabled(true)

# JMS Queue (Export)

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule')
cmo.createQueue('OctExportQueue')

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/Queues/OctExportQueue')
cmo.setJNDIName('OctExportQueue')
cmo.setSubDeploymentName('OctJMSSubDeployment')

cd('/SystemResources/OctJMSSystemModule/SubDeployments/OctJMSSubDeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=OctJMSServer,Type=JMSServer')], ObjectName))

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/Queues/OctExportQueue/DeliveryFailureParams/OctExportQueue')
cmo.setExpirationLoggingPolicy('%header%,%properties%')
cmo.setRedeliveryLimit(12)
cmo.setExpirationPolicy('Log')

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/Queues/OctExportQueue/DeliveryParamsOverrides/OctExportQueue')
cmo.setRedeliveryDelay(300000)

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/Queues/OctExportQueue/DeliveryFailureParams/OctExportQueue')
cmo.unSet('ErrorDestination')

# JMS Queue (ExportDispatcher)

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule')
cmo.createQueue('OctExportDispatcherQueue')

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/Queues/OctExportDispatcherQueue')
cmo.setJNDIName('OctExportDispatcherQueue')
cmo.setSubDeploymentName('OctJMSSubDeployment')

cd('/SystemResources/OctJMSSystemModule/SubDeployments/OctJMSSubDeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=OctJMSServer,Type=JMSServer')], ObjectName))

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/Queues/OctExportDispatcherQueue/DeliveryFailureParams/OctExportDispatcherQueue')
cmo.setExpirationLoggingPolicy('%header%,%properties%')
cmo.setRedeliveryLimit(12)
cmo.setExpirationPolicy('Log')

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/Queues/OctExportDispatcherQueue/DeliveryParamsOverrides/OctExportDispatcherQueue')
cmo.setRedeliveryDelay(300000)

cd('/JMSSystemResources/OctJMSSystemModule/JMSResource/OctJMSSystemModule/Queues/OctExportDispatcherQueue/DeliveryFailureParams/OctExportDispatcherQueue')
cmo.unSet('ErrorDestination')

# JDBC DataSource

cd('/')
cmo.createJDBCSystemResource('OctJdbcDataSource')

cd('/JDBCSystemResources/OctJdbcDataSource/JDBCResource/OctJdbcDataSource')
cmo.setName('OctJdbcDataSource')

cd('/JDBCSystemResources/OctJdbcDataSource/JDBCResource/OctJdbcDataSource/JDBCDataSourceParams/OctJdbcDataSource')
set('JNDINames',jarray.array([String('jdbc/oct')], String))

cd('/JDBCSystemResources/OctJdbcDataSource/JDBCResource/OctJdbcDataSource/JDBCDriverParams/OctJdbcDataSource')
cmo.setUrl(DB_DETAILS[DB_DIALECT]['url'])
cmo.setDriverName(DB_DETAILS[DB_DIALECT]['driver'])
cmo.setPassword(DB_PASS)

cd('/JDBCSystemResources/OctJdbcDataSource/JDBCResource/OctJdbcDataSource/JDBCDriverParams/OctJdbcDataSource/Properties/OctJdbcDataSource')
cmo.createProperty('user')

cd('/JDBCSystemResources/OctJdbcDataSource/JDBCResource/OctJdbcDataSource/JDBCDriverParams/OctJdbcDataSource/Properties/OctJdbcDataSource/Properties/user')
cmo.setValue(DB_USER)

cd('/JDBCSystemResources/OctJdbcDataSource/JDBCResource/OctJdbcDataSource/JDBCDataSourceParams/OctJdbcDataSource')
cmo.setGlobalTransactionsProtocol('TwoPhaseCommit')

cd('/JDBCSystemResources/OctJdbcDataSource/JDBCResource/OctJdbcDataSource/JDBCConnectionPoolParams/OctJdbcDataSource')
cmo.setMaxCapacity(15)
cmo.setCapacityIncrement(5)
cmo.setStatementCacheType('LRU')
cmo.setStatementCacheSize(10)
cmo.setInitialCapacity(5)
cmo.setHighestNumWaiters(2147483647)
cmo.setWrapTypes(true)
cmo.setShrinkFrequencySeconds(900)
cmo.setIgnoreInUseConnectionsEnabled(true)
cmo.setConnectionReserveTimeoutSeconds(10)
cmo.setInactiveConnectionTimeoutSeconds(15)
cmo.setPinnedToThread(false)
cmo.setStatementTimeout(-1)
cmo.setRemoveInfectedConnections(true)
cmo.setConnectionCreationRetryFrequencySeconds(0)
cmo.setSecondsToTrustAnIdlePoolConnection(10)
cmo.setTestConnectionsOnReserve(true)
cmo.setTestFrequencySeconds(120)
cmo.setTestTableName(DB_DETAILS[DB_DIALECT]['testsql'])
cmo.setLoginDelaySeconds(0)

cd('/SystemResources/OctJdbcDataSource')
set('Targets',jarray.array([ObjectName('com.bea:Name=' + WL_INSTANCE + ',Type=Server')], ObjectName))

# save and disconnect

save()
activate(block = 'true')
disconnect()
