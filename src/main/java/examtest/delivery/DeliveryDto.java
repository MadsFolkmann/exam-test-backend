package examtest.delivery;

import examtest.productorder.ProductOrderDtoWithoutDelivery;
import examtest.van.Van;
import examtest.van.VanDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DeliveryDto {
    private Long id;
    private LocalDate deliveryDate;
    private String fromWareHouse;
    private String destination;
    private List<ProductOrderDtoWithoutDelivery> productOrders;
    private VanDto van;


}
