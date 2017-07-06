package ResumeSpringPackage;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Meeliana on 7/5/2017.
 */
public interface JobRepository extends CrudRepository<Job, Long> {
List<Job> findByTitle(String title);
}

