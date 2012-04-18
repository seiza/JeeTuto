package me.couvreur.java.jeetuto.ejb.ws;

import javax.ejb.Remote;
import javax.jws.WebService;

/**
 * User: jacques
 * Date: 18.04.12
 * Time: 19:26
 */
@Remote
@WebService
public interface AdditionWebService {

    public String add(String left, String right);

}
