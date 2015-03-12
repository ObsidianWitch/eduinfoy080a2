package softarch.portal.db.test;

import java.util.List;
import java.util.Iterator;
import java.util.Properties;

import softarch.portal.data.RegularData;
import softarch.portal.db.RegularDatabase;
import softarch.portal.db.sql.RegularSQLDatabase;

/**
 * This is a test program for the regular database.
 * @author Niels Joncheere
 */
public class TestRegularDatabase {
	public static final String USAGE =
		"USAGE: java softarch.portal.db.TestRegularDatabase " +
		"<dbUser> <dbPassword> <dbUrl> <informationType> <queryString>";

	public static void main(String[] args) {
	
		if (args.length != 5) {
			System.out.println(USAGE);
			return;
		}

		String dbUser		= args[0];
		String dbPassword	= args[1];
		String dbUrl		= args[2];
		String informationType	= args[3];
		String queryString	= args[4];

		System.out.println("dbUser          : " + dbUser);
		System.out.println("dbPassword      : " + dbPassword);
		System.out.println("dbUrl           : " + dbUrl);
		System.out.println("informationType : " + informationType);
		System.out.println("queryString     : " + queryString);
		System.out.println();
		
		Properties properties = new Properties();
		properties.setProperty("dbUser", dbUser);
		properties.setProperty("dbPassword", dbPassword);
		properties.setProperty("dbUrl", dbUrl);
		
		RegularDatabase db = new RegularSQLDatabase(properties);

		try {
			System.out.println("NUMBER OF RECORDS");
			System.out.println(
				db.getNumberOfRegularRecords(informationType));
			System.out.println();
			System.out.println("RECORDS");
			List<RegularData> result = db.findRecords(	informationType,
							queryString);
			for (Iterator<RegularData> i = result.iterator(); i.hasNext(); )
				System.out.println(
					((RegularData) i.next()).asXml());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
