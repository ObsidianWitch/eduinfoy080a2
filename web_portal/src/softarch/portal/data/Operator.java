package softarch.portal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * This class represents an <i>operator</i> user account.
 * @author Niels Joncheere
 */
public class Operator extends UserProfile {
	public Operator(HttpServletRequest request) {
		super(request);
	}
	
	public Operator(ResultSet rs) throws SQLException, ParseException {
		super(rs);
	}
	
	public Operator(String username, String password, String firstName, 
			String lastName, String emailAddress, Date lastLogin) 
	{
		super(username, password, firstName, lastName, emailAddress, lastLogin);
	}
	
	/**
	 * When an operator has logged in successfully, he will be
	 * redirected to this page.
	 */
	public String getDefaultPage() {
		return "web_portal?Page=Operation";
	}

	@Override
	protected String getType() {
		return "Operator";
	}
}
