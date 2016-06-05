package com.mk;


import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

@Component
public class Receiver {

    /**
     * Get a copy of the application context
     */
    @Autowired
    ConfigurableApplicationContext context;

    
    
    @JmsListener(destination = "mailbox-destination", containerFactory = "myJmsContainerFactory")
    public void receiveMessage1(String message) throws InterruptedException {
    	Thread.sleep(500);
        System.out.println("$$$$$  @JmsListener Received11111 <" + message + ">" + Thread.currentThread().getName());
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }
    
    
    /**
     * When you receive a message, print it out, then shut down the application.
     * Finally, clean up any ActiveMQ server stuff.
     * @throws InterruptedException 
     */
    @JmsListener(destination = "mailbox-destination", containerFactory = "myJmsContainerFactory")
    public void receiveMessage2(String message) throws InterruptedException {
    	Thread.sleep(500);
        System.out.println("$$$$$ @JmsListener Received22222  <" + message + ">" + Thread.currentThread().getName());
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }
 }
