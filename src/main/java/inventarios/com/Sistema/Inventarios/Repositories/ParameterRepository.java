package inventarios.com.Sistema.Inventarios.Repositories;

import inventarios.com.Sistema.Inventarios.Models.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
}
