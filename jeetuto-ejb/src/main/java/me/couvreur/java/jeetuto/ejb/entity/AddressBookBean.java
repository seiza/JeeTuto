package me.couvreur.java.jeetuto.ejb.entity;

import me.couvreur.java.jeetuto.ejb.entity.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * User: jacques
 * Date: 18.04.12
 * Time: 14:06
 */
public class AddressBookBean implements AddressBook {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addPerson(Person person) {
        entityManager.persist(person);
    }

    @Override
    public Person findPersonWithId(String id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> findPersonsWithName(String name) {
        Query query = entityManager.createQuery("SELECT p FROM Person p WHERE p.name = ?1");
        query.setParameter(1, name);
        return query.getResultList();
    }

    @Override
    public List<Person> getPersons() {
        // return entityManager.createQuery("SELECT p FROM Person p ORDER BY p.name").getResultList();
        return entityManager.createQuery("SELECT p FROM Person p").getResultList();
    }
}
