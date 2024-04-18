package examtest.productorder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    public List<ProductOrder> getAllProductOrders(){
        return productOrderRepository.findAll();
    }

    public Optional<ProductOrder> getProductOrderById(long id) {
        return productOrderRepository.findById(id);
    }

    public List<ProductOrder> getAllProductOrdersWithoutDelivery(){
        return productOrderRepository.findAll().stream()
                .filter(productOrder -> productOrder.getDelivery() == null)
                .collect(Collectors.toList());
    }

    public ProductOrder addProductOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    public ProductOrder editProductOrder(ProductOrder productOrder, long id) {
        productOrder.setId(id);
        return productOrderRepository.save(productOrder);
    }

    public void deleteProductOrder(long id) {
        productOrderRepository.deleteById(id);
    }


}