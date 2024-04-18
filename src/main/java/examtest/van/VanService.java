package examtest.van;

import examtest.delivery.Delivery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VanService {
    private final VanRepository vanRepository;

    public VanService(VanRepository vanRepository) {
        this.vanRepository = vanRepository;
    }

    public List<VanDto> getAllVans() {
        return vanRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<VanDto> getVanById(long id) {
        return vanRepository.findById(id)
                .map(this::toDto);
    }

    private VanDto toDto(Van van) {
        VanDto dto = new VanDto();
        dto.setId(van.getId());
        dto.setBrand(van.getBrand());
        dto.setModel(van.getModel());
        dto.setCapacityInKg(van.getCapacityInKg());
        return dto;
    }
}


