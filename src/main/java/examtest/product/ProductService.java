package examtest.product;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product editProduct(Product product, int id) {
        Product productToEdit = productRepository.findById(id).orElse(null);
        if (productToEdit == null) {
            return null;
        }
        productToEdit.setName(product.getName());
        productToEdit.setPrice(product.getPrice());
        productToEdit.setWeight(product.getWeight());
        return productRepository.save(productToEdit);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
