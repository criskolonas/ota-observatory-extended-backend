package gr.alexc.otaobservatory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.Set;

@Entity
@Getter
@Setter
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "public.geometry(multipolygon, 2100)")
    private MultiPolygon geom;

    @OneToMany(mappedBy= "region")
    private Set<Prefecture> prefectures;

    private Double unemploymentRate;
    private Long criminalityRate;
}
