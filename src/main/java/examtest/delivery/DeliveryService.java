package examtest.delivery;

import examtest.productorder.ProductOrder;
import examtest.productorder.ProductOrderDtoWithoutDelivery;
import examtest.productorder.ProductOrderRepository;
import examtest.van.Van;
import examtest.van.VanDto;
import examtest.van.VanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    private DeliveryRepository deliveryRepository;
    private ProductOrderRepository productOrderRepository;
    private VanRepository vanRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, ProductOrderRepository productOrderRepository, VanRepository vanRepository) {
        this.deliveryRepository = deliveryRepository;
        this.productOrderRepository = productOrderRepository;
        this.vanRepository = vanRepository;
    }

    public List<DeliveryDto> getAllDeliveries(){
        return deliveryRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<DeliveryDto> getDeliveryById(long id) {
        return deliveryRepository.findById(id)
                .map(this::toDto);
    }


    public DeliveryDto addDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(deliveryDto.getDeliveryDate());
        delivery.setFromWareHouse(deliveryDto.getFromWareHouse());
        delivery.setDestination(deliveryDto.getDestination());
        Delivery savedDelivery = deliveryRepository.save(delivery);

        List<ProductOrder> productOrders = deliveryDto.getProductOrders().stream()
                .map(dto -> {
                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setId(dto.getId());
                    productOrder.setQuantity(dto.getQuantity());
                    productOrder.setProduct(dto.getProduct());
                    productOrder.setDelivery(savedDelivery);
                    return productOrderRepository.save(productOrder);
                })
                .collect(Collectors.toList());

        delivery.setProductOrders(productOrders);
        return toDto(savedDelivery);
    }

    public DeliveryDto addDeliveryToVan(long deliveryId, long vanId) {
        Optional<Van> vanOptional = vanRepository.findById(vanId);
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(deliveryId);

        if (vanOptional.isPresent() && deliveryOptional.isPresent()) {
            Van van = vanOptional.get();
            Delivery delivery = deliveryOptional.get();

            double totalWeight = van.getDeliveries().stream()
                    .mapToDouble(Delivery::getTotalWeightInKg)
                    .sum();

            if (totalWeight + delivery.getTotalWeightInKg() <= van.getCapacityInKg()) {
                van.getDeliveries().add(delivery);
                delivery.setVan(van); // Set the van in the delivery
                deliveryRepository.save(delivery); // Save the delivery
                vanRepository.save(van); // Save the van
            } else {
                throw new IllegalStateException("Van capacity exceeded");
            }

            return toDto(delivery);
        } else {
            throw new RuntimeException("Van with id " + vanId + " or Delivery with id " + deliveryId + " does not exist");
        }
    }

    public Delivery editDelivery(Delivery delivery, long id) {
        delivery.setId(id);
        return deliveryRepository.save(delivery);
    }

    public void deleteDelivery(long id) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(id);
        if (deliveryOptional.isPresent()) {
            Delivery delivery = deliveryOptional.get();
            for (ProductOrder productOrder : delivery.getProductOrders()) {
                productOrder.setDelivery(null);
                productOrderRepository.save(productOrder);
            }
            deliveryRepository.deleteById(id);
        } else {
            // Handle the case where the Delivery with the given id does not exist
            throw new RuntimeException("Delivery with id " + id + " does not exist");
        }
    }
    private DeliveryDto toDto(Delivery delivery) {
        DeliveryDto dto = new DeliveryDto();
        dto.setId(delivery.getId());
        dto.setDeliveryDate(delivery.getDeliveryDate());
        dto.setFromWareHouse(delivery.getFromWareHouse());
        dto.setDestination(delivery.getDestination());
        dto.setProductOrders(delivery.getProductOrders().stream()
                .map(this::toDto)
                .collect(Collectors.toList()));
        if (delivery.getVan() != null) {
            dto.setVan(toDto(delivery.getVan()));
        }
        return dto;
    }

    private VanDto toDto(Van van) {
        VanDto dto = new VanDto();
        dto.setId(van.getId());
        dto.setBrand(van.getBrand());
        dto.setModel(van.getModel());
        dto.setCapacityInKg(van.getCapacityInKg());
        return dto;
    }

    private ProductOrderDtoWithoutDelivery toDto(ProductOrder productOrder) {
        ProductOrderDtoWithoutDelivery dto = new ProductOrderDtoWithoutDelivery();
        dto.setId(productOrder.getId());
        dto.setQuantity(productOrder.getQuantity());
        dto.setProduct(productOrder.getProduct());
        return dto;
    }

}