package gr.alexc.otaobservatory.dto.mapper;

import gr.alexc.otaobservatory.dto.region.RegionDTO;
import gr.alexc.otaobservatory.dto.region.RegionGeomDTO;
import gr.alexc.otaobservatory.dto.region.RegionGeomSimpleDTO;
import gr.alexc.otaobservatory.dto.report.RegionReportDTO;
import gr.alexc.otaobservatory.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PrefectureMapper.class})
public interface RegionMapper {

    RegionGeomSimpleDTO regionToRegionSimpleDto(Region region);

    List<RegionGeomSimpleDTO> regionToRegionSimpleListDto(List<Region> regions);

    RegionDTO regionToRegionDTO(Region region);

    RegionGeomDTO regionToRegionGeomDto(Region region);

    RegionReportDTO regionToRegionReportDto(Region region);
}
