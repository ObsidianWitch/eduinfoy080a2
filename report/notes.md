% Assignement 2 notes

# Problems & solutions

* useless dependency injection
    1. InternetFrontEnd.init(): load properties (dbUser, dbPassword et dbUrl)
    from the configuration file (*web_portal.cfg*) and pass them to
    ApplicationFacade .
    2. ApplicationFacade pass the properties to DatabaseFacade
    3. DatabaseFacade pass the properties to UserDatabase, RegularDatabase and
    RawDatabase
    * problem: these properties are SQL related but are required for
    the DatabaseFacade. We would like to use other databases (e.g. JSON) than
    SQL ones, so we need to remove these dependencies from DatabaseFacade.
    Still, the configuration need to be retrieved from InternetFacade, so
    instead of passing the individual properties as parameters, we can pass the
    object of the *Properties* class which has been loaded from the configuration
    file.
    * rename these properties with the followng scheme: dbSQL*
    * "The database layer interface should be generic, and not expose the
    specific requirements of the layer." (cf subject)

* the SQL driver is forced to the HyperSQL JDBC driver, what if we want to use
another type of SQL database (e.g. MySQL)?

* *Database*, *RawDatabase*, *RegularDatabase* and *userDatabase* are all
related to an SQL database, we would like to add a database based on the JSON
format. We need to rename the previously cited class to show their real use
(and move them into a separate package).
Then, we need to create interfaces which will be implemented by the databases we
need (e.g. *SQLRawDatabase* implements the *RawDatabase* interface).
Finally, the type of database can be switched by modifying a property in
*web_portal.cfg*.
    * the database type can be tested to decide which classes to instantiate in
    *DatabaseFacade*. However, each time we want to add a type of database in
    the application we need to also add it in the *if..else* of the
    *DatabaseFacade* constructor.
    ~~~
     	if (dbType.equals("SQL")) {
			userDb		= new UserSQLDatabase(properties);
			regularDb	= new RegularSQLDatabase(properties);
			rawDb		= new RawSQLDatabase(properties);
		} else if (dbType.equals("JSON")) {
            ...
        }
    ~~~
    First, these conditions can be moved outside to avoid having a huge
    constructor. An Abstract Factory pattern was used in this case (it
    seems a bit overkill for this case, but it was a nice real case in which
    an Abstract Factory pattern could be implemented).
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
    reflection has an impact on performances and should be used as a last
    resort.
     
     

* Managers are only wrappers and give more or less direct access to the database
layer, is it acceptable? See if can refactor so that more processing can be done
by the application layer.

* "A 3-tiered system really is made up of layers. The UI Layer has access to the
Logic Layer, and the Logic Layer has access to the Data Layer. But the UI Layer
cannot directly access the Data Layer. In order for the UI Layer to access data,
it must go through the Logic Layer via some kind of interface." ([source](http://allthingscs.blogspot.be/2011/03/mvc-vs-3-tier-pattern.html))
-> is this application really a 3-tiered system?

* The UI layer would gain in clarity by using a template engine (e.g. something
    similar to twig for php, mustache.java, apache velocity)
    
* SQL queries are written directly in Strings, a SQLQueryBuilder and a Directory
    (e.g. Symfony2 & Doctrine)
    could be created to be able to reuse parts of queries
    
* data coupled with the database layer
    * need a constructor for constructing a data entity from a database result
    object (e.g. *public CheapSubscription(ResultSet rs)*)
    * need a way to construct an object which can be inserted/updated into the
    database (e.g. *asSql()*)
    * -> Directory idea (e.g. Symfony2 & Doctrine)

* ExpensiveSubscription, CheapSubscription and FreeSubscription are the same
thing under the hood, there is a lot of code duplication. They could be grouped
as a Subscription with a field specifying which type of subscription it is
(expensive, cheap or free). It would then be easier to add new types of
subscription.
    * When we modify the *UserProfile* (e.g. add *asJson()* method) it has an impact
    on all subclasses -> may be a pain (visitor pattern).

    * UserProfile.getType() is used to have an uniform way to knwow where the data
    must be added -> refacto data
    
    * after some research: ExpensiveSubscription, CheapSubscription, FreeSubscription,
    ExpertAdministator, ExternalAdministrator, RegularAdministrator and Operator
    are the same thing...

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
