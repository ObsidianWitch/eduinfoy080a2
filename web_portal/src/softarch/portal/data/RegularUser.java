package softarch.portal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is a superclass for all regular user accounts.
 * @author Niels Joncheere
 */
public abstract class RegularUser extends UserProfile {
	public RegularUser(HttpServletRequest request) {
		super(request);
	}
	
	public RegularUser(ResultSet rs) throws SQLException, ParseException {
		super(rs);
	}
	
	public RegularUser(String username, String password, String firstName, 
			String lastName, String emailAddress, Date lastLogin) 
	{
		super(username, password, firstName, lastName, emailAddress, lastLogin);
	}
	
	/**
	 * When a regular user has logged in successfully, he will be
	 * redirected to this page.
	 */
	public String getDefaultPage() {
		return "web_portal?Page=Query";
	}
}
