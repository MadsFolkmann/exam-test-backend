package examtest.van;


import examtest.delivery.Delivery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/van")
public class VanController {
    private final VanService vanService;

    public VanController(VanService vanService) {
        this.vanService = vanService;
    }

    @GetMapping
    public List<VanDto> getAllVans() {
        return vanService.getAllVans();
    }

    @GetMapping("/id/{id}")
    public Optional<VanDto> getVanById(@PathVariable int id) {
        return vanService.getVanById(id);
    }




}
