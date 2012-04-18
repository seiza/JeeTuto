package me.couvreur.java.jeetuto.client.session;

import me.couvreur.java.jeetuto.ejb.session.HelloSession;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * User: jacques
 * Date: 15.04.12
 * Time: 17:29
 */
public class ClientHelloSessionStateless {

    public static void main(String[] args) {
        try {
            Context context = new InitialContext();

            // @see mappedName attribute of @Stateless annotation of HelloSessionStatelessBean class
            Object bean = context.lookup("ejb/HelloSessionStateless");

            System.out.println("************************ INFORMATION SUR LE BEAN OBTENU DU LOOKUP SUR LE CONTEXT ************************");
            System.out.println("************************          (pour s'assurer de la bonne connexion)         ************************");
            System.out.println(bean.toString());
            System.out.println("*********************************************************************************************************");
            HelloSession beanRemote = (HelloSession)bean;
            System.out.println(beanRemote.sayHello("JACK"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

}
