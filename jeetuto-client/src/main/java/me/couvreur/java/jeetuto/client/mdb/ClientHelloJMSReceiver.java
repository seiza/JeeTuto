package me.couvreur.java.jeetuto.client.mdb;

import me.couvreur.java.jeetuto.ejb.mdb.business.HelloMDB;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.Properties;


/**
 * User: jacques
 * Date: 17/04/12
 * Time: 12:16
 */
public class ClientHelloJMSReceiver {
    public static void main(String[] args) {

        try {
            Properties jndiProperties = new Properties();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "");
            // jndiProperties.put(Context.PROVIDER_URL, “tcp://localhost:61616″);

            Context context = new InitialContext(jndiProperties);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("ConnectionFactory");

            QueueConnection connection = connectionFactory.createQueueConnection();

            QueueSession session = connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);

            Queue queue = (Queue) context.lookup("queue/HelloQueue");

            QueueReceiver consumer = session.createReceiver(queue);

            ClientHelloMDBReceiverListener listener = new ClientHelloMDBReceiverListener();
            consumer.setMessageListener(listener);

            connection.start();

            System.out.println("Connected. Waiting for messages ...");
            try {
                Thread.sleep(30000);
            }
            catch (java.lang.InterruptedException ie) {
                ie.printStackTrace(System.err);
            }
            System.out.println("Closing connection.");
            connection.close();

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    private static class ClientHelloMDBReceiverListener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            try {
                ObjectMessage objectMessage = (ObjectMessage) message;
                Serializable serializable = objectMessage.getObject();
                HelloMDB helloMDB = (HelloMDB) serializable;
                System.out.println("******* Dans le JMS Consumer/Listener vous venez de recevoir un HelloMDB nommé : " + helloMDB.getName());

                System.exit(0);

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
