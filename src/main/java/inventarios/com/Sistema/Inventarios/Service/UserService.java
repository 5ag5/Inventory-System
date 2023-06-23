package inventarios.com.Sistema.Inventarios.Service;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;

import java.util.List;

public interface UserService {
    void inputUser(UserInventory userInventory);

    void modifyUser(UserInventory userInventory);

    void deleteUser(String login);
    UserInventory findUser(String login);
    List<UserInventory> findAllUsers();
}
