package softarch.portal.db.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
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
		
		JsonObject jo = new JsonObject(); // TODO JsonArray
		// TODO won't be necessary anymore when UserProfile subclasses will be
		// removed
		jo.add("FreeSubscription", new JsonArray());
		jo.add("CheapSubscription", new JsonArray());
		jo.add("ExpensiveSubscription", new JsonArray());
		
		Gson gson = new Gson();
		String jsonDocument = gson.toJson(jo);
		writeToFile(jsonDocument);
		
		return true; 
	}

	public void insert(UserProfile profile) throws DatabaseException {
		Gson gson = new Gson();
		
		JsonObject jsonParsed = getUsersJson();
		
		// TODO remove UserProfile subclasses and add type field to UserProfile
		String type = profile.getType();
		JsonElement jsonProfile = gson.toJsonTree(profile);
		
		JsonArray usersFromType = jsonParsed.getAsJsonArray(type);
		usersFromType.add(jsonProfile);

		try {
			writeToFile(jsonParsed.toString());
		} catch (IOException e) {
			throw new DatabaseException("IOException: " + e.getMessage());
		}
	}
	
	public void update(UserProfile profile) throws DatabaseException {
		remove(profile);
		insert(profile);
	}
	
	private void remove(UserProfile profile) throws DatabaseException {
		String type = profile.getType();
		JsonObject jsonParsed = getUsersJson();
		JsonArray usersFromType = jsonParsed.getAsJsonArray(type);
		
		for (JsonElement userProfileElement : usersFromType) {
			JsonObject userProfileObject = (JsonObject) userProfileElement;
			
			if (userProfileObject.get("username").getAsString()
					.equals(profile.getUsername())) 
			{
				usersFromType.remove(userProfileElement);
				break;
			}
		}
		
		try {
			writeToFile(jsonParsed.toString());
		} catch (IOException e) {
			throw new DatabaseException("IOException: " + e.getMessage());
		}
	}

	public UserProfile findUser(String username) throws DatabaseException {
		// TODO refacto type field
		Gson gson = new Gson();
		
		try {
			JsonObject jsonParsed = getUsersJson();
			for (Map.Entry<String,JsonElement> entry : jsonParsed.entrySet()) {
				JsonArray types = (JsonArray) entry.getValue();
				
				for (JsonElement userProfileElement : types) {
					JsonObject userProfileObject = (JsonObject) userProfileElement;
					
					if (userProfileObject.get("username").getAsString()
							.equals(username))
					{					
						return (UserProfile) gson.fromJson(
							userProfileObject,
							Class.forName("softarch.portal.data." + entry.getKey())
						);
					}
				}
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean userExists(String username) throws DatabaseException {
		return findUser(username) != null;
	}
	
	private JsonObject getUsersJson() throws DatabaseException {
		JsonObject jsonParsed = null;
		
		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(usersFile));
			jsonParsed = jsonElement.getAsJsonObject();
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
