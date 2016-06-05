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
    - "<dependency><groupId>org.apache.activemq</groupId><artifactId>activemq-all</artifactId><version>5.10.0</version></dependency>"
