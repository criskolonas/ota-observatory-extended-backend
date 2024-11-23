package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.OtaVariableDTO;
import gr.alexc.otaobservatory.service.OtaVariableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("ota-variables")
public class OtaVariableController {

    private final OtaVariableService otaVariableService;

    @GetMapping
    public ResponseEntity<List<OtaVariableDTO>> getAllVariables() {
        return ResponseEntity.ok(otaVariableService.getOtaVariables());
    }

    @GetMapping("{id}")
    public ResponseEntity<OtaVariableDTO> getVariableById(@PathVariable Long id) {
        return ResponseEntity.ok(otaVariableService.getOtaVariable(id));
    }

}
