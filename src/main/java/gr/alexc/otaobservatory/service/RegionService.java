package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.region.RegionDTO;
import gr.alexc.otaobservatory.dto.region.RegionGeomDTO;
import gr.alexc.otaobservatory.dto.region.RegionGeomSimpleDTO;
import gr.alexc.otaobservatory.dto.mapper.RegionMapper;
import gr.alexc.otaobservatory.dto.report.RegionReportDTO;
import gr.alexc.otaobservatory.entity.Region;
import gr.alexc.otaobservatory.exception.ResourceNotFoundException;
import gr.alexc.otaobservatory.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    private final RegionMapper regionMapper;

    public List<RegionGeomSimpleDTO> getAllRegions() {
        return regionMapper.regionToRegionSimpleListDto(regionRepository.findAll());
    }

    public RegionGeomSimpleDTO getRegionById(Long id) {
        return regionMapper.regionToRegionSimpleDto(regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Region.class, id)));
    }

    public RegionDTO getRegionDetails(Long id) {
        return regionMapper.regionToRegionDTO(regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Region.class, id)));
    }

    public RegionGeomDTO getRegionGeomDetails(Long id) {
        return regionMapper.regionToRegionGeomDto(regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Region.class, id)));
    }

    public RegionReportDTO getRegionReport(Long regionId) {
        RegionReportDTO regionReport = regionMapper.regionToRegionReportDto(regionRepository.findById(regionId)
                .orElseThrow(() -> new ResourceNotFoundException(Region.class, regionId)));
        regionReport.setTotalPrefectures((long) regionReport.getPrefectures().size());
        regionReport.setTotalOTA(regionRepository.getTotalOtaById(regionId));
        return regionReport;
    }

}
