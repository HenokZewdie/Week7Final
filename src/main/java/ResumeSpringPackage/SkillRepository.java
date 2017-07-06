package ResumeSpringPackage;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkillRepository extends CrudRepository<Skill, Integer> {
    List<Skill> findByEmail(String email);
}
