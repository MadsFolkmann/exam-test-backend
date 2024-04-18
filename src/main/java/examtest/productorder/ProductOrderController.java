package examtest.productorder;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productOrder")
public class ProductOrderController {
    private final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @GetMapping
    public List<ProductOrder> getAllProductOrders() {
        return productOrderService.getAllProductOrders();
    }

    @GetMapping("/id/{id}")
    public Optional<ProductOrder> getProductOrderById(@PathVariable int id) {
        return productOrderService.getProductOrderById(id);
    }

    @GetMapping("/withoutDelivery")
    public List<ProductOrder> getAllProductOrdersWithoutDelivery() {
        return productOrderService.getAllProductOrdersWithoutDelivery();
    }

    @PostMapping
    public ProductOrder addProductOrder(@RequestBody ProductOrder productOrder) {
        return productOrderService.addProductOrder(productOrder);
    }

    @PutMapping("/id/{id}")
    public ProductOrder editProductOrder(@RequestBody ProductOrder productOrder, @PathVariable int id) {
        return productOrderService.editProductOrder(productOrder, id);
    }

    @DeleteMapping("/id/{id}")
    public void deleteProductOrder(@PathVariable int id) {
        productOrderService.deleteProductOrder(id);
    }

}
