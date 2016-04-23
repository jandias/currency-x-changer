# Currency-x-changer
Web application to convert between money currency values


### How to install

1. Install Java JDK 8 and Maven 3.

2. Build complete project, run tests, pack `.war` file and install on local Maven:

        mvn clean install
 
`.war` _archive is also made available under_ `target` _directory_.


### How to execute application

1. To run with the provided embedded server and the default Database configuration, deploy application and start it:

        mvn tomcat7:run
    _Client interface available at_: http://localhost:8080
    * If using the default Database, a PostgreSQL 9 instance will be used. URI, user and password should be set as system environment variables: `JDBC_DATABASE_URL`, `JDBC_DATABASE_USERNAME` and `JDBC_DATABASE_PASSWORD`
    * If you want to use an in-memory data store (lost each time the application is restarted) an embedded instance of HSQLDB is provided. Run the application as:
    
        mvn tomcat7:run -Ddb.code=hsql 


3. **Optional**: You can also use properties to define additional application server/Tomcat parameters. For example:

        mvn tomcat7:run -Ddb.code=hsql -Dmaven.tomcat.port=80
  

4. **Optional**: Instead of running the application with Maven, you can also deploy the `.war` archive to an installed application server instance.


### Application usage

* Register your account
* Use on the fly queries
* Save current query
* Consult last 10 queries made with your account


### Maintenance REST API

* Administrators can use "/account" endpoint for existing user account information.


### Notes
  
* There is a know bug in .js money script that causes first loaded currency rates that cause 1 EUR = 1 USD
