package me.couvreur.java.jeetuto.ejb;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: jacques
 * Date: 15.04.12
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
@Stateless(mappedName = "ejb/HelloEJB")
public class HelloEJBBean implements HelloEJB {

    public HelloEJBBean() {
    }

    public String sayHello(String name) {
        return "Vous etes dans l'EJB cote serveur, cher " + name + "!";
    }

}
