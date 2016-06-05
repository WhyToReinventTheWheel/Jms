package com.mk;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.FileSystemUtils;

@SpringBootApplication
@EnableJms
public class SpringJmsTemplateApplication {

	@Bean // Strictly speaking this bean is not necessary as boot creates a default
    JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
	
	public static void main(String[] args) throws InterruptedException, JMSException {
		
		 // Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));

        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(SpringJmsTemplateApplication.class, args);

        
        // Send a message
        MessageCreator messageCreator = new MessageCreator() {
        	int messageCounter=0;	
            @Override
            public Message createMessage(Session session) throws JMSException {
            	messageCounter++;
                return session.createTextMessage("ping!--> " + messageCounter);
            }
        };
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        
        ExecutorService executor = Executors.newFixedThreadPool(5);  
		for (int i = 0; i < 5; i++) {  
			executor.execute(new CustomReceiver(jmsTemplate));
		}
		executor.shutdown();
        
        
        int i=1;
        while(i<1000){
        	Thread.sleep(500);
			System.out.println("Sending a new message." + i);
			jmsTemplate.send("mailbox-destination", messageCreator);
			i++;
        }
        
        System.out.println("***************************** Sending Message Complete *************************");
        executor.awaitTermination(10,TimeUnit.MINUTES );
	}
}
