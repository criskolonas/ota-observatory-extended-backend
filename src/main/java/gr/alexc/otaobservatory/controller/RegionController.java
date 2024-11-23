package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.region.RegionDTO;
import gr.alexc.otaobservatory.dto.region.RegionGeomDTO;
import gr.alexc.otaobservatory.dto.region.RegionGeomSimpleDTO;
import gr.alexc.otaobservatory.dto.report.RegionReportDTO;
import gr.alexc.otaobservatory.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("geom")
    public List<RegionGeomSimpleDTO> getAllRegions() {
        return regionService.getAllRegions();
    }

    @GetMapping("geom/{id}")
    public RegionGeomSimpleDTO getRegionById(@PathVariable Long id) {
        return regionService.getRegionById(id);
    }

    @GetMapping("geom/details/{id}")
    public RegionGeomDTO getRegionDetailsGeom(@PathVariable Long id) {
        return regionService.getRegionGeomDetails(id);
    }

    @GetMapping("details/{id}")
    public RegionDTO getRegionDetailsById(@PathVariable Long id) {
        return regionService.getRegionDetails(id);
    }

    @GetMapping("report/{id}")
    public RegionReportDTO getRegionReport(@PathVariable Long id) {
        return regionService.getRegionReport(id);
    }
}

