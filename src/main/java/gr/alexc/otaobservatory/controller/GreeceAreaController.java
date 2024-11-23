package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.GreeceAreaViewDTO;
import gr.alexc.otaobservatory.service.GreeceAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("greece-areas")
public class GreeceAreaController {

    private final GreeceAreaService greeceAreaService;

    @GetMapping
    public ResponseEntity<List<GreeceAreaViewDTO>> getGreeceAreas() {
        return ResponseEntity.ok(greeceAreaService.getGreeceAreas());
    }

}
