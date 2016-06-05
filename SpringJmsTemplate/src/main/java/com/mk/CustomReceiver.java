package com.mk;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;



public class CustomReceiver implements Runnable{
	private JmsTemplate jmsTemplate=null;

	CustomReceiver(JmsTemplate jmsTemplate){
		this.jmsTemplate=jmsTemplate;
	}

	public void run() {
		int count=0;
		while(count <10000){
			try {
				Thread.sleep(1000);
			//JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
			Message message=(Message) jmsTemplate.receive("mailbox-destination");
			
			if (message instanceof TextMessage) {
			    TextMessage textMessage = (TextMessage) message;
			    String text = textMessage.getText();
			     System.out.println("Sync Received: ThreadName() : " +Thread.currentThread().getName() +"--->" + text +"count : "+count);
			} else {
			    System.out.println("Received: " + message);
			    }
			    count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
