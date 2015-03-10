package softarch.portal.db.json;

import java.util.Properties;

import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseException;
import softarch.portal.db.UserDatabase;

public class UserJSONDatabase extends JSONDatabase implements UserDatabase {

	public UserJSONDatabase(Properties properties) {
		super(properties);
	}

	public void insert(UserProfile profile) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	public void update(UserProfile profile) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	public UserProfile findUser(String username) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean userExists(String username) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

}
