package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class UserDBStore {

    private final SessionFactory sf;

    public UserDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<User> findAll() {
        return this.tx(
                session -> {
                    final List result = session.createQuery("from User").list();
                    return result;
                }
        );
    }

    public Optional<User> add(User user) {
        return this.tx(
                session -> {
                    session.save(user);
                  return user.getId() == 0 ? Optional.empty() : Optional.of(user);
                }
        );
    }


    public void update(int id, User user) {
        this.tx(
                session -> {
                    user.setId(id);
                    session.update(user);
                    return null;
                }
        );
    }

    public User findById(int id) {
        return this.tx(
                session -> {
                    final User result = session.get(User.class, id);
                    return result;
                }
        );
    }

    public Optional<User> findUserByNameAndPwd(String name, String password) {
        return this.tx(
                session -> {
                    Query query = session.createQuery("from User u where u.name = :newName and u.password = :newPassword");
                    query.setParameter("newName", name);
                    query.setParameter("newPassword", password);
                    return query.uniqueResultOptional();
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
