package softarch.portal.db.test;

import softarch.portal.data.RawData;
import softarch.portal.data.SoftwareRepository;
import softarch.portal.db.RawDatabase;
import softarch.portal.db.sql.RawSQLDatabase;

import java.util.Date;
import java.util.Properties;
import java.net.URL;

/**
 * This is a test program for the raw database's
 * <code>addRawData</code> method.
 * @author Niels Joncheere
 */
public class TestAddRawData {
	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			properties.setProperty("dbUser", "njonchee");
			properties.setProperty("dbPassword", "chivas12");
			properties.setProperty("dbUrl", "localhost/njonchee");
			
			RawDatabase rawDb = new RawSQLDatabase(properties);
			RawData rd = new RawData(0, "boe");
			rd.setStructure(new SoftwareRepository(
				new Date(),
				"Nilipili",
				"Niels\' Repository",
				new URL("http://void")));
//			rawDb.addRawData(rd);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
