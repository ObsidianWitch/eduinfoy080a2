% Assignement 2 notes

# Problems

* useless dependency injection
    1. InternetFrontEnd.init(): load properties (dbUser, dbPassword et dbUrl)
    from the configuration file (*web_portal.cfg*) and pass them to
    ApplicationFacade .
    2. ApplicationFacade pass the properties to DatabaseFacade
    3. DatabaseFacade pass the properties to UserDatabase, RegularDatabase and
    RawDatabase
    * first problem: passing these dependencies is not required, they could
    directly be retrieved in the Database class
    * second problem: these properties are SQL related but are required for
    the DatabaseFacade. We would like to use other databases (e.g. JSON) than
    SQL ones, so we need to remove these attributes from DatabaseFacade.
    * rename these properties with the followng scheme: dbSQL*

* the SQL driver is forced to the HyperSQL JDBC driver, what if we want to use
another type of SQL database (e.g. MySQL)?

* Managers are only wrappers and give more or less direct access to the database
layer, is it acceptable? See if can refactor so that more processing can be done
by the application layer.

* "A 3-tiered system really is made up of layers. The UI Layer has access to the
Logic Layer, and the Logic Layer has access to the Data Layer. But the UI Layer
cannot directly access the Data Layer. In order for the UI Layer to access data,
it must go through the Logic Layer via some kind of interface." ([source](http://allthingscs.blogspot.be/2011/03/mvc-vs-3-tier-pattern.html))
-> is this application really a 3-tiered system?

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
