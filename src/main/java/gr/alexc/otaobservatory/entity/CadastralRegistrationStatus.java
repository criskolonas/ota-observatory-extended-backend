package gr.alexc.otaobservatory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CadastralRegistrationStatus implements Comparable<CadastralRegistrationStatus> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String status;

    @Override
    public int compareTo(CadastralRegistrationStatus o) {
        return status.compareTo(o.status);
    }
}
