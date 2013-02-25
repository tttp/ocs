package eu.europa.ec.eci.oct.persistence.loader;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import eu.europa.ec.eci.oct.persistence.DAOFactory;
import eu.europa.ec.eci.oct.persistence.TestDAOFactory;

public class DBLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// MySQL variant
		//DAOFactory daof = new TestDAOFactory("mysqloct");
		
		// Oracle variant
		DAOFactory daof = new TestDAOFactory("oracleoct");
		
		((TestDAOFactory)daof).getEntityManager().unwrap(Session.class).doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				IDataSet dataSet;
				try {
					//dataSet = new XmlDataSet(DBTestSuite.class.getResourceAsStream("/octData.xml"));
					dataSet = new CsvDataSet(new File("./src/main/db/data"));
					
					DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(connection), dataSet);
					
				} catch (DataSetException e) {
					e.printStackTrace();
				} catch (DatabaseUnitException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						connection.commit();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
	}

}
