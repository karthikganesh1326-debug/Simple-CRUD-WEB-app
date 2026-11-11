package springboot.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import springboot.learning.models.userdata;

import java.util.Optional;

@Component
public interface userrepository extends JpaRepository<userdata , Long > {

    Optional<userdata> findByEmail(String email);
}
