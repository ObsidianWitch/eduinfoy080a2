package softarch.portal.db.json;

import java.util.Properties;

public class JSONDatabase {
	protected String dbUrl;
	
	public JSONDatabase(Properties properties) {
		this.dbUrl = properties.getProperty("dbJSONUrl");
	}
}
