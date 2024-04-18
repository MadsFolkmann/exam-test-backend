package examtest.delivery;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public List<DeliveryDto> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDto> getDeliveryById(@PathVariable int id) {
        return deliveryService.getDeliveryById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public DeliveryDto addDelivery(@RequestBody DeliveryDto delivery) {
        return deliveryService.addDelivery(delivery);
    }
    @PostMapping("/{id}/addVan/{vanId}")
    public ResponseEntity<?> addDeliveryToVan(@PathVariable Long id, @PathVariable Long vanId) {
        try {
            DeliveryDto addedDelivery = deliveryService.addDeliveryToVan(id, vanId);
            return ResponseEntity.ok(addedDelivery);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public Delivery editDelivery(Delivery delivery, @PathVariable int id) {
        return deliveryService.editDelivery(delivery, id);
    }

    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable int id) {
        deliveryService.deleteDelivery(id);
    }
}
