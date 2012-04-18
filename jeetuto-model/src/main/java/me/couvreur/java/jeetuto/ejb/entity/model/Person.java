package me.couvreur.java.jeetuto.ejb.entity.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: jacques
 * Date: 17.04.12
 * Time: 18:50
 */
@Entity
@Table(name="PERSON")
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
