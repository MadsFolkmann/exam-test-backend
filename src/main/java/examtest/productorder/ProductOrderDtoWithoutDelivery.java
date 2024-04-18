package examtest.productorder;

import examtest.product.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrderDtoWithoutDelivery {
    private Long id;
    private int quantity;
    private Product product;
}
