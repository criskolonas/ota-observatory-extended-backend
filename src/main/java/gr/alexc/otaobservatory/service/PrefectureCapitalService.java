package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.prefecture.PrefectureCapitalDTO;
import gr.alexc.otaobservatory.dto.mapper.PrefectureCapitalMapper;
import gr.alexc.otaobservatory.repository.PrefectureCapitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrefectureCapitalService {

    private final PrefectureCapitalRepository prefectureCapitalRepository;
    private final PrefectureCapitalMapper prefectureCapitalMapper;

    public List<PrefectureCapitalDTO> getAllPrefectureCapitals() {
        return prefectureCapitalMapper.toPrefectureCapitalDTO(prefectureCapitalRepository.findAll());
    }
}

