package me.couvreur.java.jeetuto.ejb.ws;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * User: jacques
 * Date: 18.04.12
 * Time: 19:28
 */
@Stateless
@WebService(
        endpointInterface = "me.couvreur.java.jeetuto.ejb.ws.AdditionWebService",
        serviceName="AdditionWebService")
@SOAPBinding(style = javax.jws.soap.SOAPBinding.Style.DOCUMENT)
public class AdditionWebServiceBean implements AdditionWebService {

    @Override
    @WebMethod
    public String add(String left, String right) {
        return left + right;
    }

}
