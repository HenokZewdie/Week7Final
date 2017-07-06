package ResumeSpringPackage;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by student on 6/28/17.
 */
public interface ExperienceRepository extends CrudRepository<Experience, Integer>{
    List<Experience> findByEmail(String email);
    List<Experience> findByCompany(String company);
}
