package me.couvreur.java.jeetuto.ejb;

import javax.ejb.Remote;

/**
 * Created with IntelliJ IDEA.
 * User: jacques
 * Date: 15.04.12
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
@Remote
public interface HelloEJB {
    public String sayHello(String name);
}
