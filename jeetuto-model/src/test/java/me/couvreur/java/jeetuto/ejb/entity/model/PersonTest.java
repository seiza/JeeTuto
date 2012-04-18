package me.couvreur.java.jeetuto.ejb.entity.model;

import me.couvreur.java.jeetuto.ejb.entity.model.Person;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: jacques
 * Date: 17.04.12
 * Time: 18:46
 */
public class PersonTest {

    @Test
    public void myTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MaBaseDeTestPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();
        Person p = new Person();
        p.setName("Jacques");
        em.persist(p);
        em.flush();
        et.commit();

        Person person = em.find(Person.class, 1);
        assertNotNull(person);

        assertEquals("Jacques", person.getName());

        em.close();
        emf.close();
    }
}
