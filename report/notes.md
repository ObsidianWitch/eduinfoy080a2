% Assignement 2 notes

# Required functionality
Your new layer does not need to implement all of the original layer’s
functionality. Only the following use cases need to be supported:

* Creating:
    * a new “free subscription” user
    * a new “cheap subscription” user
    * a new “expensive subscription” user
* Log in and log out with the new users
* All other operations do not need to be implemented, but can raise an exception.
* Enable a developer to change between database layers using a configuration
setting. Layer-specific settings should be in the configuration file.
* The database layer interface should be generic, and not expose the specific
requirements of the layer.
* Identify the remaining design deficiencies and include a discussion in the
report.

# Problems & ideas

* the SQL driver is forced to the HyperSQL JDBC driver, what if we want to use
another type of SQL database (e.g. MySQL)?

* Managers are only wrappers and give more or less direct access to the database
layer, is it acceptable? See if can refactor so that more processing can be done
by the application layer.

* "A 3-tiered system really is made up of layers. The UI Layer has access to the
Logic Layer, and the Logic Layer has access to the Data Layer. But the UI Layer
cannot directly access the Data Layer. In order for the UI Layer to access data,
it must go through the Logic Layer via some kind of interface."
([source](http://allthingscs.blogspot.be/2011/03/mvc-vs-3-tier-pattern.html))
-> is this application really a 3-tiered system?

* The UI layer would gain in clarity by using a template engine (e.g. something
similar to twig for php, mustache.java, apache velocity)
    
* SQL queries are written directly in Strings, a SQLQueryBuilder and a Repository
(e.g. Symfony2 & Doctrine) could be created to be able to reuse parts of queries
    
* data coupled with the database layer -> **data should be decoupled from where
it will be stored**
    * need a constructor for constructing a data entity from a database result
    object (e.g. *public CheapSubscription(ResultSet rs)*)
    * need a way to construct an object which can be inserted/updated into the
    database (e.g. *asSql()*)
    * -> Repository idea (e.g. Symfony2 & Doctrine)
    * Data.normalizeXML & Data.normalizeSql should already exist in libraries and
    should be handled at the db layer level, not in the data itself

* Data, RegularData, UserProfile and RawData seem to have some inconsistencies
in their interfaces
    * asSql()
    * asSql(RawData)
    * asSqlDelete(RawData)
    * asSqlDelete()
    * asSqlUpdate()
    
* talk about the JSONDatabase implementation
    
# Solved problems

## useless dependency injection

1. *InternetFrontEnd.init()*: load properties (dbUser, dbPassword et dbUrl)
from the configuration file (*web_portal.cfg*) and pass them to
*ApplicationFacade*
2. *ApplicationFacade* pass the properties to *DatabaseFacade*
3. *DatabaseFacade* pass the properties to *UserDatabase*, *RegularDatabase* and
*RawDatabase*

The problem is, these properties are SQL related but are required for the
DatabaseFacade. "The database layer interface should be generic, and not expose
the specific requirements of the layer." (cf subject). We would like to use
other databases (e.g. JSON) than SQL ones, so we need to remove or modify these
dependencies from DatabaseFacade. The configuration need to be retrieved from
InternetFacade and given to the database layer by passing trough the applcation
layer, so, instead of passeing the individual properties as parameters, we can
pass the *Properties* object holding all properties.

additional modifications: rename properties related to SQL with the following
pattern: dbSQL* (e.g. dbUser -> dbSQLUser).

## Databases
*Database*, *RawDatabase*, *RegularDatabase* and *UserDatabase* are all
related to an SQL database, we would like to add a database based on the JSON
format. We need to rename the previously cited class to show their real use
(and move them into a separate package).
    
* *Database* -> *SQLDatabase*
* *RawDatabase* -> *RawSQLDatabase*
* *RegularDatabase* -> *RegularSQLDatabase*
* *UserDatabase* -> *UserSQLDatabase*

Then, we need to create interfaces which will be implemented by the databases we
need (e.g. *SQLRawDatabase* implements the *RawDatabase* interface).

Finally, we must be able to switch the type of database by modifying a property
(*dbType*) in the *web_portal.cfg* file. The database type can be tested
to decide which classes to instantiate in *DatabaseFacade*. However, each time
we want to add a type of database in the application we also need to add it in
the long *if..else* of the *DatabaseFacade* constructor which can be seen below.
~~~
 	if (dbType.equals("SQL")) {
		userDb		= new UserSQLDatabase(properties);
		regularDb	= new RegularSQLDatabase(properties);
		rawDb		= new RawSQLDatabase(properties);
	} else if (dbType.equals("JSON")) {
        ...
    }
~~~

These conditions can be moved outside to avoid having a huge
constructor. An Abstract Factory pattern was used, which may be a bit overkill
in this case (but it was a real application of the Abstract Factory pattern and
I wanted to try it).

~~~
public interface AbstractDatabaseFactory {
	public RawDatabase getRawDatabase(Properties properties);
	public RegularDatabase getRegularDatabase(Properties properties);
	public UserDatabase getUserDatabase(Properties properties);
}

public class DatabaseFactoryProducer {
	
	/**
	 * Get a factory based on the type of database we want to use.
	 * @param type of database
	 * @return database factory for the selected type of database
	 */
	public static AbstractDatabaseFactory getFactory(String type) {
		if (type.equals("SQL")) {
			return new SQLDatabaseFactory();
		}
		
		return null;
	}
}

public class SQLDatabaseFactory implements AbstractDatabaseFactory {

	public RawDatabase getRawDatabase(Properties properties) {
		return new RawSQLDatabase(properties);
	}

	public RegularDatabase getRegularDatabase(Properties properties) {
		return new RegularSQLDatabase(properties);
	}

	public UserDatabase getUserDatabase(Properties properties) {
		return new UserSQLDatabase(properties);
	}

}

public DatabaseFacade(Properties properties) {
	String dbType = properties.getProperty("dbType");
	AbstractDatabaseFactory df = DatabaseFactoryProducer.getFactory(dbType);
	
	userDb    = df.getUserDatabase(properties);
	regularDb = df.getRegularDatabase(properties);
	rawDb     = df.getRawDatabase(properties);
}
~~~

Here we only have two databases implementations, but should the number grow
quickly, a more dynamic solution would be to use reflection. However using
reflection has an impact on performances and should be used as a last resort.

## UserProfile subclasses code duplication

*ExpensiveSubscription*, *CheapSubscription*, *FreeSubscription*,
*ExpertAdministator*, *ExternalAdministrator*, *RegularAdministrator* and
*Operator* contain a lot of code duplication (constructors, *asSql()*,
*asSqlUpdate() and *asXml()*), only a few strings are different.

First, the *getType()* abstract method was added to the *UserProfile*. All its
subclasses need to implement it by indicating the type of user. It will be user
further below as an uniform way to know where the data must be added (e.g. SQL
table, XML tag).

~~~
public class UserProfile extends Data {
    ...
    protected abstract String getType();
    ...
}

public class CheapSubscription extends RegularUser {
    ...
    
    @Override
	protected String getType() {
		return "CheapSubscription";
	}
    ...
}
~~~

With the *getType()* method we can modify and move the *asSql()*, *asSqlUpdate()*,
and *asXml()* methods from the subclasses to *UserProfile*. The constructors can
also be moved.

~~~
public class UserProfile extends Data {
	public UserProfile(HttpServletRequest request) { ... }
    public UserProfile(ResultSet rs) throws SQLException, ParseException {
        ...
    }
    public UserProfile(String username, String password, String firstName,
		String lastName, String emailAddress, Date lastLogin)
    { ... }
        
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
}
~~~

Further code refactoring could be done, for example we could add a *String*
field containing the type of user. Then, we could remove the
*ExpensiveSubscription*, *CheapSubscription*, *FreeSubscription*,
*ExpertAdministator*, *ExternalAdministrator*, *RegularAdministrator* and
*Operator* classes. It would then be easier to add new types of users.
<!-- TODO see if I have enough motivation to do it -->

<!-- TODO speak of ui.RegistrationPage -> we do not have to do a check on the
subscription type anymore
			switch (request
				.getParameter("Subscription")
				.charAt(0)) {

				case 'F':
					up = new FreeSubscription(request);
					break;
				case 'C':
					up = new CheapSubscription(request);
					break;
				case 'E':
					up = new ExpensiveSubscription(request);
					break;
				default:
					throw new ApplicationException(
						"You did not provide a valid " +
						"subscription type.  Please " +
						"try again.");
			}
-->

# Miscellaneous

The following files were added to git but further changes are not commited :

* DB/web_portal.lck
* DB/web_portal.log
* DB/web_portal.properties
* DB/web_portal.script

This is done with the following command :
~~~
git update-index --assume-unchanged file
~~~

([source](https://stackoverflow.com/questions/936249/stop-tracking-and-ignore-changes-to-a-file-in-git))

The following JSON parsing library was added to the project: https://jsonp.java.net/
