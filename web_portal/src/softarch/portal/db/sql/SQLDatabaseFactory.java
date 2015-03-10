package softarch.portal.db.sql;

import java.util.Properties;

import softarch.portal.db.AbstractDatabaseFactory;
import softarch.portal.db.RawDatabase;
import softarch.portal.db.RegularDatabase;
import softarch.portal.db.UserDatabase;

public class SQLDatabaseFactory implements AbstractDatabaseFactory {

	public RawDatabase getRawDatabase(Properties properties) {
		return new RawSQLDatabase(properties);
	}

	public RegularDatabase getRegularDatabase(Properties properties) {
		return new RegularSQLDatabase(properties);
	}

	public UserDatabase getUserDatabase(Properties properties) {
		return new UserSQLDatabase(properties);
	}

}
