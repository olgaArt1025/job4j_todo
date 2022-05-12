package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Item;
import ru.job4j.store.ItemStore;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Service
public class ItemService {
    private final ItemStore store;

    public ItemService(ItemStore store) {
        this.store = store;
    }

    public Collection<Item> findAll() {
        return store.findAll();
    }

    public Item add(Item item) {
        return store.add(item);
    }

    public void update(int id, Item item) {
       store.update(id, item);
    }

    public Item findById(int id) {
        return store.findById(id);
    }

    public void  delete(int id) {
        store.delete(id);
    }

    public List<Item> findByName(String key) {
        return store.findByName(key);
    }

    public Collection<Item> completed() {
        return store.condition(true);
    }

    public Collection<Item> fresh() {
        return store.condition(false);
    }

    public void completed(int id) {
        store.completed(id);
    }
}
