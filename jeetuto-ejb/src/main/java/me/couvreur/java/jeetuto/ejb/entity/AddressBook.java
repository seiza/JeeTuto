package me.couvreur.java.jeetuto.ejb.entity;

import me.couvreur.java.jeetuto.ejb.entity.model.Person;

import javax.ejb.Remote;
import java.util.List;

/**
 * User: jacques
 * Date: 18.04.12
 * Time: 14:02
 */
@Remote
public interface AddressBook {

    public void addPerson(Person person);
    public Person findPersonWithId(String id);
    public List<Person> findPersonsWithName(String name);
    public List<Person> getPersons();

}
