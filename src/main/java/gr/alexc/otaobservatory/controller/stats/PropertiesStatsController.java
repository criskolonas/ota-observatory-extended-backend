package gr.alexc.otaobservatory.controller.stats;

import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.confiscations.ConfiscationsForPrefectureForMonths;
import gr.alexc.otaobservatory.dto.stats.properties.PropertiesByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.properties.PropertiesForPrefectureForMonths;
import gr.alexc.otaobservatory.service.stats.ConfiscationStatsService;
import gr.alexc.otaobservatory.service.stats.PropertiesStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("stats/properties")
public class PropertiesStatsController {
    private final PropertiesStatsService service;

    @GetMapping("per-month")
    public ResponseEntity<PropertiesByPrefecturePerMonth> getPropertiesByPrefecturePerMonth(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date
    ) {
        return ResponseEntity.ok(service.getPropertiesByPrefecturePerMonth(date));
    }

    @GetMapping("for-prefecture-per-month")
    public ResponseEntity<PropertiesForPrefectureForMonths> getPropertiesForPrefecturePerMonth(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> fromDate,
            @RequestParam(value = "toDate",  required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> toDate,
            @RequestParam("prefectureId") Long prefectureId
    ) {
        return ResponseEntity.ok(service.getPropertiesForPrefecturePerMonth(
                fromDate,
                toDate,
                prefectureId
        ));
    }
}
