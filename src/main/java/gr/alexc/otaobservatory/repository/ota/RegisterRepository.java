package gr.alexc.otaobservatory.repository.ota;

import gr.alexc.otaobservatory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<User, Long> {

}
