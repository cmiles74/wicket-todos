To-Do Sample Spring, JPA and Wicket Application
===============================================

This is a sample application meant to demonstrate the structure of a
web-based application that leverages the [Spring Framework][1], the
[Java Persistence API (JPA)][2] and [Wicket][3]. To this end, it does
it's best to stay simple while still demonstrating some best
practices.

## Data Source Configuration

When running tests under Maven, the test will look to a properties
file called "test.properties" in the classpath. A suitable properties
file is supplied with this project, it will setup an in-memory Derby
database. It would be rare for you to need to configure another
database but you can easily do so by changing the values in this
properties file.

When running under Maven in development mode (`mvn jetty:run`), the
application will for a properties file called "default.properties" in
the classpath. Again, a suitable file has been provided that will use
a Derby database that's stored on disk in the "data" directory at the
root of the project.

Lastly, when running in production you may wish to configure the data
source through JNDI. The application will look for a JNDI object under
the key "/jdbc/todosDataSource". If there is no object in JNDI under
that key, it will fallback to the values in the "default.properties"
file.

## Integration Testing

This project uses the Selenium library to do some simple browser
integration testing. Presently these test utilize the Chrome browser
through the Chrome WebDriver for Selenium. In order to run these tests
you must first have both the Chrome web browser and the Chrome
WebDriver installed.

The Chrome web browser can be downloaded from Google.

  [Google Chrome][http://www.google.com/chrome]

The Chrome WebDriver is available on Google Code.

  [Google Chrome Selenium WebDriver][http://code.gooogle.com/p/selenium/wiki/ChromeDriver]

The Chrome WebDriver should be smart enough to find the location of
the Chrome browser binary.

To run the integration tests, invoke maven as follows:

    mvn verify

The "verify" target will run both the regular suite of unit tests and
the integration tests. The integration tests will be run under the
"failsafe" Maven test runner; this will ensure that Selenium is
correctly shutdown in case of failure.

[1]: http://www.springframework.org
[2]: http://en.wikipedia.org/wiki/Java_Peristence_API
[3]: http://wicket.apache.org
