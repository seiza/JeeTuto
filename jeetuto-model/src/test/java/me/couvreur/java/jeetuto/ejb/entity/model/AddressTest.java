package me.couvreur.java.jeetuto.ejb.entity.model;

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
public class AddressTest {

    @Test
    public void constructorsTest() {
        String geneva = "Geneva";
        {
            Address address = new Address();
            assertEquals("", address.getCity());

            address.setCity(geneva);
            assertEquals(geneva, address.getCity());
        }
        {
            assertEquals("", new Address("").getCity());
            assertEquals(geneva, new Address(geneva).getCity());
        }
    }

    @Test
    public void findAddressWithCityTest() {
        String geneva = "Geneva";
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MaBaseDeTestPU");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        {
            Address address = new Address();
            address.setCity(geneva);
            manager.persist(address);
        }
        manager.flush();
        transaction.commit();

        {
            Address address = manager.find(Address.class, 1);
            assertNotNull(address);
            assertEquals(geneva, address.getCity());
        }
        manager.close();
        factory.close();
    }
}
