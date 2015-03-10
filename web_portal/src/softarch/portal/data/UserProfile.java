package softarch.portal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * This is an abstract superclass for all user profiles.
 * @author Niels Joncheere
 */
public abstract class UserProfile extends Data {
	protected	String	username;
	protected	String	password;
	protected	String	firstName;
	protected	String	lastName;
	protected	String	emailAddress;
	protected	Date	lastLogin;
	
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
			new Date()
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
		}
	
	/**
	 * Creates a new account.
	 */
	public UserProfile(String username, String password, String firstName, 
			String lastName, String emailAddress, Date lastLogin) 
	{
		this.username     = username;
		this.password     = password;
		this.firstName    = firstName;
		this.lastName     = lastName;
		this.emailAddress = emailAddress;
		this.lastLogin    = lastLogin;
	}

	/**
	 * When a user has logged in successfully, he will be
	 * redirected to this page.
	 */
	public abstract String getDefaultPage();
	
	/**
	 * Type of user profile (e.g. Operator, ExpertAministrator, 
	 * CheapSubscription)
	 */
	protected abstract String getType();
	
	/**
	 * Returns an XML representation of the object.
	 */
	public String asXml() {
		return	"<" + getType() + ">" +
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
			"</" + getType() + ">";
	}

	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSql() {
		return	"INSERT INTO " + getType() + " (Username, " +
			"Password, FirstName, LastName, EmailAddress, " +
			"LastLogin) VALUES (\'" + normalizeSql(username) +
			"\', \'" + normalizeSql(password) +"\', \'" +
			normalizeSql(firstName) + "\', \'" +
			normalizeSql(lastName) + "\', \'" +
			normalizeSql(emailAddress) + "\', \'" + 
			df.format(lastLogin) + "\');";
	}

	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdate() {
		return  "UPDATE " + getType() + " SET Password = \'" +
			normalizeSql(password) + "\', FirstName = \'" +
			normalizeSql(firstName) + "\', LastName = \'" +
			normalizeSql(lastName) + "\', EmailAddress = \'" +
			normalizeSql(emailAddress) + "\', LastLogin = \'" +
			df.format(lastLogin) + "\' " + "WHERE Username = \'" +
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
