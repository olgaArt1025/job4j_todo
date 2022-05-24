package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Category;
import ru.job4j.model.Item;
import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class HbmRun {
    public static void main(String[] args) {
        List<Category> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

          Category one = Category.of("Развлечения");
            session.save(one);

            Category two = Category.of("Покупки");
            session.save(two);

            Category three = Category.of("Работа");
            session.save(three);

            Category four = Category.of("Домашние дела");
            session.save(four);

            Item first = Item.of("Сходите в кино", "Анчартед:На картах не значится",   new Date(), false, new User(1, "Olga", "123"));
            first.getCategories().add(one);

            session.persist(first);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static <T> List<T> findAll(Class<T> cl, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<T> list = session.createQuery("from " + cl.getName(), cl).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
