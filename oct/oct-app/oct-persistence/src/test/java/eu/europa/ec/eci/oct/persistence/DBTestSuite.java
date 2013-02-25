package eu.europa.ec.eci.oct.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Assert;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.BeforeClass;


public class DBTestSuite {
	
	static DAOFactory daof = new TestDAOFactory("oct");

	
	@BeforeClass
	public static void setUp(){		
		
		((TestDAOFactory)daof).em.unwrap(Session.class).doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				try {				
					IDataSet dataSet = new XmlDataSet(DBTestSuite.class.getResourceAsStream("/octTestData.xml"));		
					
					DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(connection), dataSet);
					
				} catch (DatabaseUnitException e) {
					Assert.fail(e.getMessage());
				} catch (SQLException e) {
					Assert.fail(e.getMessage());
				}				
			}

		});
	}
	
	
	


}
