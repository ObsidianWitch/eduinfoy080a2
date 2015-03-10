package softarch.portal.db.json;

import java.util.Properties;

import softarch.portal.db.AbstractDatabaseFactory;
import softarch.portal.db.RawDatabase;
import softarch.portal.db.RegularDatabase;
import softarch.portal.db.UserDatabase;

public class JSONDatabaseFactory implements AbstractDatabaseFactory {

	public RawDatabase getRawDatabase(Properties properties) {
		return new RawJSONDatabase(properties);
	}

	public RegularDatabase getRegularDatabase(Properties properties) {
		return new RegularJSONDatabase(properties);
	}

	public UserDatabase getUserDatabase(Properties properties) {
		return new UserJSONDatabase(properties);
	}

}
