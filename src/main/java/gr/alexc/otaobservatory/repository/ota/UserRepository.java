package gr.alexc.otaobservatory.repository.ota;

import gr.alexc.otaobservatory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
            select * from ota_observatory.user
            where (email = :email and password = :password)
            limit 1;
            """, nativeQuery = true)
    User getUser(
            @Param("email") String email,
            @Param("password") String password
    );

    @Query(value = """
            select * from ota_observatory.user
            where (email = :email )
            limit 1;
            """, nativeQuery = true)
    User getUserByEmail(
            @Param("email") String email
    );

    @Query(value = """
            select * from ota_observatory.user
            where (token = :token )
            limit 1;
            """, nativeQuery = true)
    User getUserByExpirationToken(
            @Param("token") String token
    );
}
