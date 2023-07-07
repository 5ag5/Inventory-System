package inventarios.com.Sistema.Inventarios.Repositories;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserInventoryRepository extends JpaRepository<UserInventory, Long> {
    UserInventory findByLogin(String login);

    UserInventory findByEmail(String email);

    @Query("UPDATE UserInventory u SET u.numberOfLoginTries=?1 WHERE u.login=?2")
    @Modifying
    void updateNumberOfLoginTries(int numberOfLoginTries, String login);
}
