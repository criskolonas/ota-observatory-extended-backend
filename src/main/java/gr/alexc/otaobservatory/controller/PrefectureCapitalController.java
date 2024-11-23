package gr.alexc.otaobservatory.controller;
import gr.alexc.otaobservatory.dto.prefecture.PrefectureCapitalDTO;
import gr.alexc.otaobservatory.service.PrefectureCapitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("prefectureCapitals")
@RequiredArgsConstructor
public class PrefectureCapitalController {
    private final PrefectureCapitalService prefectureCapitalService;

    @GetMapping("")
    public List<PrefectureCapitalDTO> getPrefecturesGeom() {
        return prefectureCapitalService.getAllPrefectureCapitals();
    }}
