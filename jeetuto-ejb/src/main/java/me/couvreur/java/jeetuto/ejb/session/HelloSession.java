package me.couvreur.java.jeetuto.ejb.session;

import javax.ejb.Remote;

/**
 * User: jacques
 * Date: 15.04.12
 * Time: 16:55
 */
@Remote
public interface HelloSession {
    public String sayHello(String name);
}
