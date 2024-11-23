package gr.alexc.otaobservatory.entity.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "greece_areas_view")
@Immutable
@Getter
@Setter
public class GreeceAreaView {

    @Id
    private Long id;

    private Long regionId;

    private Long prefectureId;

    private String name;

    private String type;


}
