package me.couvreur.java.jeetuto.ejb.mdb.business;

import java.io.Serializable;

/**
 * User: jacques
 * Date: 17/04/12
 * Time: 10:44
 */
public class HelloMDB implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
