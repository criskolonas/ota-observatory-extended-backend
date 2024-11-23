package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.entity.GovApiCall;
import gr.alexc.otaobservatory.service.govapi.OTADataUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("test-gov-call")
public class TestCallGovController {

    private final OTADataUpdateService otaDataUpdateService;

    @GetMapping("confiscations")
    public ResponseEntity<GovApiCall> callConfiscationsGovApi() {
        return ResponseEntity.ok(otaDataUpdateService.updateConfiscationData());
    }

    @GetMapping("areas")
    public ResponseEntity<GovApiCall> callAreasGovApi() {
        return ResponseEntity.ok(otaDataUpdateService.updateAreaData());
    }

    @GetMapping("cadastralRegistrationStatus")
    public ResponseEntity<GovApiCall> callCadastralRegistrationStatusGovApi() {
        return ResponseEntity.ok(otaDataUpdateService.updateCadastralStatusData());

    }

    @GetMapping("mortgages")
    public ResponseEntity<GovApiCall> callMortgageGovApi() {
        return ResponseEntity.ok(otaDataUpdateService.updateMortgageData());

    }

    @GetMapping("owners")
    public ResponseEntity<GovApiCall> callOwnersGovApi() {
        return ResponseEntity.ok(otaDataUpdateService.updateOwnersData());

    }

    @GetMapping("properties")
    public ResponseEntity<GovApiCall> callPropertiesGovApi() {
        return ResponseEntity.ok(otaDataUpdateService.updatePropertiesData());

    }

    @GetMapping("propertiesDealing")
    public ResponseEntity<GovApiCall> callPropertiesDealingGovApi() {
        return ResponseEntity.ok(otaDataUpdateService.updatePropertyDealingData());

    }

}
