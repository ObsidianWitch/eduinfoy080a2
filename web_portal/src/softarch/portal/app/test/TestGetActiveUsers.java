package softarch.portal.app.test;

import java.util.List;
import java.util.Iterator;
import java.util.Properties;

import softarch.portal.app.ApplicationFacade;
import softarch.portal.data.UserProfile;

/**
 * This is a test program for the application facade's
 * <code>getActiveUsers</code> method.
 * @author Niels Joncheere
 */
public class TestGetActiveUsers {
	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			properties.setProperty("dbUser", "njonchee");
			properties.setProperty("dbPassword", "chivas12");
			properties.setProperty("dbUrl", "localhost/njonchee");
			
			ApplicationFacade appFacade = new ApplicationFacade(properties);
			System.out.println(appFacade.login("God", "7475"));
			System.out.println(appFacade.login("Nilipili", "7475"));
			System.out.println(appFacade.login("Niels82", "7475"));
			List<UserProfile> users = appFacade.getActiveUsers();
			for (Iterator<UserProfile> i = users.iterator(); i.hasNext(); )
				System.out.println(
					((UserProfile) i.next()).asXml());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
