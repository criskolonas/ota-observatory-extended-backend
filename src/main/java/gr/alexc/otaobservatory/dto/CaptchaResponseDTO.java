package gr.alexc.otaobservatory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaptchaResponseDTO {
        private boolean success;
        private String challengeTs;
        private String hostname;
        private double score; // For v3
        private String action; // For v3

        @JsonProperty("success")
        public boolean isSuccess() {
            return success;
        }
}