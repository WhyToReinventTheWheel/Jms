package com.mk.queqe;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.mk.util.Config;


public class Sender {
	
	   public static void main(String[] args) throws Exception {
		   
		   try {
	           // Create a ConnectionFactory
	           ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Config.QUEUE_URL);

	           // Create a Connection
	           Connection connection = connectionFactory.createConnection();
	           connection.start();

	           // Create a Session
	           Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	           // Create the destination (Topic or Queue)
	           Destination destination = session.createQueue(Config.QUEUE_NAME);

	           // Create a MessageProducer from the Session to the Topic or Queue
	           MessageProducer producer = session.createProducer(destination);
	           producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

	           
	           int count=0;
	           while(count < 1000){
	        	   // Create a messages
		           String text = "Hello world! " + " :-->"  +count +"<--:";
		           TextMessage message = session.createTextMessage(text);
	        	   
		           // Tell the producer to send the message
		           System.out.println("Sent message: Count : "+ count );
		           producer.send(message);
		           
		           //Thread.sleep(500);
		           count++;
	           }   
		       // Clean up
	           session.close();
	           connection.close();
	       }
	       catch (Exception e) {
	           System.out.println("Caught: " + e);
	           e.printStackTrace();
	       }
	    }
}
