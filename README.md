To-Do Sample Spring, JPA and Wicket Application
===============================================

This is a sample application meant to demonstrate the structure of a
web-based application that leverages the [Spring Framework][1], the
[Java Persistence API (JPA)][2] and [Wicket][3]. To support integration
testing, PhantomJS is used. To this end, it does it's best to stay simple
while still demonstrating some best practices.

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

This project uses Selenium with WebDriver (via GhostDriver) over PhantomJS to
perform integration testing. At this time there is a known bug in GhostDriver
that prevents switching from frame to frame, this will cause our tests to
fail. This change will be integrated into PhantomJS but in the meantime, you will
need to compile your own version. Detailed information on how to do so are
provided on the GhostDriver project page.

  [GhostDriver Project](https://github.com/detro/ghostdriver)

Phantomjs has clear installation instructions on their website as well as
documentation on how to compile from source.

  [Phantomjs Installation](http://phantomjs.org/download.html)

To run the integration tests, invoke maven as follows:

    mvn verify

The "verify" target will then run both the regular suite of unit tests and the
integration tests. The output of the integration tests can be found in the
"log.xml" file.

[1]: http://www.springframework.org
[2]: http://en.wikipedia.org/wiki/Java_Peristence_API
[3]: http://wicket.apache.org
