package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.configuration.CaptchaConfig;
import gr.alexc.otaobservatory.dto.CaptchaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {

    private final CaptchaConfig captchaConfig;

    @Autowired
    public CaptchaService(CaptchaConfig captchaConfig) {
        this.captchaConfig = captchaConfig;
    }

    public boolean verifyCaptcha(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", captchaConfig.getRecaptchaSecret());
        requestMap.add("response", captchaResponse);

        CaptchaResponseDTO response = restTemplate.postForObject(
                captchaConfig.getRecaptchaVerifyUrl(),
                requestMap,
                CaptchaResponseDTO.class
        );

        return response != null && response.isSuccess();
    }
}
