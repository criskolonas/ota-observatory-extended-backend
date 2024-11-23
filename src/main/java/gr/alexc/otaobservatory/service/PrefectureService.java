package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.prefecture.PrefectureGeomDTO;
import gr.alexc.otaobservatory.dto.prefecture.PrefectureGeomSimpleDTO;
import gr.alexc.otaobservatory.dto.mapper.PrefectureMapper;
import gr.alexc.otaobservatory.entity.Prefecture;
import gr.alexc.otaobservatory.exception.ResourceNotFoundException;
import gr.alexc.otaobservatory.repository.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrefectureService {

    private final PrefectureRepository prefectureRepository;
    private final PrefectureMapper prefectureMapper;

    public List<PrefectureGeomSimpleDTO> getAllPrefecturesGeom() {
        return prefectureMapper.toPrefectureGeomSimpleDTO(prefectureRepository.findAll());
    }

    public List<PrefectureGeomSimpleDTO> getPrefecturesByRegionId(Long regionId) {
        return prefectureMapper.toPrefectureGeomSimpleDTO(prefectureRepository.findAllByRegionId(regionId));
    }

    public PrefectureGeomDTO getPrefectureById(Long id) {
        return prefectureMapper.toPrefectureGeomDTO(prefectureRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException(Prefecture.class, id)));
    }

}
