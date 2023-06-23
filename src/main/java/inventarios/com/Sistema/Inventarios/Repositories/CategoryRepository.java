package inventarios.com.Sistema.Inventarios.Repositories;

import inventarios.com.Sistema.Inventarios.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
