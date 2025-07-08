package gr.alexc.otaobservatory.repository.ota;

import gr.alexc.otaobservatory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            select * from ota_observatory.user
            where (email = :email )
            limit 1;
            """, nativeQuery = true)
    Optional<User> getUserByEmail(
            @Param("email") String email
    );

    @Query(value = """
            select * from ota_observatory.user
            where (token = :token )
            limit 1;
            """, nativeQuery = true)
    Optional<User> getUserByExpirationToken(
            @Param("token") String token
    );
}
