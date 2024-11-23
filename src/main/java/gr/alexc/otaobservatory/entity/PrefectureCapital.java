package gr.alexc.otaobservatory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
public class PrefectureCapital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nameEn;

    private Long population;

    private Double area;

    @Column(columnDefinition = "public.geometry(point, 4326)")
    private Point geom;

}
