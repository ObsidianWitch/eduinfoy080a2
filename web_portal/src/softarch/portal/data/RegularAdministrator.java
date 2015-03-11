package softarch.portal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents a <i>regular administrator</i> user account.
 * @author Niels Joncheere
 */
public class RegularAdministrator extends Administrator {
	public RegularAdministrator(HttpServletRequest request) {
		super(request);
	}
	
	public RegularAdministrator(ResultSet rs) 
			throws SQLException, ParseException
	{
		super(rs);
	}
	
	public RegularAdministrator(String username, String password,
			String firstName, String lastName, String emailAddress,
			Date lastLogin) 
	{
		super(username, password, firstName, lastName, emailAddress, lastLogin);
	}
	
	@Override
	public String getType() {
		return "RegularAdministrator";
	}
}
