package org.example;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {


    public static void main(String[] args) throws Exception {
        final String CLIENTID = "testconsumer";
        String BROKER_URL;
        String uname;
        String pass;
        String queueName;
        ConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Destination destination;
        MessageConsumer consumer;

        // The name of the queue.
        queueName = "ProduceConsumeQ";
        // URL of the JMS server is required to create connection factory.
        BROKER_URL = "tcp://ip:port";
        uname = "username";
        pass = "password";
        connectionFactory = new ActiveMQConnectionFactory(uname, pass, BROKER_URL);
        // Getting JMS connection from the server and starting it
        connection = connectionFactory.createConnection();
        connection.setClientID(CLIENTID);
        connection.start();
        // Creating a non-transactional session to send/receive JMS message.
        session = connection.createSession(false, AUTO_ACKNOWLEDGE);
        // Destination represents here our queue "ProduceConsumeQ" on the JMS
        // server.
        // The queue will be created automatically on the JSM server if it is not already
        // created.
        destination = session.createQueue(queueName);
        // MessageProducer is used for sending (producing) messages to the queue.
        consumer = session.createConsumer(destination);


        String response;
        do {
            // Receive the message
            Message msg = consumer.receive();
            response = ((TextMessage) msg).getText();

            System.out.println("Received = " + response);

        } while (!response.equalsIgnoreCase("Quit"));


        try {
            consumer.close();
            session.close();
            connection.close();
        }
        catch(Exception e) {
            System.out.println("An exception has occured at connection closing time");
        }
    }
}
