package gr.alexc.otaobservatory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;

@Entity
@Table(name = "OTA")
@Getter
@Setter
public class OTA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "geom_reoriented", columnDefinition = "public.geometry(multipolygon, 4326)")
    private MultiPolygon geom;

    private String otaCode;

    private String prefectureCode;

    private String otaId;

    private String name;

    private String nameEn;

    private String shapeStar;

    private String shapeStle;

    @ManyToOne
    private Prefecture prefecture;

}
