package controller;

import model.Category;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.product.CategoryService;
import service.product.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("category")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/sort")
    public String showSort(Model model, String search) {
        Iterable<Product> products;
        if (search == null) {
            products = productService.findAllByOrderByPriceDesc();

        } else {
            products = productService.findByName(search);
        }
        model.addAttribute("list", products);
        return "/product/list";
    }

    @GetMapping("/sort2")
    public String showSort2(Model model, String search) {
        Iterable<Product> products;
        if (search == null) {
            products = productService.findAllByOrderByPriceAsc();

        } else {
            products = productService.findByName(search);
        }
        model.addAttribute("list", products);
        return "/product/list";
    }

    @GetMapping("")
    public String listProducts(Model model, String search) {
        Iterable<Product> productList;
        if (search == null) {
            productList = productService.findAll();
        } else {
            productList = productService.findByName(search);
        }
        model.addAttribute("list", productList);
        return "/product/list";
    }

    @GetMapping("/create-product")
    public String showFormCreate() {
        return "/product/create";
    }

    @PostMapping("/create")
    public String create(Product product, @RequestParam MultipartFile image1) throws IOException {
        String fileName = image1.getOriginalFilename();
        productService.directoryCreate();
        try {
            FileCopyUtils.copy(image1.getBytes(),
                    new File("E:/images/" + fileName)); // coppy ảnh từ ảnh nhận được vào thư mục quy định,
            // đường dẫn ảo là /images/
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        product.setImage(fileName);
        productService.save(product);
        return "/product/create";
    }

    @GetMapping("/edit-product/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product != null) {
            Product product1 = product.get();
            model.addAttribute("product", product1);
            return "/product/edit";
        } else {
            return "error";
        }
    }

    @PostMapping("/edit-product")
    public String update(Product product, @RequestParam MultipartFile image1) {
        String fileName = image1.getOriginalFilename();
        productService.directoryCreate();
        try {
            FileCopyUtils.copy(image1.getBytes(),
                    new File("E:/images/" + fileName)); // coppy ảnh từ ảnh nhận được vào thư mục quy định,
            // đường dẫn ảo là /images/
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        product.setImage(fileName);
        productService.save(product);
        return "/product/edit";
    }

    @GetMapping("/delete-product/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product != null) {
            Product product1 = product.get();
            model.addAttribute("product", product1);
            return "/product/delete";
        } else {
            return "error";
        }
    }

    @PostMapping("/delete-product")
    public String deleteProduct(Product product) {
        productService.remove(product.getId());
        return "redirect:/products";
    }
}
