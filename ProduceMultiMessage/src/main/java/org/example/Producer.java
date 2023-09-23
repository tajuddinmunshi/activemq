package org.example;

import java.util.Scanner;
import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
public class Producer {



    public static void main(String[] args) throws Exception {

         final String CLIENTID = "testproducer";
         String BROKER_URL;
         String uname;
         String pass;
         String queueName;
         ConnectionFactory connectionFactory;
         Connection connection;
         Session session;
         Destination destination;
         MessageProducer producer;
        // The name of the queue.
         queueName = "ProduceConsumeQ";
        // URL of the JMS server is required to create connection factory.
        BROKER_URL = "tcp://IP:PORT";
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
        producer = session.createProducer(destination);

        Scanner input = new Scanner(System.in);
        String response;
        do {
            System.out.println("Enter message: ");
            response = input.nextLine();
            // Create a message object
            TextMessage msg = session.createTextMessage(response);

            // Send the message to the queue
            producer.send(msg);

        } while (!response.equalsIgnoreCase("Quit"));

        try {
            input.close();
            producer.close();
            session.close();
            connection.close();
        }
        catch(Exception e) {
            System.out.println("An exception occured at connection termibation time");
        }
    }
}

