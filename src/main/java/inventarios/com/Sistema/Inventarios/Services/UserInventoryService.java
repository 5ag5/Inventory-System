package inventarios.com.Sistema.Inventarios.Services;

import inventarios.com.Sistema.Inventarios.DTOs.UserDTO;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserInventoryService {
    public static final int MAX_FAILED_ATTEMPTS = 3;

    void inputUser(UserInventory userInventory);

    void modifyUser(UserInventory userInventory);

    void deleteUser(String login);
    UserInventory findUser(String login);
    List<UserInventory> findAllUsers();

    UserInventory findByEmail(String email);

    UserInventory getAuthenticatedUser(Authentication authentication);


    UserDTO findUserDTO(String userLogin);
}
