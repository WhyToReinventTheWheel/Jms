package com.mk.topic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.mk.topic.HelloWorldConsumer.QueueMode;
import com.mk.util.Config;

class Listener  implements  MessageListener{
	int count=0;
	
	@Override
	public void onMessage(Message message)  {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		String text = null;
		  if (message instanceof TextMessage) {
		      TextMessage textMessage = (TextMessage) message;
		    
			try {
				text = textMessage.getText();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		      System.out.println("***** Async Received: ThreadName() : " +Thread.currentThread().getName() +"--->" + text +"count : "+count);
		  } else {
		      System.out.println("Received: " + message);
		  }
		  count++;
	}
}


class HelloWorldConsumer implements Runnable, ExceptionListener {
	enum QueueMode{
		SYNC,
		ASYC
	}
	
	private Listener listener=new Listener();
	private QueueMode queueMode;
	private ActiveMQConnectionFactory connectionFactory=null;
	private Connection connection=null;
	private Session session=null;
	private MessageConsumer consumer=null;
	
	HelloWorldConsumer(QueueMode mode) throws JMSException{
		queueMode=mode;
		 // Create a ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory(Config.QUEUE_URL);

        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.start();
       
        connection.setExceptionListener(this);

        // Create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createTopic(Config.QUEUE_TOPIC);

        // Create a MessageConsumer from the Session to the Topic or Queue
        consumer = session.createConsumer(destination);
	}
	
	public void close() throws JMSException{
		   consumer.close();
	       session.close();
	       connection.close();
   }
		
	
	public void run() {
		
		try {
			if(queueMode == QueueMode.ASYC){
				consumer.setMessageListener(listener);
			}else{
				int count=0;
				while(count <10000){
					Thread.sleep(2000);
				Message message = consumer.receive(100);
				
				if (message instanceof TextMessage) {
				    TextMessage textMessage = (TextMessage) message;
				    String text = textMessage.getText();
				     System.out.println("Sync Received: ThreadName() : " +Thread.currentThread().getName() +"--->" + text +"count : "+count);
				} else {
				    System.out.println("Received: " + message);
				    }
				    count++;
				}
			}
		    System.out.println("<=================== "+Thread.currentThread().getName() + " Close =======================>" );
		} catch (Exception e) {
		    System.out.println("Caught: " + e);
		    e.printStackTrace();
		}
		 
	}
   
	public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }
}

public class TopicReciever {
	public static void main(String[] args) throws InterruptedException, JMSException {
		ExecutorService executor = Executors.newFixedThreadPool(10);//creating a pool of 20 threads  
		for (int i = 0; i < 1; i++) {  
			executor.execute(new HelloWorldConsumer(QueueMode.SYNC));
			executor.execute(new HelloWorldConsumer(QueueMode.ASYC));
		}
		
		executor.shutdown();
		executor.awaitTermination(1,TimeUnit.MINUTES );
	}
}
