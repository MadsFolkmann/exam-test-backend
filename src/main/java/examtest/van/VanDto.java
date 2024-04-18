package examtest.van;

import examtest.delivery.DeliveryDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VanDto {
    private Long id;
    private String brand;
    private String model;
    private int capacityInKg;
}
