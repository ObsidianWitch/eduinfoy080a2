package softarch.portal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents a <i>cheap subscription</i> user account.
 * @author Niels Joncheere
 */
public class CheapSubscription extends RegularUser {
	public CheapSubscription(HttpServletRequest request) {
		super(request);
	}
	
	public CheapSubscription(ResultSet rs) throws SQLException, ParseException {
		super(rs);
	}
	
	public CheapSubscription(String username, String password, String firstName, 
			String lastName, String emailAddress, Date lastLogin) 
	{
		super(username, password, firstName, lastName, emailAddress, lastLogin);
	}
	
	@Override
	protected String getType() {
		return "CheapSubscription";
	}
}
