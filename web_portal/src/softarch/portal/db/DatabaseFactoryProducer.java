package softarch.portal.db;

import softarch.portal.db.json.JSONDatabaseFactory;
import softarch.portal.db.sql.SQLDatabaseFactory;

public class DatabaseFactoryProducer {
	
	/**
	 * Get a factory based on the type of database we want to use.
	 * @param type of database
	 * @return database factory for the selected type of database
	 */
	public static AbstractDatabaseFactory getFactory(String type) {
		if (type.equals("SQL")) {
			return new SQLDatabaseFactory();
		}
		else if (type.equals("JSON")) {
			return new JSONDatabaseFactory();
		}
		
		return null;
	}
}
