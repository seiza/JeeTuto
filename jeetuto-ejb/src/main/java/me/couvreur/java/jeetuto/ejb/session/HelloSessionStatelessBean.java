package me.couvreur.java.jeetuto.ejb.session;

import javax.ejb.Stateless;

/**
 * User: jacques
 * Date: 15.04.12
 * Time: 16:56
 */
@Stateless(mappedName = "ejb/HelloSessionStateless")
public class HelloSessionStatelessBean implements HelloSession {

    public HelloSessionStatelessBean() {
    }

    public String sayHello(String name) {
        return "Cher " + name + ", ce message provient de l'EJB Session Stateless cote serveur : c'est une bonne nouvelle !";
    }

}
