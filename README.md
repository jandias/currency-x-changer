# currency-x-changer
Spring Web App to query betweeen currency exchange rates


### How to build jar
Build complete project, run tests, pack .war file and install on local Maven:
* _mvn clean install_
 
.war archive is also made available under _target_ directory.

### How to execute application
Run embeeded server, deploy application and start it:
* _mvn tomcat7:run_

Client interface available at: http://localhost:8080

### Usage

* Register your acount
* Use on the fly queries
* Save current query
* Consult last 10 queries made with your acount

### Notes

* Persistence is available only with embeeded DB for now.
* There is a know bug in .js money script wich causes first loaded currency rates that cause 1 EUR = 1 USD
