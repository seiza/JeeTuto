package me.couvreur.java.jeetuto.ejb.entity.model;

import javax.persistence.*;

/**
 * User: jacques
 * Date: 18.04.12
 * Time: 13:12
 */
@Entity
@Table(name="ADDRESS")
public class Address {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "CITY")
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
