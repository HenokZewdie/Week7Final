package ResumeSpringPackage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EducationRepository extends CrudRepository<Education, Long> {
    @Query
    List<Education> findByEmail(String email);

}
