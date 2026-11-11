
package springboot.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import springboot.learning.models.tododata;

import java.util.List;
import java.util.Optional;

@Component
public interface todorepository extends JpaRepository<tododata, Long> {

    List<tododata> findAllByUser_Email(String email);

    Optional<tododata> findByIdAndUser_Email(Long id, String email);


}
