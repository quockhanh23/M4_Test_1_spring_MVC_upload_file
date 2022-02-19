package service.product;

import model.Category;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import repository.IProductRepository;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public Iterable<Product> findAll() {
        return iProductRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return iProductRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        iProductRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        iProductRepository.deleteById(id);
    }

    @Override
    public Iterable<Product> findAllByCategory(Category category) {
        return iProductRepository.findAllByCategory(category);
    }

    @Override
    public Iterable<Product> findByName(String name) {
        return iProductRepository.findByNameContaining(name);
    }

    @Override
    public Iterable<Product> findAllByOrderByPriceDesc() {
        return iProductRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public Iterable<Product> findAllByOrderByPriceAsc() {
        return iProductRepository.findAllByOrderByPriceAsc();
    }

}
