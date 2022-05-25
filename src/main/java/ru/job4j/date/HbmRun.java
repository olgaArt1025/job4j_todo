package ru.job4j.date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Item;
import ru.job4j.model.User;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

       /*    Item item = Item.of("Полить цветы", "В комнатах + балкон", new Date(),
                    false, new User(1, "Olga", "123"));
            session.save(item);

            session.getTransaction().commit();
            session.close();

        */

            List<Item> items = session.createQuery("from Item ").list();
            for (Item it : items) {
                System.out.println(it);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
