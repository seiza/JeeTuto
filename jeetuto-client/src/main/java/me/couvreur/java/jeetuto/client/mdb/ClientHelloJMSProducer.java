package me.couvreur.java.jeetuto.client.mdb;

import me.couvreur.java.jeetuto.ejb.mdb.business.HelloMDB;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * User: jacques
 * Date: 17/04/12
 * Time: 12:00
 */
public class ClientHelloJMSProducer {

    public static void main(String[] args) {
        try {
            Context context = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
            Destination destination = (Destination) context.lookup("queue/HelloQueue");

            Connection connection = connectionFactory.createConnection("guest", "guest");
            connection.setClientID("clientID");

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(destination);

            connection.start();

            HelloMDB helloMDB = new HelloMDB();
            helloMDB.setName("Bernard !!");
            ObjectMessage objectMessage = session.createObjectMessage(helloMDB);
            messageProducer.send(objectMessage);

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
