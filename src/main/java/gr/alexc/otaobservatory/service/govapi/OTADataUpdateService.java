package gr.alexc.otaobservatory.service.govapi;

import gr.alexc.otaobservatory.dto.request.govapi.*;
import gr.alexc.otaobservatory.entity.CadastralRegistrationStatus;
import gr.alexc.otaobservatory.entity.GovApiCall;
import gr.alexc.otaobservatory.entity.OTA;
import gr.alexc.otaobservatory.entity.ota.*;
import gr.alexc.otaobservatory.enums.OTAVariable;
import gr.alexc.otaobservatory.repository.CadastralRegistrationStatusRepository;
import gr.alexc.otaobservatory.repository.GovApiCallRepository;
import gr.alexc.otaobservatory.repository.OTARepository;
import gr.alexc.otaobservatory.repository.ota.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OTADataUpdateService {

    private final GovApiService govApiService;
    private final GovApiCallRepository govApiCallRepository;
    private final ConfiscationOTARepository confiscationOTARepository;
    private final AreaOTARepository areaOTARepository;
    private final CadastralStatusOTARepository cadastralStatusOTARepository;
    private final MortgageOTARepository mortgageOTARepository;
    private final OwnersOTARepository ownersOTARepository;
    private final PropertiesOTARepository propertiesOTARepository;
    private final PropertyDealingOTARepository propertyDealingOTARepository;
    private final OTARepository otaRepository;
    private final CadastralRegistrationStatusRepository registrationStatusRepository;
    private final CadastralRegistrationStatusRepository cadastralRegistrationStatusRepository;

    public GovApiCall updateConfiscationData() {

        String otaVariable = OTAVariable.CONFISCATION.name();

        // get last date updated
        Optional<GovApiCall> lastDateUpdateOptional = govApiCallRepository
                .findLatestOfOtaVariableType(otaVariable);

        // set the from date
        LocalDate fromDate;
        if (lastDateUpdateOptional.isPresent()) {
            fromDate = lastDateUpdateOptional.get().getToDate().plusDays(1);
        } else {
            fromDate = LocalDate.of(2024, Month.JANUARY, 1);
        }

        // set the to date
        LocalDate toDate;
        LocalDate today = LocalDate.now();
        if (Duration.between(fromDate.atStartOfDay(), today.atStartOfDay()).toDays() > 10) {
            toDate = fromDate.plusDays(10);
        } else {
            toDate = today;
        }

        // call the API to get data
        //TODO: We need to add also case of which a day may not have data
        Map<Date, List<ConfiscationRecord>> govData = govApiService.getOTAConfiscationsForDates(fromDate, toDate);

        // get for each the last record for the specific variable
        List<Date> govDataDates = govData.keySet().stream().sorted().toList();
        for (Date govDataDate : govDataDates) {
            List<ConfiscationRecord> confRecords = govData.get(govDataDate);

            // we check each record individually
            for (ConfiscationRecord confRecord : confRecords) {

                // we try to check if there is a record for the specific OTA by getting the last record in the table
                Optional<ConfiscationOTA> latestConfiscation = confiscationOTARepository
                        .getLastConfiscationOTA(confRecord.getOtaId());

                if (latestConfiscation.isPresent()) {
                    ConfiscationOTA confiscationOTA = latestConfiscation.get();
                    if (Objects.equals(confiscationOTA.getConfiscations(), confRecord.getConfiscations())) {
                        // if value is the same, we update the dates only
                        confiscationOTA.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        // TODO: add check date for the last check even if the day data is empty. e.g. 31/1 is empty day
                        confiscationOTA.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        confiscationOTARepository.save(confiscationOTA);
                    } else {
                        ConfiscationOTA newConfiscationEntry = new ConfiscationOTA();
                        newConfiscationEntry.setConfiscations(confRecord.getConfiscations());
                        newConfiscationEntry.setOta(confiscationOTA.getOta());
                        newConfiscationEntry.setConfiscations(confRecord.getConfiscations());
                        newConfiscationEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newConfiscationEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newConfiscationEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        confiscationOTARepository.save(newConfiscationEntry);
                    }
                } else {
                    Optional<OTA> otaOptional = otaRepository.findOTAByOtaId(confRecord.getOtaId().toString());
                    if (otaOptional.isPresent()) {
                        OTA ota = otaOptional.get();
                        ConfiscationOTA newConfiscationEntry = new ConfiscationOTA();
                        newConfiscationEntry.setConfiscations(confRecord.getConfiscations());
                        newConfiscationEntry.setOta(ota);
                        newConfiscationEntry.setConfiscations(confRecord.getConfiscations());
                        newConfiscationEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newConfiscationEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newConfiscationEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        confiscationOTARepository.save(newConfiscationEntry);
                    }

                }
            }

        }

        GovApiCall currentGovApiCall = new GovApiCall();
        currentGovApiCall.setOtaVariable(otaVariable);
        currentGovApiCall.setFromDate(fromDate);
        currentGovApiCall.setToDate(toDate);
        currentGovApiCall.setTimestamp(Timestamp.from(Instant.now()));
        return govApiCallRepository.save(currentGovApiCall);
    }

    public GovApiCall updateAreaData() {

        String otaVariable = OTAVariable.AREA.name();

        // get last date updated
        Optional<GovApiCall> lastDateUpdateOptional = govApiCallRepository
                .findLatestOfOtaVariableType(otaVariable);

        // set the from date
        LocalDate fromDate;
        if (lastDateUpdateOptional.isPresent()) {
            fromDate = lastDateUpdateOptional.get().getToDate().plusDays(1);
        } else {
            fromDate = LocalDate.of(2024, Month.JANUARY, 1);
        }

        // set the to date
        LocalDate toDate;
        LocalDate today = LocalDate.now();
        if (Duration.between(fromDate.atStartOfDay(), today.atStartOfDay()).toDays() > 10) {
            toDate = fromDate.plusDays(10);
        } else {
            toDate = today;
        }

        // call the API to get data
        //TODO: We need to add also case of which a day may not have data
        Map<Date, List<AreaRecord>> govData = govApiService.getOTAreasForDates(fromDate, toDate);

        // get for each the last record for the specific variable
        List<Date> govDataDates = govData.keySet().stream().sorted().toList();
        for (Date govDataDate : govDataDates) {
            List<AreaRecord> areaRecords = govData.get(govDataDate);

            // we check each record individually
            for (AreaRecord areaRecord : areaRecords) {

                // we try to check if there is a record for the specific OTA by getting the last record in the table
                Optional<AreaOTA> latestArea = areaOTARepository.getLastAreaOTA(areaRecord.getOtaId());

                if (latestArea.isPresent()) {
                    AreaOTA areaOTA = latestArea.get();
                    if (Objects.equals(areaOTA.getArea(), areaRecord.getArea())) {
                        // if value is the same, we update the dates only
                        areaOTA.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        // TODO: add check date for the last check even if the day data is empty. e.g. 31/1 is empty day
                        areaOTA.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        areaOTARepository.save(areaOTA);
                    } else {
                        AreaOTA newAreaEntry = new AreaOTA();
                        newAreaEntry.setArea(areaRecord.getArea());
                        newAreaEntry.setOta(areaOTA.getOta());
                        newAreaEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newAreaEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newAreaEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        areaOTARepository.save(newAreaEntry);
                    }
                } else {
                    Optional<OTA> otaOptional = otaRepository.findOTAByOtaId(areaRecord.getOtaId().toString());
                    if (otaOptional.isPresent()) {
                        OTA ota = otaOptional.get();
                        AreaOTA newAreaEntry = new AreaOTA();
                        newAreaEntry.setArea(areaRecord.getArea());
                        // check ti vazoume edw
//                        newAreaEntry.setStatus();
                        newAreaEntry.setOta(ota);
                        newAreaEntry.setArea(areaRecord.getArea());
                        newAreaEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newAreaEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newAreaEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        areaOTARepository.save(newAreaEntry);
                    }

                }
            }

        }

        GovApiCall currentGovApiCall = new GovApiCall();
        currentGovApiCall.setOtaVariable(otaVariable);
        currentGovApiCall.setFromDate(fromDate);
        currentGovApiCall.setToDate(toDate);
        currentGovApiCall.setTimestamp(Timestamp.from(Instant.now()));
        return govApiCallRepository.save(currentGovApiCall);
    }

    public GovApiCall updateCadastralStatusData() {

        String otaVariable = OTAVariable.CADASTRAL_REGISTRATION_STATUS.name();

        // get last date updated
        Optional<GovApiCall> lastDateUpdateOptional = govApiCallRepository
                .findLatestOfOtaVariableType(otaVariable);

        // set the from date
        LocalDate fromDate;
        if (lastDateUpdateOptional.isPresent()) {
            fromDate = lastDateUpdateOptional.get().getToDate().plusDays(1);
        } else {
            fromDate = LocalDate.of(2024, Month.JANUARY, 1);
        }

        // set the to date
        LocalDate toDate;
        LocalDate today = LocalDate.now();
        if (Duration.between(fromDate.atStartOfDay(), today.atStartOfDay()).toDays() > 10) {
            toDate = fromDate.plusDays(10);
        } else {
            toDate = today;
        }

        // call the API to get data
        //TODO: We need to add also case of which a day may not have data
        Map<Date, List<CadastralStatusRecord>> govData = govApiService.getOTACadastralStatusForDates(fromDate, toDate);

        // get for each the last record for the specific variable
        List<Date> govDataDates = govData.keySet().stream().sorted().toList();
        for (Date govDataDate : govDataDates) {
            List<CadastralStatusRecord> cadastralStatusRecords = govData.get(govDataDate);

            // we check each record individually
            for (CadastralStatusRecord cadastralStatusRecord : cadastralStatusRecords) {

                // we try to check if there is a record for the specific OTA by getting the last record in the table
                Optional<CadastralRegistrationStatusOTA> latestCadastralRegistrationStatus = cadastralStatusOTARepository.getLastCadastralStatusOTA(cadastralStatusRecord.getOtaId());
                if (latestCadastralRegistrationStatus.isPresent()) {
                    CadastralRegistrationStatusOTA cadastralStatusOTA = latestCadastralRegistrationStatus.get();
                    if (Objects.equals(cadastralStatusOTA.getStatus().getStatus(), cadastralStatusRecord.getStatus())) {
                        // if value is the same, we update the dates only
                        cadastralStatusOTA.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        // TODO: add check date for the last check even if the day data is empty. e.g. 31/1 is empty day
                        cadastralStatusOTA.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        cadastralStatusOTARepository.save(cadastralStatusOTA);
                    } else {
                        CadastralRegistrationStatusOTA newCadastralStatudEntry = new CadastralRegistrationStatusOTA();
                        newCadastralStatudEntry.setStatus(getCadastralRegistrationStatus(cadastralStatusRecord.getStatus()));
                        newCadastralStatudEntry.setOta(cadastralStatusOTA.getOta());
                        newCadastralStatudEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newCadastralStatudEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newCadastralStatudEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        cadastralStatusOTARepository.save(newCadastralStatudEntry);
                    }
                } else {
                    Optional<OTA> otaOptional = otaRepository.findOTAByOtaId(cadastralStatusRecord.getOtaId().toString());
                    if (otaOptional.isPresent()) {
                        OTA ota = otaOptional.get();
                        CadastralRegistrationStatusOTA newCadastralStatusEntry = new CadastralRegistrationStatusOTA();
                        newCadastralStatusEntry.setStatus(getCadastralRegistrationStatus(cadastralStatusRecord.getStatus()));
                        newCadastralStatusEntry.setOta(ota);
                        newCadastralStatusEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newCadastralStatusEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newCadastralStatusEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        cadastralStatusOTARepository.save(newCadastralStatusEntry);
                    }

                }
            }

        }

        GovApiCall currentGovApiCall = new GovApiCall();
        currentGovApiCall.setOtaVariable(otaVariable);
        currentGovApiCall.setFromDate(fromDate);
        currentGovApiCall.setToDate(toDate);
        currentGovApiCall.setTimestamp(Timestamp.from(Instant.now()));
        return govApiCallRepository.save(currentGovApiCall);
    }

    private CadastralRegistrationStatus getCadastralRegistrationStatus(String status) {
        Optional<CadastralRegistrationStatus> cadastralRegistrationStatusOptional =
                cadastralRegistrationStatusRepository.findByDescription(status);
        if (cadastralRegistrationStatusOptional.isEmpty()) {
            return cadastralRegistrationStatusRepository.save(new CadastralRegistrationStatus(null, status));
        } else {
            return cadastralRegistrationStatusOptional.get();
        }
    }

    public GovApiCall updateMortgageData() {

        String otaVariable = OTAVariable.MORTGAGES.name();

        // get last date updated
        Optional<GovApiCall> lastDateUpdateOptional = govApiCallRepository
                .findLatestOfOtaVariableType(otaVariable);

        // set the from date
        LocalDate fromDate;
        if (lastDateUpdateOptional.isPresent()) {
            fromDate = lastDateUpdateOptional.get().getToDate().plusDays(1);
        } else {
            fromDate = LocalDate.of(2024, Month.JANUARY, 1);
        }

        // set the to date
        LocalDate toDate;
        LocalDate today = LocalDate.now();
        if (Duration.between(fromDate.atStartOfDay(), today.atStartOfDay()).toDays() > 10) {
            toDate = fromDate.plusDays(10);
        } else {
            toDate = today;
        }

        // call the API to get data
        //TODO: We need to add also case of which a day may not have data
        Map<Date, List<MortgageRecord>> govData = govApiService.getOTAMortgageForDates(fromDate, toDate);

        // get for each the last record for the specific variable
        List<Date> govDataDates = govData.keySet().stream().sorted().toList();
        for (Date govDataDate : govDataDates) {
            List<MortgageRecord> mortgageRecords = govData.get(govDataDate);

            // we check each record individually
            for (MortgageRecord mortgageRecord : mortgageRecords) {

                // we try to check if there is a record for the specific OTA by getting the last record in the table
                Optional<MortgageOTA> latestMortgage = mortgageOTARepository.getLastMortgageOTA(mortgageRecord.getOtaId());
                if (latestMortgage.isPresent()) {
                    MortgageOTA mortgageOTA = latestMortgage.get();
                    if (Objects.equals(mortgageOTA.getMortgages(), mortgageRecord.getLiens())) {
                        // if value is the same, we update the dates only
                        mortgageOTA.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        // TODO: add check date for the last check even if the day data is empty. e.g. 31/1 is empty day
                        mortgageOTA.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        mortgageOTARepository.save(mortgageOTA);
                    } else {
                        MortgageOTA newMortgageEntry = new MortgageOTA();
                        newMortgageEntry.setMortgages(mortgageRecord.getLiens());
                        newMortgageEntry.setOta(mortgageOTA.getOta());
                        newMortgageEntry.setMortgages(mortgageRecord.getLiens());
                        newMortgageEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newMortgageEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newMortgageEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        mortgageOTARepository.save(newMortgageEntry);
                    }
                } else {
                    Optional<OTA> otaOptional = otaRepository.findOTAByOtaId(mortgageRecord.getOtaId().toString());
                    if (otaOptional.isPresent()) {
                        OTA ota = otaOptional.get();
                        MortgageOTA newMortgageEntry = new MortgageOTA();
                        newMortgageEntry.setMortgages(mortgageRecord.getLiens());
                        newMortgageEntry.setOta(ota);
                        newMortgageEntry.setMortgages(mortgageRecord.getLiens());
                        newMortgageEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newMortgageEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newMortgageEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        mortgageOTARepository.save(newMortgageEntry);
                    }

                }
            }

        }

        GovApiCall currentGovApiCall = new GovApiCall();
        currentGovApiCall.setOtaVariable(otaVariable);
        currentGovApiCall.setFromDate(fromDate);
        currentGovApiCall.setToDate(toDate);
        currentGovApiCall.setTimestamp(Timestamp.from(Instant.now()));
        return govApiCallRepository.save(currentGovApiCall);
    }
    public GovApiCall updateOwnersData() {

        String otaVariable = OTAVariable.OWNERS.name();

        // get last date updated
        Optional<GovApiCall> lastDateUpdateOptional = govApiCallRepository
                .findLatestOfOtaVariableType(otaVariable);

        // set the from date
        LocalDate fromDate;
        if (lastDateUpdateOptional.isPresent()) {
            fromDate = lastDateUpdateOptional.get().getToDate().plusDays(1);
        } else {
            fromDate = LocalDate.of(2024, Month.JANUARY, 1);
        }

        // set the to date
        LocalDate toDate;
        LocalDate today = LocalDate.now();
        if (Duration.between(fromDate.atStartOfDay(), today.atStartOfDay()).toDays() > 10) {
            toDate = fromDate.plusDays(10);
        } else {
            toDate = today;
        }

        // call the API to get data
        //TODO: We need to add also case of which a day may not have data
        Map<Date, List<OwnersRecord>> govData = govApiService.getOTAOwnersForDates(fromDate, toDate);

        // get for each the last record for the specific variable
        List<Date> govDataDates = govData.keySet().stream().sorted().toList();
        for (Date govDataDate : govDataDates) {
            List<OwnersRecord> ownersRecords = govData.get(govDataDate);

            // we check each record individually
            for (OwnersRecord ownersRecord : ownersRecords) {

                // we try to check if there is a record for the specific OTA by getting the last record in the table
                Optional<OwnersOTA> latestOwnersOTA = ownersOTARepository.getLastOwnerOTA(ownersRecord.getOtaId());
                if (latestOwnersOTA.isPresent()) {
                    OwnersOTA ownersOTA = latestOwnersOTA.get();
                    if (Objects.equals(ownersOTA.getOwners(), ownersRecord.getOwners())) {
                        // if value is the same, we update the dates only
                        ownersOTA.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        // TODO: add check date for the last check even if the day data is empty. e.g. 31/1 is empty day
                        ownersOTA.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        ownersOTARepository.save(ownersOTA);
                    } else {
                        OwnersOTA newOwnerEntry = new OwnersOTA();
                        newOwnerEntry.setOwners(ownersRecord.getOwners());
                        newOwnerEntry.setOta(ownersOTA.getOta());
                        newOwnerEntry.setOwners(ownersRecord.getOwners());
                        newOwnerEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newOwnerEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newOwnerEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        ownersOTARepository.save(newOwnerEntry);
                    }
                } else {
                    Optional<OTA> otaOptional = otaRepository.findOTAByOtaId(ownersRecord.getOtaId().toString());
                    if (otaOptional.isPresent()) {
                        OTA ota = otaOptional.get();
                        OwnersOTA newOwnerEntry = new OwnersOTA();
                        newOwnerEntry.setOwners(ownersRecord.getOwners());
                        newOwnerEntry.setOta(ota);
                        newOwnerEntry.setOwners(ownersRecord.getOwners());
                        newOwnerEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newOwnerEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newOwnerEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        ownersOTARepository.save(newOwnerEntry);
                    }

                }
            }

        }

        GovApiCall currentGovApiCall = new GovApiCall();
        currentGovApiCall.setOtaVariable(otaVariable);
        currentGovApiCall.setFromDate(fromDate);
        currentGovApiCall.setToDate(toDate);
        currentGovApiCall.setTimestamp(Timestamp.from(Instant.now()));
        return govApiCallRepository.save(currentGovApiCall);
    }
    public GovApiCall updatePropertiesData() {

        String otaVariable = OTAVariable.PROPERTIES.name();

        // get last date updated
        Optional<GovApiCall> lastDateUpdateOptional = govApiCallRepository
                .findLatestOfOtaVariableType(otaVariable);

        // set the from date
        LocalDate fromDate;
        if (lastDateUpdateOptional.isPresent()) {
            fromDate = lastDateUpdateOptional.get().getToDate().plusDays(1);
        } else {
            fromDate = LocalDate.of(2024, Month.JANUARY, 1);
        }

        // set the to date
        LocalDate toDate;
        LocalDate today = LocalDate.now();
        if (Duration.between(fromDate.atStartOfDay(), today.atStartOfDay()).toDays() > 10) {
            toDate = fromDate.plusDays(10);
        } else {
            toDate = today;
        }

        // call the API to get data
        //TODO: We need to add also case of which a day may not have data
        Map<Date, List<PropertiesRecord>> govData = govApiService.getOTAPropertiesForDates(fromDate, toDate);

        // get for each the last record for the specific variable
        List<Date> govDataDates = govData.keySet().stream().sorted().toList();
        for (Date govDataDate : govDataDates) {
            List<PropertiesRecord> propertiesRecords = govData.get(govDataDate);

            // we check each record individually
            for (PropertiesRecord propertiesRecord : propertiesRecords) {

                // we try to check if there is a record for the specific OTA by getting the last record in the table
                Optional<PropertiesOTA> latestProperties = propertiesOTARepository.getLastPropertyOTA(propertiesRecord.getOtaId());
                if (latestProperties.isPresent()) {
                    PropertiesOTA propertiesOTA = latestProperties.get();
                    if (Objects.equals(propertiesOTA.getProperties(), propertiesRecord.getPlots())) {
                        // if value is the same, we update the dates only
                        propertiesOTA.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        // TODO: add check date for the last check even if the day data is empty. e.g. 31/1 is empty day
                        propertiesOTA.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        propertiesOTARepository.save(propertiesOTA);
                    } else {
                        PropertiesOTA newPropertyEntry = new PropertiesOTA();
                        newPropertyEntry.setProperties(propertiesRecord.getPlots());
                        newPropertyEntry.setOta(propertiesOTA.getOta());
                        newPropertyEntry.setProperties(propertiesRecord.getPlots());
                        newPropertyEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newPropertyEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newPropertyEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        propertiesOTARepository.save(newPropertyEntry);
                    }
                } else {
                    Optional<OTA> otaOptional = otaRepository.findOTAByOtaId(propertiesRecord.getOtaId().toString());
                    if (otaOptional.isPresent()) {
                        OTA ota = otaOptional.get();
                        PropertiesOTA newPropertyEntry = new PropertiesOTA();
                        newPropertyEntry.setProperties(propertiesRecord.getPlots());
                        newPropertyEntry.setOta(ota);
                        newPropertyEntry.setProperties(propertiesRecord.getPlots());
                        newPropertyEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newPropertyEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newPropertyEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        propertiesOTARepository.save(newPropertyEntry);
                    }

                }
            }

        }

        GovApiCall currentGovApiCall = new GovApiCall();
        currentGovApiCall.setOtaVariable(otaVariable);
        currentGovApiCall.setFromDate(fromDate);
        currentGovApiCall.setToDate(toDate);
        currentGovApiCall.setTimestamp(Timestamp.from(Instant.now()));
        return govApiCallRepository.save(currentGovApiCall);
    }
    public GovApiCall updatePropertyDealingData() {

        String otaVariable = OTAVariable.PROPERTY_DEALING.name();

        // get last date updated
        Optional<GovApiCall> lastDateUpdateOptional = govApiCallRepository
                .findLatestOfOtaVariableType(otaVariable);

        // set the from date
        LocalDate fromDate;
        if (lastDateUpdateOptional.isPresent()) {
            fromDate = lastDateUpdateOptional.get().getToDate().plusDays(1);
        } else {
            fromDate = LocalDate.of(2024, Month.JANUARY, 1);
        }

        // set the to date
        LocalDate toDate;
        LocalDate today = LocalDate.now();
        if (Duration.between(fromDate.atStartOfDay(), today.atStartOfDay()).toDays() > 10) {
            toDate = fromDate.plusDays(10);
        } else {
            toDate = today;
        }

        // call the API to get data
        //TODO: We need to add also case of which a day may not have data
        Map<Date, List<PropertyDealingRecord>> govData = govApiService.getOTAPropertyDealingForDates(fromDate, toDate);

        // get for each the last record for the specific variable
        List<Date> govDataDates = govData.keySet().stream().sorted().toList();
        for (Date govDataDate : govDataDates) {
            List<PropertyDealingRecord> propertyDealingRecords = govData.get(govDataDate);

            // we check each record individually
            for (PropertyDealingRecord propertyDealingRecord : propertyDealingRecords) {

                // we try to check if there is a record for the specific OTA by getting the last record in the table
                Optional<PropertyDealingOTA> latestPropertyDealing = propertyDealingOTARepository.getLastPropertyDealingOTA(propertyDealingRecord.getOtaId());
                if (latestPropertyDealing.isPresent()) {
                    PropertyDealingOTA propertyDealingOTA = latestPropertyDealing.get();
                    if (Objects.equals(propertyDealingOTA.getPropertyDeals(), propertyDealingRecord.getTransactions())) {
                        // if value is the same, we update the dates only
                        propertyDealingOTA.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        // TODO: add check date for the last check even if the day data is empty. e.g. 31/1 is empty day
                        propertyDealingOTA.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        propertyDealingOTARepository.save(propertyDealingOTA);
                    } else {
                        PropertyDealingOTA newPropertyDealingEntry = new PropertyDealingOTA();
                        newPropertyDealingEntry.setPropertyDeals(propertyDealingRecord.getTransactions());
                        newPropertyDealingEntry.setOta(propertyDealingOTA.getOta());
                        newPropertyDealingEntry.setPropertyDeals(propertyDealingRecord.getTransactions());
                        newPropertyDealingEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newPropertyDealingEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newPropertyDealingEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        propertyDealingOTARepository.save(newPropertyDealingEntry);
                    }
                } else {
                    Optional<OTA> otaOptional = otaRepository.findOTAByOtaId(propertyDealingRecord.getOtaId().toString());
                    if (otaOptional.isPresent()) {
                        OTA ota = otaOptional.get();
                        PropertyDealingOTA newPropertyDealingEntry = new PropertyDealingOTA();
                        newPropertyDealingEntry.setPropertyDeals(propertyDealingRecord.getTransactions());
                        newPropertyDealingEntry.setOta(ota);
                        newPropertyDealingEntry.setPropertyDeals(propertyDealingRecord.getTransactions());
                        newPropertyDealingEntry.setCreateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newPropertyDealingEntry.setLastCheckDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        newPropertyDealingEntry.setUpdateDate(govDataDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        propertyDealingOTARepository.save(newPropertyDealingEntry);
                    }

                }
            }

        }

        GovApiCall currentGovApiCall = new GovApiCall();
        currentGovApiCall.setOtaVariable(otaVariable);
        currentGovApiCall.setFromDate(fromDate);
        currentGovApiCall.setToDate(toDate);
        currentGovApiCall.setTimestamp(Timestamp.from(Instant.now()));
        return govApiCallRepository.save(currentGovApiCall);
    }

}
