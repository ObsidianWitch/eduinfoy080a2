package softarch.portal.db;

import java.util.Properties;

public interface AbstractDatabaseFactory {
	public RawDatabase getRawDatabase(Properties properties);
	public RegularDatabase getRegularDatabase(Properties properties);
	public UserDatabase getUserDatabase(Properties properties);
}
