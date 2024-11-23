package gr.alexc.otaobservatory.controller.stats;
import gr.alexc.otaobservatory.dto.stats.mortgages.MortgagesByPrefecturePerMonth;
import gr.alexc.otaobservatory.dto.stats.mortgages.MortgagesForPrefectureForMonths;
import gr.alexc.otaobservatory.service.stats.MortgageStatsService;
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
@RequestMapping("stats/mortgages")
public class MortgageStatsController {
    private final MortgageStatsService service;

    @GetMapping("per-month")
    public ResponseEntity<MortgagesByPrefecturePerMonth> getMortgagesByPrefecturePerMonth(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date
    ) {
        return ResponseEntity.ok(service.getMortgagesByPrefecturePerMonth(date));
    }

    @GetMapping("for-prefecture-per-month")
    public ResponseEntity<MortgagesForPrefectureForMonths> getMortgagesForPrefecturePerMonth(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> fromDate,
            @RequestParam(value = "toDate",  required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> toDate,
            @RequestParam("prefectureId") Long prefectureId
    ) {
        return ResponseEntity.ok(service.getMortgagesForPrefecturePerMonth(
                fromDate,
                toDate,
                prefectureId
        ));
    }
}
