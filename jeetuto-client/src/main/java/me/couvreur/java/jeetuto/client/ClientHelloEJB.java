package me.couvreur.java.jeetuto.client;

import me.couvreur.java.jeetuto.ejb.HelloEJB;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created with IntelliJ IDEA.
 * User: jacques
 * Date: 15.04.12
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
public class ClientHelloEJB {

    public static void main(String[] args) {
        try {
            Context context = new InitialContext();
            Object bean = context.lookup("jeetuto-ear-0.1/jeetuto-ejb-0.1.jar/HelloEJBBean!remote");
            System.out.println("*** INFORMATION SUR LE BEAN OBTENU DU LOOKUP SUR LE CONTEXT (pour s'assurer de la bonne connexion : ***");
            System.out.println(bean.toString());
            System.out.println("*******************************************************************************************************");
            HelloEJB beanRemote = (HelloEJB)bean;
            System.out.println(beanRemote.sayHello("ClientHelloEJB JACK"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

}
