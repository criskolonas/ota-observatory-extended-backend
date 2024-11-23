package gr.alexc.otaobservatory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.Set;

@Entity
@Getter
@Setter
public class Prefecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameGr;

    private String nameEn;

    private Long population;

    private String parent;

    private String esyeId;

    private Double shapeLeng;

    private Double shapeArea;

    @Column(name = "geom_reoriented", columnDefinition = "public.geometry(multipolygon, 4326)")
    private MultiPolygon geom;

    @ManyToOne
    private Region region;

    @OneToOne
    private PrefectureCapital capital;

    @OneToMany(mappedBy= "prefecture")
    private Set<OTA> OTAs;
}
