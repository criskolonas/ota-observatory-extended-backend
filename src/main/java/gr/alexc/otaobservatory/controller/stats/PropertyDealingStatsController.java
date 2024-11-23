package gr.alexc.otaobservatory.controller.stats;
import gr.alexc.otaobservatory.dto.stats.propertyDealing.PropertyDealingByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.propertyDealing.PropertyDealingForPrefectureForMonths;
import gr.alexc.otaobservatory.service.stats.PropertyDealingStatsService;
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
@RequestMapping("stats/propertyDealings")
public class PropertyDealingStatsController {
    private final PropertyDealingStatsService service;

    @GetMapping("per-month")
    public ResponseEntity<PropertyDealingByPrefecturePerMonth> getPropertyDealingByPrefecturePerMonth(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date
    ) {
        return ResponseEntity.ok(service.getPropertyDealingByPrefecturePerMonth(date));
    }

    @GetMapping("for-prefecture-per-month")
    public ResponseEntity<PropertyDealingForPrefectureForMonths> getPropertyDealingForPrefecturePerMonth(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> fromDate,
            @RequestParam(value = "toDate",  required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> toDate,
            @RequestParam("prefectureId") Long prefectureId
    ) {
        return ResponseEntity.ok(service.getPropertyDealingForPrefecturePerMonth(
                fromDate,
                toDate,
                prefectureId
        ));
    }
}
