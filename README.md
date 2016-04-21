# Currency-x-changer
Web application to convert between money currency values


### How to install

1. Install Java JDK 8 and Maven 3.

2. Build complete project, run tests, pack `.war` file and install on local Maven:

        mvn clean install
 
`.war` _archive is also made available under_ `target` _directory_.


### How to execute application

1. To run with the provided embedded server, deploy application and start it:

        mvn tomcat7:run
    _Client interface available at_: http://localhost:8080

2. **Optional**: use Tomcat Maven plugin options
  

3. **Or**: deploy the `.war` archive to a installed application server.


### Application usage

* Register your account
* Use on the fly queries
* Save current query
* Consult last 10 queries made with your account


### Maintenance REST API

* Administrators can use "/account" endpoint for existing user account information.


### Notes

* Persistence in production is by default enabled for PostgreSQL 9.
  URI, user and password should be set as system environment variables:
  `JDBC_DATABASE_URL`, `JDBC_DATABASE_USERNAME` and `JDBC_DATABASE_PASSWORD`
  
* There is a know bug in .js money script that causes first loaded currency rates that cause 1 EUR = 1 USD
