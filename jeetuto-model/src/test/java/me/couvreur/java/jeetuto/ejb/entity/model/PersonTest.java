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
    public void addAddressTest() {
        String jacques = "Jacques";
        String geneva = "Geneva";

        Person person = new Person();
        person.setName(jacques);
        person.addAddress(new Address(geneva));

        assertEquals(jacques, person.getName());
        assertEquals(1, person.getAdresses().size());
        assertEquals(geneva, person.getAdresses().iterator().next().getCity());
    }

    @Test
    public void findPersonWithNameTest() {
        String jacques = "Jacques";
        String geneva = "Geneva";

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MaBaseDeTestPU");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        {
            Person person = new Person();
            person.setName(jacques);
            person.addAddress(new Address(geneva));
            manager.persist(person);
        }
        manager.flush();
        transaction.commit();

        {
            Person person = manager.find(Person.class, 1);
            assertNotNull(person);
            assertEquals(jacques, person.getName());

            assertEquals(1, person.getAdresses().size());
            assertEquals(geneva, person.getAdresses().iterator().next().getCity());
        }
        manager.close();
        factory.close();
    }
}
