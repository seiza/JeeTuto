package me.couvreur.java.jeetuto.ejb.mdb;

import me.couvreur.java.jeetuto.ejb.mdb.business.HelloMDB;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * User: jacques
 * Date: 17/04/12
 */
@MessageDriven(
        name = "HelloMessageListenerMDB",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/HelloQueue") })
                // "queue/" suffixe is mandatory for JBoss
                // http://www.coderanch.com/t/319834/EJB-JEE/java/correct-way-create-MDB
//@MessageDriven(mappedName = "jms/topic/HelloMessageListenerMDB")
public class HelloMessageListenerMDB implements MessageListener {

    @Resource
    private MessageDrivenContext context;

    private void handleBusinessLogic(HelloMDB helloMDB) {
        System.out.println("HelloMDB message handled with name = " + helloMDB.getName());
    }

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;

            System.out.println(objectMessage.getObject());

            HelloMDB helloMDB = (HelloMDB) objectMessage.getObject();
            handleBusinessLogic(helloMDB);
        } catch (/*JMS*/Exception e) {
            e.printStackTrace();
            context.setRollbackOnly();
        }
    }
}
