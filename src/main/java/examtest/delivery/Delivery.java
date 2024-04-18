package examtest.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import examtest.productorder.ProductOrder;
import examtest.van.Van;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate deliveryDate;
    private String  fromWareHouse;
    private String destination;
    @JsonManagedReference
  @OneToMany(mappedBy = "delivery")
    private List<ProductOrder> productOrders = new ArrayList<>();

    @ManyToOne
    private Van van;

    public Delivery() {
    }

    public Delivery(LocalDate deliveryDate, String fromWareHouse, String destination, List<ProductOrder> productOrders) {
        this.deliveryDate = deliveryDate;
        this.fromWareHouse = fromWareHouse;
        this.destination = destination;
        this.productOrders = productOrders;
    }

    public int getTotalWeightInKg() {
        int totalWeight = 0;
                for (ProductOrder productOrder : productOrders) {
                    totalWeight += productOrder.getWeightInGrams();
                }

                return totalWeight / 1000;
//        (int)Math.ceil((double)totalWeight / 1000);
    }
}
