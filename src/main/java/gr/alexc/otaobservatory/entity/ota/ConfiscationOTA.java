package gr.alexc.otaobservatory.entity.ota;

import gr.alexc.otaobservatory.entity.OTA;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
// Πλήθος Κατασχέσεων
public class ConfiscationOTA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long confiscations;

    private LocalDate createDate;

    private LocalDate updateDate;

    private LocalDate lastCheckDate;

    @ManyToOne
    private OTA ota;

}
