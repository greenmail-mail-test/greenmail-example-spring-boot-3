# Intro

This example demonstrates using GreenMail 2.0.x using Spring Boot 3.

Note that in order to avoid a Jakarta Activation implementation conflict,
you'll have to exclude the with GreenMail transitively, via com.sun.mail:jakarta.mail:2.0x provided 
version of `com.sun.activation:jakarta.activation`.

See https://github.com/greenmail-mail-test/greenmail/issues/512 for details.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.1/maven-plugin/reference/html/#build-image)

