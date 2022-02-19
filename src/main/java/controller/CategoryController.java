package controller;

import model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.product.CategoryService;

import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public String showCategory(Model model) {
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("list", categories);
        return "/category/list";
    }

    @GetMapping("/create-category")
    public String showCreate() {
        return "/category/create";
    }

    @PostMapping("/create")
    public String create(Category category) {
        categoryService.save(category);
        return "redirect:/category";
    }

    @GetMapping("/edit-category/{id}")
    public String showEdit(@PathVariable Long id, Model model) {
        Optional<Category> optionalCategory = categoryService.findById(id);
        Category category = optionalCategory.get();
        model.addAttribute("edit", category);
        return "/category/edit";
    }

    @PostMapping("/edit")
    public String update(Category category) {
        categoryService.save(category);
        return "redirect:/category";
    }

    @GetMapping("/delete-category/{id}")
    public String showDelete(@PathVariable Long id, Model model) {
        Optional<Category> optionalCategory = categoryService.findById(id);
        Category category = optionalCategory.get();
        model.addAttribute("delete", category);
        return "/category/delete";
    }

    @PostMapping("/delete")
    public String remove(Category category) {
        categoryService.remove(category.getId());
        return "redirect:/category";
    }
}
