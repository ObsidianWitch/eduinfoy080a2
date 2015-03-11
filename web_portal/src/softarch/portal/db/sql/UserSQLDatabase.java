package softarch.portal.db.sql;

import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseException;
import softarch.portal.db.UserDatabase;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

/**
 * This class encapsulates the user database.
 * @author Niels Joncheere
 */
public class UserSQLDatabase extends SQLDatabase implements UserDatabase {
	/**
	 * Creates a new user database.
	 */
	public UserSQLDatabase(Properties properties) {
		super(properties);
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile)
		throws DatabaseException {
		
		executeSql(profile.asSql());
	}

	/**
	 * Updates an existing user profile in the user database.
	 */
	public void update(UserProfile profile)
		throws DatabaseException {
		
		executeSql(profile.asSqlUpdate());
	}

	/**
	 * Returns the user with the specified username.
	 */
	public UserProfile findUser(String username) throws DatabaseException {

		try {
			Statement statement = getConnection().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE
			);
			
			ResultSet rs = statement.executeQuery(
				"SELECT * FROM UserProfile WHERE " +
				"Username = \'" + username + "\';"
			);
			
			if (rs.first()) {
				return new UserProfile(rs);
			}
			
			throw new DatabaseException("Invalid username!");
		}
		catch (SQLException e) {
			throw new DatabaseException("SQL Exception: " + e.getMessage());
		}
		catch (ParseException e) {
			throw new DatabaseException("Parse Exception: " + e.getMessage());
		}
	}

	/**
	 * Checks whether a user with the specified username exists.
	 */
	public boolean userExists(String username) throws DatabaseException {
		return findUser(username) != null;
	}
}
