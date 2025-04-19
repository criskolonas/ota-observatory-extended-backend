package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.CaptchaRequestDTO;
import gr.alexc.otaobservatory.exception.CaptchaFailException;
import gr.alexc.otaobservatory.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController{

    private final CaptchaService captchaService;

    @Autowired
    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @PostMapping("/recaptcha")
    public ResponseEntity<CaptchaRequestDTO> verifyCaptcha(@RequestBody CaptchaRequestDTO request) {
        boolean isValid = captchaService.verifyCaptcha(request.getCaptchaResponse());

        if (!isValid) {
            throw new CaptchaFailException("Bad captcha response");
        }

        return ResponseEntity.ok(request);
    }
}