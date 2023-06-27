package inventarios.com.Sistema.Inventarios.Repositories;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserInventoryRepository extends JpaRepository<UserInventory, Long> {
    UserInventory findByLogin(String login);

    UserInventory findByEmail(String email);
}
