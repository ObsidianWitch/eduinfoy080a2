package softarch.portal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents a <i>free subscription</i> user account.
 * @author Niels Joncheere
 */
public class FreeSubscription extends RegularUser {
	public FreeSubscription(HttpServletRequest request) {
		super(request);
	}
	
	public FreeSubscription(ResultSet rs) throws SQLException, ParseException {
		super(rs);
	}
	
	public FreeSubscription(String username, String password, String firstName, 
			String lastName, String emailAddress, Date lastLogin) 
	{
		super(username, password, firstName, lastName, emailAddress, lastLogin);
	}
	
	@Override
	public String getType() {
		return "FreeSubscription";
	}
}
