package examtest.van;

import examtest.delivery.Delivery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Van {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private int capacityInKg;
    @OneToMany(mappedBy = "van")
    private List<Delivery> deliveries;
    
    public Van() {
    }

    public Van(String brand, String model, int capacityInKg) {
        this.brand = brand;
        this.model = model;
        this.capacityInKg = capacityInKg;
    }

    public int getCombinedWeightOfDeliveriesInKg() {
        int totalWeightOfDeliveries = 0;
        for (Delivery d : getDeliveries()) {
            totalWeightOfDeliveries += d.getTotalWeightInKg();
        }
        return totalWeightOfDeliveries;
    }
}
