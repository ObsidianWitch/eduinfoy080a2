package softarch.portal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents an <i>external administrator</i> user account.
 * @author Niels Joncheere
 */
public class ExternalAdministrator extends Administrator {
	public ExternalAdministrator(HttpServletRequest request) {
		super(request);
	}
	
	public ExternalAdministrator(ResultSet rs)
			throws SQLException, ParseException 
	{
		super(rs);
	}
	
	public ExternalAdministrator(String username, String password, 
			String firstName, String lastName, String emailAddress,
			Date lastLogin) 
	{
		super(username, password, firstName, lastName, emailAddress, lastLogin);
	}
	
	@Override
	protected String getType() {
		return "ExternalAdministrator";
	}
}
