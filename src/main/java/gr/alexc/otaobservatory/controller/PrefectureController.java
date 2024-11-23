package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.prefecture.PrefectureGeomDTO;
import gr.alexc.otaobservatory.dto.prefecture.PrefectureGeomSimpleDTO;
import gr.alexc.otaobservatory.service.PrefectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("prefectures")
@RequiredArgsConstructor
public class PrefectureController {

    private final PrefectureService prefectureService;

    @GetMapping("geom")
    public List<PrefectureGeomSimpleDTO> getPrefecturesGeom() {
        return prefectureService.getAllPrefecturesGeom();
    }


    @GetMapping("geom/region/{regionId}")
    public List<PrefectureGeomSimpleDTO> getPrefecturesGeomByRegionId(@PathVariable Long regionId) {
        return prefectureService.getPrefecturesByRegionId(regionId);
    }

    @GetMapping("geom/{id}")
    public PrefectureGeomDTO getPrefectureById(@PathVariable Long id) {
        return prefectureService.getPrefectureById(id);
    }
}
