package me.couvreur.java.jeetuto.client.mdb;

/**
 * User: jacques
 * Date: 17/04/12
 * Time: 12:16
 */
public class ClientHelloMDBReceiver {
    public static void main(String[] args) {
/*
        try {
            Context context = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
            Destination destination = (Destination) context.lookup("/topic/DemandeFormation");

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
        }
*/
    }
}
