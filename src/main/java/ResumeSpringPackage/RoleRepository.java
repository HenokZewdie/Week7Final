package ResumeSpringPackage;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by student on 7/5/17.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRole(String role);
}