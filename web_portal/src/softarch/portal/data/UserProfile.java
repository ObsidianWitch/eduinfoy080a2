package softarch.portal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents all user profiles.
 * @author Niels Joncheere
 */
public class UserProfile extends Data {
	public enum UserTypes {
		ExpertAdministrator("web_portal?Page=Administration"),
		ExternalAdministrator("web_portal?Page=Administration"),
		RegularAdministrator("web_portal?Page=Administration"),
		CheapSubscription("web_portal?Page=Query"),
		ExpensiveSubscription("web_portal?Page=Query"),
		FreeSubscription("web_portal?Page=Query"),
		Operator("web_portal?Page=Operation");
		
		private String defaultPage;
 
		private UserTypes(String defaultPage) {
			this.defaultPage = defaultPage;
		}
	}
	
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String emailAddress;
	protected Date   lastLogin;
	protected String type;
	
	/**
	 * Creates a new account from a
	 * <code>javax.servlet.http.HttpServletRequest</code> object.
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public UserProfile(HttpServletRequest request) {
		this(
			request.getParameter("Username"),
			request.getParameter("Password"),
			request.getParameter("FirstName"),
			request.getParameter("LastName"),
			request.getParameter("EmailAddress"),
			new Date(),
			request.getParameter("Type")
		);
	}
	
	/**
	 * Creates a new account from a <code>java.sql.ResultSet</code> object.
	 * @see java.sql.ResultSet
	 */
	public UserProfile(ResultSet rs) throws SQLException, ParseException {
			this.username     = rs.getString("Username");
			this.password     = rs.getString("Password");
			this.firstName    = rs.getString("FirstName");
			this.lastName     = rs.getString("LastName");
			this.emailAddress = rs.getString("EmailAddress");
			this.lastLogin    = df.parse(rs.getString("LastLogin"));
			this.type         = rs.getString("Type");
		}
	
	/**
	 * Creates a new account.
	 */
	public UserProfile(String username, String password, String firstName, 
			String lastName, String emailAddress, Date lastLogin, String type) 
	{
		this.username     = username;
		this.password     = password;
		this.firstName    = firstName;
		this.lastName     = lastName;
		this.emailAddress = emailAddress;
		this.lastLogin    = lastLogin;
		this.type         = type;
	}

	/**
	 * When a user has logged in successfully, he will be
	 * redirected to this page.
	 */
	public String getDefaultPage() {
		return UserTypes.valueOf(type).defaultPage;
	}
	
	/**
	 * Returns an XML representation of the object.
	 */
	public String asXml() {
		return	"<" + type + ">" +
			"<username>" + normalizeXml(username) + "</username>" +
			// password is not returned,
			// as it should only be used internally
			"<firstName>" + 
			normalizeXml(firstName) +
			"</firstName>" +
			"<lastName>" + normalizeXml(lastName) + "</lastName>" +
			"<emailAddress>" +
			normalizeXml(emailAddress) +
			"</emailAddress>" +
			"<lastLogin>" + df.format(lastLogin) + "</lastLogin>" +
			"</" + type + ">";
	}

	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSql() {
		return	"INSERT INTO userProfile (Username, " +
				"Password, FirstName, LastName, EmailAddress, " +
				"LastLogin, Type) VALUES (\'" + normalizeSql(username) +
				"\', \'" + normalizeSql(password) +"\', \'" +
				normalizeSql(firstName) + "\', \'" +
				normalizeSql(lastName) + "\', \'" +
				normalizeSql(emailAddress) + "\', \'" + 
				df.format(lastLogin) + "\', \'" +
				normalizeSql(type) + "\');";
	}

	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdate() {
		return  "UPDATE UserProfile SET Password = \'" +
				normalizeSql(password) + "\', FirstName = \'" +
				normalizeSql(firstName) + "\', LastName = \'" +
				normalizeSql(lastName) + "\', EmailAddress = \'" +
				normalizeSql(emailAddress) + "\', LastLogin = \'" +
				df.format(lastLogin) + "\', Type = \'" + 
				normalizeSql(type) + "\' " + "WHERE Username = \'" +
				normalizeSql(username) + "\';";
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public UserProfile setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}
}
