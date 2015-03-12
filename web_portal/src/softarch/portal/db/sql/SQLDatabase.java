package softarch.portal.db.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Iterator;
import java.util.Properties;

import softarch.portal.db.DatabaseException;

/**
 * This abstract class implements the behaviour that is to be shared
 * by all SQL databases.
 * @author Niels Joncheere
 */
public class SQLDatabase {
	protected String dbUser;
	protected String dbPassword;
	protected String dbUrl;

	/**
	 * Creates a new database.
	 */
	public SQLDatabase(Properties properties) {
		this.dbUser	= properties.getProperty("dbSQLUser");
		this.dbPassword	= properties.getProperty("dbSQLPassword");
		this.dbUrl	= properties.getProperty("dbSQLUrl");
	}

	/**
	 * Executes the given SQL queries.
	 */
	public void executeSql(List<String> queries)
		throws DatabaseException {

		for (Iterator<String> i = queries.iterator(); i.hasNext(); )
			executeSql(i.next());
	}
	
	public Connection getConnection() throws DatabaseException, SQLException {
		// Load the HyperSQL JDBC driver:
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
		}
		catch (Exception e) {
			throw new DatabaseException(
				"Unable to load the HyperSQL JDBC driver!");
		}
		
		Connection dbConnection = DriverManager.getConnection(
									"jdbc:hsqldb:" + dbUrl,
									dbUser,
									dbPassword);
		
		return dbConnection;
	}

	/**
	 * Executes the given SQL query.
	 * Note that no result will be returned.
	 */
	public void executeSql(String query)
		throws DatabaseException {

		

		// Connect to the database:
		try {
			Connection dbConnection = getConnection();
			Statement statement = dbConnection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE
			);

			statement.executeUpdate(query);

			statement.close();
			dbConnection.close();
		}

		// Exception handling:
		catch (SQLException e) {
			throw new DatabaseException(
				"SQL Exception: " + e.getMessage());
		}
		catch (Exception e) {
			throw new DatabaseException(
				"Unexpected Exception: " + e.getMessage());
		}
	}
}
