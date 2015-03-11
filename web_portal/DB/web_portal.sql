-- create DB with HyperSQL
-- :web_portal/DB$ java -jar ../WebContent/WEB-INF/lib/hsqldb.jar --rcFile sqltool.rc web_portal web_portal.sql

CREATE TABLE ExpertAdministrator (Username		VARCHAR(16),
								  Password		VARCHAR(16),
								  FirstName		VARCHAR(64),
								  LastName		VARCHAR(64),
								  EmailAddress	VARCHAR(64),
								  LastLogin		DATETIME,
								  PRIMARY KEY	(Username));

CREATE TABLE Operator (Username		VARCHAR(16),
					   Password		VARCHAR(16),
					   FirstName	VARCHAR(64),
					   LastName		VARCHAR(64),
					   EmailAddress	VARCHAR(64),
					   LastLogin	DATETIME,
					   PRIMARY KEY	(Username));	

CREATE TABLE UserProfile (Username		VARCHAR(16),
						  Password		VARCHAR(16),
						  FirstName		VARCHAR(64),
						  LastName		VARCHAR(64),
						  EmailAddress	VARCHAR(64),
						  LastLogin		DATETIME,
						  Type			VARCHAR(64),
						  PRIMARY KEY	(Username));

CREATE TABLE Book (DateAdded DATETIME,
				   Author VARCHAR(64),
				   ISBN INTEGER,
				   Pages INTEGER,
				   PublicationDate DATETIME,
				   Publisher VARCHAR(64),
				   Review VARCHAR(10000),
				   Summary VARCHAR(10000),
				   Title VARCHAR(250),
				   PRIMARY KEY (Title));

SET SCHEMA PUBLIC;

INSERT INTO UserProfile ( Username   , Password , FirstName  , LastName           , EmailAddress    , LastLogin            , Type                    )
			VALUES		( 'niels'    , '1234'   , 'Niels'    , 'Joncheere'        , 'niels@soft'    , '2008-02-01 14:32:00', 'ExpertAdministrator'   );
INSERT INTO UserProfile ( Username   , Password , FirstName  , LastName           , EmailAddress    , LastLogin            , Type                    )
			VALUES		( 'ragnhild' , '5678'   , 'Ragnhild' , 'Van Der Straeten' , 'ragnhild@soft' , '2008-02-01 14:32:00', 'Operator'              );
INSERT INTO UserProfile ( Username   , Password , FirstName  , LastName           , EmailAddress    , LastLogin            , Type                    )
			VALUES		( 'bruno'    , '9012'   , 'Bruno'    , 'De Fraine'        , 'bruno@soft'    , '2008-02-01 14:32:00', 'ExpensiveSubscription' );
INSERT INTO UserProfile ( Username   , Password , FirstName  , LastName           , EmailAddress    , LastLogin            , Type                    )
			VALUES		( 'test'     , 'test'   , 'test'    , 'test'        	  , 'test@soft'     , '2008-02-01 14:32:00', 'FreeSubscription'      );

INSERT INTO Book	(DateAdded,				Author,		ISBN,	Pages, 	PublicationDate, 		Publisher, Review,	Summary, 	Title)
			VALUES	('2008-02-01 14:32:00',	'Mrs Test',	0,		0, 		'2008-02-01 14:32:00',	'test',		'test', 'test', 	'test' );

			
COMMIT;
