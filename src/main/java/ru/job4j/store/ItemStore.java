package ru.job4j.store;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }
}
