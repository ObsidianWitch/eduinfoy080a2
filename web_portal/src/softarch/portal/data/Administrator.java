package softarch.portal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is a superclass for all administrator accounts.
 * @author Niels Joncheere
 */
public abstract class Administrator extends UserProfile {
	public Administrator(HttpServletRequest request) {
		super(request);
	}
	
	public Administrator(ResultSet rs) throws SQLException, ParseException {
		super(rs);
	}
	
	public Administrator(String username, String password, String firstName, 
			String lastName, String emailAddress, Date lastLogin) 
	{
		super(username, password, firstName, lastName, emailAddress, lastLogin);
	}
	
	/**
	 * When an administrator has logged in successfully, he will be
	 * redirected to this page.
	 */
	public String getDefaultPage() {
		return "web_portal?Page=Administration";
	}
}
