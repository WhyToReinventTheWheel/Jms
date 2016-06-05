------------
  Setup 
-----------
  * download : http://activemq.apache.org/download.html
  * go to ~\apache-activemq-5.13.3\bin\win64\activemq.bat
  * Check the logs findout  'Listening for connection at' it will show something like tcp://DESKTOP-21J3C8J:61616 like ,this will work for connection 
  * url for connection -- failover:tcp://host:port
  * http://localhost:8161/admin/
    - username-admin
    - password-admin
  * Add dependency in project 
    - ~\apache-activemq-5.13.3\activemq-all-5.13.3.jar add this in build path
    - OR
    - Maven dependency : http://activemq.apache.org/download.html
  * Sample Java: http://activemq.apache.org/hello-world.html
  * Sample Java example with download:http://activemq.apache.org/web-samples.html
  * http://activemq.apache.org/web-samples.html
  
--------------------
Spring JMS Template  
------------------
* Spring Boot-- https://spring.io/guides/gs/messaging-jms/
* Spring Boot-- https://github.com/spring-guides/gs-messaging-jms
* http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html
  - Edit application.properties src/main/resource if follwing properties are not present then spring broker will come in effect
    - spring.activemq.broker-url=tcp://DESKTOP-21J3C8J:61616
    - spring.activemq.user=admin
    - spring.activemq.password=admin
