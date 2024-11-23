package gr.alexc.otaobservatory.entity;

import gr.alexc.otaobservatory.enums.OTAVariable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GovApiCall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date timestamp;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String otaVariable;

//    @ManyToOne
//    private OtaVariable otaVariable;

//    @Enumerated(EnumType.STRING)
//    private OTAVariable otaVariable;

}
