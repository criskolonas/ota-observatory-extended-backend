package gr.alexc.otaobservatory.service.govapi;

import gr.alexc.otaobservatory.dto.request.govapi.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GovApiService {

    @Value("${govApi.url.confiscations}")
    private String confiscationsUrl;
    @Value("${govApi.url.areas}")
    private String areaUrl;
    @Value("${govApi.url.cadastralRecords}")
    private String cadastralRecordsUrl;
    @Value("${govApi.url.mortgages}")
    private String mortgagesUrl;
    @Value("${govApi.url.owners}")
    private String ownersUrl;
    @Value("${govApi.url.properties}")
    private String propertiesUrl;
    @Value("${govApi.url.propertyDealings}")
    private String propertyDealingsUrl;

    @Value("${govApi.key}")
    private String govApiKey;

    public Map<Date, List<ConfiscationRecord>> getOTAConfiscationsForDates(LocalDate from, LocalDate to) {
        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(confiscationsUrl)
                .queryParam("date_from", "{date_from}")
                .queryParam("date_to", "{date_to}")
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Token " + govApiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date_from", df.format(from));
        queryParams.put("date_to", df.format(to));

        ResponseEntity<ConfiscationRecord[]> response = template.exchange(url, HttpMethod.GET, httpEntity, ConfiscationRecord[].class, queryParams);

        var consifications = response.getBody();

        Map<Date, List<ConfiscationRecord>> responseMap = new HashMap<>();

        if (consifications != null) {
            Arrays.stream(consifications).forEach(c -> {
                if (!responseMap.containsKey(c.getDate())) {
                    responseMap.put(c.getDate(), new ArrayList<>());
                }
                responseMap.get(c.getDate()).add(c);
            });
        }

        return responseMap;
    }

    public Map<Date, List<AreaRecord>> getOTAreasForDates(LocalDate from, LocalDate to) {
        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(areaUrl)
                .queryParam("date_from", "{date_from}")
                .queryParam("date_to", "{date_to}")
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Token " + govApiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date_from", df.format(from));
        queryParams.put("date_to", df.format(to));

        ResponseEntity<AreaRecord[]> response = template.exchange(url, HttpMethod.GET, httpEntity, AreaRecord[].class, queryParams);

        var areas = response.getBody();

        System.out.println(areas);

        Map<Date, List<AreaRecord>> responseMap = new HashMap<>();

        if (areas != null) {
            Arrays.stream(areas).forEach(c -> {
                if (!responseMap.containsKey(c.getDate())) {
                    responseMap.put(c.getDate(), new ArrayList<>());
                }
                responseMap.get(c.getDate()).add(c);
            });
        }

        return responseMap;
    }

    public Map<Date, List<CadastralStatusRecord>> getOTACadastralStatusForDates(LocalDate from, LocalDate to) {
        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(cadastralRecordsUrl)
                .queryParam("date_from", "{date_from}")
                .queryParam("date_to", "{date_to}")
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Token " + govApiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date_from", df.format(from));
        queryParams.put("date_to", df.format(to));

        ResponseEntity<CadastralStatusRecord[]> response = template.exchange(url, HttpMethod.GET, httpEntity, CadastralStatusRecord[].class, queryParams);

        var cadastralStatusRecords = response.getBody();

        Map<Date, List<CadastralStatusRecord>> responseMap = new HashMap<>();

        if (cadastralStatusRecords != null) {
            Arrays.stream(cadastralStatusRecords).forEach(c -> {
                if (!responseMap.containsKey(c.getDate())) {
                    responseMap.put(c.getDate(), new ArrayList<>());
                }
                responseMap.get(c.getDate()).add(c);
            });
        }

        return responseMap;
    }

    public Map<Date, List<MortgageRecord>> getOTAMortgageForDates(LocalDate from, LocalDate to) {
        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(mortgagesUrl)
                .queryParam("date_from", "{date_from}")
                .queryParam("date_to", "{date_to}")
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Token " + govApiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date_from", df.format(from));
        queryParams.put("date_to", df.format(to));

        ResponseEntity<MortgageRecord[]> response = template.exchange(url, HttpMethod.GET, httpEntity, MortgageRecord[].class, queryParams);

        var mortgages = response.getBody();

        Map<Date, List<MortgageRecord>> responseMap = new HashMap<>();

        if (mortgages != null) {
            Arrays.stream(mortgages).forEach(c -> {
                if (!responseMap.containsKey(c.getDate())) {
                    responseMap.put(c.getDate(), new ArrayList<>());
                }
                responseMap.get(c.getDate()).add(c);
            });
        }

        return responseMap;
    }

    public Map<Date, List<OwnersRecord>> getOTAOwnersForDates(LocalDate from, LocalDate to) {
        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(ownersUrl)
                .queryParam("date_from", "{date_from}")
                .queryParam("date_to", "{date_to}")
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Token " + govApiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date_from", df.format(from));
        queryParams.put("date_to", df.format(to));

        ResponseEntity<OwnersRecord[]> response = template.exchange(url, HttpMethod.GET, httpEntity, OwnersRecord[].class, queryParams);

        var ownersRecords = response.getBody();

        Map<Date, List<OwnersRecord>> responseMap = new HashMap<>();

        if (ownersRecords != null) {
            Arrays.stream(ownersRecords).forEach(c -> {
                if (!responseMap.containsKey(c.getDate())) {
                    responseMap.put(c.getDate(), new ArrayList<>());
                }
                responseMap.get(c.getDate()).add(c);
            });
        }

        return responseMap;
    }

    public Map<Date, List<PropertiesRecord>> getOTAPropertiesForDates(LocalDate from, LocalDate to) {
        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(propertiesUrl)
                .queryParam("date_from", "{date_from}")
                .queryParam("date_to", "{date_to}")
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Token " + govApiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date_from", df.format(from));
        queryParams.put("date_to", df.format(to));

        ResponseEntity<PropertiesRecord[]> response = template.exchange(url, HttpMethod.GET, httpEntity, PropertiesRecord[].class, queryParams);

        var propertiesRecords = response.getBody();

        Map<Date, List<PropertiesRecord>> responseMap = new HashMap<>();

        if (propertiesRecords != null) {
            Arrays.stream(propertiesRecords).forEach(c -> {
                if (!responseMap.containsKey(c.getDate())) {
                    responseMap.put(c.getDate(), new ArrayList<>());
                }
                responseMap.get(c.getDate()).add(c);
            });
        }

        return responseMap;
    }

    public Map<Date, List<PropertyDealingRecord>> getOTAPropertyDealingForDates(LocalDate from, LocalDate to) {
        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(propertyDealingsUrl)
                .queryParam("date_from", "{date_from}")
                .queryParam("date_to", "{date_to}")
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Token " + govApiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date_from", df.format(from));
        queryParams.put("date_to", df.format(to));

        ResponseEntity<PropertyDealingRecord[]> response = template.exchange(url, HttpMethod.GET, httpEntity, PropertyDealingRecord[].class, queryParams);

        var propertyDealingRecords = response.getBody();

        Map<Date, List<PropertyDealingRecord>> responseMap = new HashMap<>();

        if (propertyDealingRecords != null) {
            Arrays.stream(propertyDealingRecords).forEach(c -> {
                if (!responseMap.containsKey(c.getDate())) {
                    responseMap.put(c.getDate(), new ArrayList<>());
                }
                responseMap.get(c.getDate()).add(c);
            });
        }

        return responseMap;
    }



}
