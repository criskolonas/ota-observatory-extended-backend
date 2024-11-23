package gr.alexc.otaobservatory.controller.stats;
import gr.alexc.otaobservatory.dto.stats.area.AreaByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.area.AreaForPrefectureForMonths;
import gr.alexc.otaobservatory.service.stats.AreaStatsService;
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
@RequestMapping("stats/areas")
public class AreaStatsController {
    private final AreaStatsService service;

    @GetMapping("per-month")
    public ResponseEntity<AreaByPrefecturePerMonth> getAreaByPrefecturePerMonth(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date
    ) {
        return ResponseEntity.ok(service.getAreaByPrefecturePerMonth(date));
    }

    @GetMapping("for-prefecture-per-month")
    public ResponseEntity<AreaForPrefectureForMonths> getAreaForPrefecturePerMonth(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> fromDate,
            @RequestParam(value = "toDate",  required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> toDate,
            @RequestParam("prefectureId") Long prefectureId
    ) {
        return ResponseEntity.ok(service.getAreaForPrefecturePerMonth(
                fromDate,
                toDate,
                prefectureId
        ));
    }
}
