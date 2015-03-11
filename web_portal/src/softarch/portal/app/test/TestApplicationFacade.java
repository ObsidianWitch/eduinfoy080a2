package softarch.portal.app.test;

import softarch.portal.app.ApplicationFacade;
import softarch.portal.data.UserProfile;

import java.util.Date;
import java.util.Properties;

/**
 * This is a test program for the application facade.
 * @author Niels Joncheere
 */
public class TestApplicationFacade {
	public static void main(String[] args) {
		try {
			UserProfile fs = new UserProfile(
				"Niels82",
				"7475",
				"Niels",
				"Joncheere",
				"niels@joncheere.be",
				new Date(),
				UserProfile.UserTypes.FreeSubscription.toString()
			);
			
			Properties properties = new Properties();
			properties.setProperty("dbUser", "njonchee");
			properties.setProperty("dbPassword", "chivas12");
			properties.setProperty("dbUrl", "localhost/njonchee");
			
			ApplicationFacade appFacade = new ApplicationFacade(properties);
			appFacade.add(fs);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
