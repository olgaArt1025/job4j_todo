package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Category;

import java.util.List;
import java.util.function.Function;

@Repository
public class CategoryStore {
    private final SessionFactory sf;

    public CategoryStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Category> findAll() {
        return this.tx(
                session -> {
                    final List result = session.createQuery("from Category").list();
                    return result;
                }
        );
    }

    public Category findById(int id) {
        return this.tx(
                session -> {
                    final Category result = session.get(Category.class, id);
                    return result;
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
