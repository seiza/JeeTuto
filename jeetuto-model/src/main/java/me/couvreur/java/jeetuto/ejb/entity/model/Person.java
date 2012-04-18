package me.couvreur.java.jeetuto.ejb.entity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * User: jacques
 * Date: 17.04.12
 * Time: 18:50
 */
@Entity
@Table(name="PERSON")
public class Person implements Serializable {

    private Integer id;

    private String name;

    private Set<Address> adresses;

    public Person() {
        adresses = new HashSet<Address>();
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PERSON_ADDRESS",
            joinColumns = { @JoinColumn(name = "PERSON_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID") })
    public Set<Address> getAdresses() {
        return adresses;
    }

    public void setAdresses(Set<Address> adresses) {
        this.adresses = adresses;
    }

    public void addAddress(Address address) {
        adresses.add(address);
    }
}
