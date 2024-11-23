package gr.alexc.otaobservatory.entity.ota;

import gr.alexc.otaobservatory.entity.CadastralRegistrationStatus;
import gr.alexc.otaobservatory.entity.OTA;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
// Εμβαδό Οριζοντίων
public class AreaOTA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double area;

    private LocalDate createDate;

    private LocalDate updateDate;

    private LocalDate lastCheckDate;

    @ManyToOne
    private OTA ota;
}
