package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Category;
import ru.job4j.store.CategoryStore;

import java.util.Collection;

@Service
public class CategoryService {
    private final CategoryStore store;

    public CategoryService(CategoryStore store) {
        this.store = store;
    }

    public Collection<Category> findAll() {
        return store.findAll();
    }
}
