package gr.alexc.otaobservatory.controller.stats;
import gr.alexc.otaobservatory.dto.stats.owners.OwnersByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.owners.OwnersForPrefectureForMonths;
import gr.alexc.otaobservatory.service.stats.OwnerStatsService;
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
@RequestMapping("stats/owners")
public class OwnerStatsController {
    private final OwnerStatsService service;

    @GetMapping("per-month")
    public ResponseEntity<OwnersByPrefecturePerMonth> getOwnersByPrefecturePerMonth(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date
    ) {
        return ResponseEntity.ok(service.getOwnersByPrefecturePerMonth(date));
    }

    @GetMapping("for-prefecture-per-month")
    public ResponseEntity<OwnersForPrefectureForMonths> getOwnersForPrefecturePerMonth(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> fromDate,
            @RequestParam(value = "toDate",  required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> toDate,
            @RequestParam("prefectureId") Long prefectureId
    ) {
        return ResponseEntity.ok(service.getOwnersForPrefecturePerMonth(
                fromDate,
                toDate,
                prefectureId
        ));
    }
}
