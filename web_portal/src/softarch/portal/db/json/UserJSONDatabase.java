package softarch.portal.db.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseException;
import softarch.portal.db.UserDatabase;

public class UserJSONDatabase extends JSONDatabase implements UserDatabase {
	
	public File usersFile;

	public UserJSONDatabase(Properties properties) {
		super(properties);
		
		try {
			String dbUrl = properties.getProperty("dbJSONUrl");
			usersFile = new File(dbUrl + "/users.json");
			createJsonDocument();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean createJsonDocument() throws IOException {
		boolean created = usersFile.createNewFile();
		if (!created) { return false; }
		
		JsonArray ja = new JsonArray();
		
		Gson gson = new Gson();
		String jsonDocument = gson.toJson(ja);
		writeToFile(jsonDocument);
		
		return true; 
	}

	public void insert(UserProfile profile) throws DatabaseException {
		Gson gson = new Gson();
		
		JsonArray usersArray = getUsersJson();
		
		JsonElement jsonProfile = gson.toJsonTree(profile);
		usersArray.add(jsonProfile);

		try {
			writeToFile(usersArray.toString());
		} catch (IOException e) {
			throw new DatabaseException("IOException: " + e.getMessage());
		}
	}
	
	public void update(UserProfile profile) throws DatabaseException {
		remove(profile);
		insert(profile);
	}
	
	private void remove(UserProfile profile) throws DatabaseException {
		JsonArray usersArray = getUsersJson();
		
		for (JsonElement userProfileElement : usersArray) {
			JsonObject userProfileObject = (JsonObject) userProfileElement;
			
			if (userProfileObject.get("username").getAsString()
					.equals(profile.getUsername())) 
			{
				usersArray.remove(userProfileElement);
				break;
			}
		}
		
		try {
			writeToFile(usersArray.toString());
		} catch (IOException e) {
			throw new DatabaseException("IOException: " + e.getMessage());
		}
	}

	public UserProfile findUser(String username) throws DatabaseException {
		Gson gson = new Gson();
		
		try {
			JsonArray usersArray = getUsersJson();
			
			for (JsonElement userProfileElement : usersArray) {
				JsonObject userProfileObject = (JsonObject) userProfileElement;
				
				if (userProfileObject.get("username").getAsString()
						.equals(username)) 
				{
					return (UserProfile) gson.fromJson(
						userProfileObject, UserProfile.class
					);
				}
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean userExists(String username) throws DatabaseException {
		return findUser(username) != null;
	}
	
	private JsonArray getUsersJson() throws DatabaseException {
		JsonArray jsonParsed = null;
		
		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(usersFile));
			jsonParsed = jsonElement.getAsJsonArray();
		}
		catch (FileNotFoundException e) {
			throw new DatabaseException(
				"File not found Exception: " + e.getMessage()
			);
		}  
		
		return jsonParsed;
	}
	
	private void writeToFile(String json) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(
        		new FileOutputStream(usersFile)
        );
        
        out.write(json);
        out.close();
	}

}
