package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Item;

import java.util.Date;
import java.util.List;
import java.util.function.Function;


@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }


    public Item add(Item item) {
        return this.tx(
                session -> {
                    item.setCreated(new Date());
                    item.setDone(false);
                    session.save(item);
                    return item;
                }
        );
    }

    public void update(int id, Item item) {
        this.tx(
                session -> {
        item.setId(id);
        item.setCreated(new Date());
        session.update(item);
                    return null;
                }
        );
    }

    public void delete(int id) {
        this.tx(
                session -> {
                    Item item = new Item();
                    item.setId(id);
                    session.delete(item);
                    return null;
                }
        );
    }

    public List<Item> findAll() {
        return this.tx(
                session -> {
                    final List result = session.createQuery("from ru.job4j.model.Item").list();
                    return result;
                }
        );
    }

    public List<Item> findByName(String key) {
        return this.tx(
                session -> {
                    final List result = session.createQuery("from ru.job4j.model.Item  where name = :paramName")
                            .setParameter("paramName", key).list();
                    return result;
                }
        );
    }

    public Item findById(int id) {
        return this.tx(
                session -> {
                    final Item result = session.get(Item.class, id);
                    return result;
                }
        );
    }

    public List<Item> condition(boolean done) {
        return this.tx(
                session -> {
                    final List result = session.createQuery("from ru.job4j.model.Item  where done = :paramDone")
                            .setParameter("paramDone", done).list();
                    return result;
                }
        );
    }

    public void completed(int id) {
        this.tx(
                session -> {
        session.createQuery("update ru.job4j.model.Item i set i.done = true where i.id = :id").
                setParameter("id", id).executeUpdate();
                    return null;
                }
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
