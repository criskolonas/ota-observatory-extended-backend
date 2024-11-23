package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.OtaVariableDTO;
import gr.alexc.otaobservatory.dto.mapper.OtaVariableMapper;
import gr.alexc.otaobservatory.entity.OtaVariable;
import gr.alexc.otaobservatory.exception.ResourceNotFoundException;
import gr.alexc.otaobservatory.repository.OtaVariableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OtaVariableService {

    private final OtaVariableRepository otavariableRepository;
    private final OtaVariableMapper otavariableMapper;

    public List<OtaVariableDTO> getOtaVariables() {
        return otavariableMapper.toDTO(otavariableRepository.findAll());
    }

    public OtaVariableDTO getOtaVariable(Long id) {
        return otavariableMapper.toDTO(otavariableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(OtaVariable.class, id)));
    }

}
