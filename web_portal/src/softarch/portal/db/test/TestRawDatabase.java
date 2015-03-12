package softarch.portal.db.test;

import java.util.List;
import java.util.Iterator;
import java.util.Properties;

import softarch.portal.data.RawData;
import softarch.portal.db.RawDatabase;
import softarch.portal.db.sql.RawSQLDatabase;

/**
 * This is a test program for the raw database.
 * @author Niels Joncheere
 */
public class TestRawDatabase {
	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			properties.setProperty("dbUser", "njonchee");
			properties.setProperty("dbPassword", "chivas12");
			properties.setProperty("dbUrl", "localhost/njonchee");
			
			RawDatabase rd = new RawSQLDatabase(properties);
			System.out.println("NUMBER OF RECORDS");
			System.out.println(rd.getNumberOfRawRecords());
			System.out.println();
			System.out.println("RECORDS");
			List<RawData> l = rd.getRawData();
			for (Iterator<RawData> i = l.iterator(); i.hasNext(); )
				System.out.println( i.next().asXml() );
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
