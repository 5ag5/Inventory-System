package inventarios.com.Sistema.Inventarios.Services;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;

import java.util.List;

public interface UserInventoryService {
    void inputUser(UserInventory userInventory);

    void modifyUser(UserInventory userInventory);

    void deleteUser(String login);
    UserInventory findUser(String login);
    List<UserInventory> findAllUsers();
}
