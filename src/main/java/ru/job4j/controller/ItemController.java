package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Item;
import ru.job4j.model.User;
import ru.job4j.service.CategoryService;
import ru.job4j.service.ItemService;

import javax.servlet.http.HttpSession;

@Controller
public class ItemController {
    private final ItemService service;
    private final CategoryService categoryService;


    public ItemController(ItemService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping("/items")
    public String items(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", service.findAll(user));
        return "items";
    }

    @GetMapping("/formAddItem")
    public String formAddItem(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "addItem";
    }


    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, HttpSession session) {
        item.setUser((User) session.getAttribute("user"));
        service.add(item);
        return "redirect:/items";
    }

    @GetMapping("/formUpdateItem/{itemId}")
    public String formUpdateItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", service.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item, HttpSession session) {
        item.setUser((User) session.getAttribute("user"));
        service.update(item.getId(), item);
        return "redirect:/items";
    }

    @GetMapping("/completed")
    public String completed(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", service.completed(user));
        return "items";
    }

    @GetMapping("/fresh")
    public String fresh(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", service.fresh(user));
        return "items";
    }

    @GetMapping("/itemDetails/{itemId}")
    public String itemDetails(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", service.findById(id));
        return "itemDetails";
    }

    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable("itemId") int id) {
         service.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/completed/{itemId}")
    public String completed(@PathVariable("itemId") int id) {
        service.completed(id);
        return "redirect:/items";
    }
}
