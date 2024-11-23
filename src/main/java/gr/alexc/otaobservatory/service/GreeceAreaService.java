package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.GreeceAreaViewDTO;
import gr.alexc.otaobservatory.dto.mapper.GreeceAreaMapper;
import gr.alexc.otaobservatory.repository.view.GreeceAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GreeceAreaService {

    private final GreeceAreaRepository greeceAreaRepository;
    private final GreeceAreaMapper greeceAreaMapper;

    public List<GreeceAreaViewDTO> getGreeceAreas() {
        return greeceAreaMapper.toDto(greeceAreaRepository.findAllAreas());
    }

}
