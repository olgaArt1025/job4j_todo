package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Item;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }


    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        item.setCreated(new Date());
        item.setDone(false);
        session.save(item);
        session.getTransaction().commit();
        return item;
    }

    public boolean update(int id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        item.setId(id);
        item.setCreated(new Date());
        session.update(item);
        session.getTransaction().commit();
        session.close();
        return true;
    }


    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item();
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
        return true;
    }


    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }


    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.model.Item  where name = :paramName")
                .setParameter("paramName", key).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

   public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Item> condition(boolean done) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.model.Item  where done = :paramDone")
                .setParameter("paramDone", done).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void completed(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("update ru.job4j.model.Item i set i.done = true where i.id = :id").
        setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
