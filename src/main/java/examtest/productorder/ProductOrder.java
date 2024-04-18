package examtest.productorder;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import examtest.delivery.Delivery;
import examtest.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    private Product product;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public ProductOrder() {
    }

    public ProductOrder(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }
    public ProductOrder(int quantity, Product product, Delivery delivery) {
        this.quantity = quantity;
        this.product = product;
        this.delivery = delivery;
    }


    public double getWeightInGrams() {
        return product.getWeight() * quantity;
    }
}
