package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Repositories.UserInventoryRepository;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInventoryServiceImplements implements UserInventoryService {
    @Autowired
    UserInventoryRepository userInventoryRepository;

    @Override
    public void inputUser(UserInventory userInventory) {
        userInventoryRepository.save(userInventory);
    }

    @Override
    public void modifyUser(UserInventory userInventory) {
        userInventoryRepository.save(userInventory);
    }

    @Override
    public void deleteUser(String login) {
        UserInventory userInventory = userInventoryRepository.findByLogin(login);
        userInventory.setStatus(false);
        userInventoryRepository.save(userInventory);
    }

    @Override
    public UserInventory findUser(String login) {
        return userInventoryRepository.findByLogin(login);
    }

    @Override
    public List<UserInventory> findAllUsers() {
        return userInventoryRepository.findAll();
    }

    @Override
    public UserInventory findByEmail(String email) {
        return userInventoryRepository.findByEmail(email);
    }

    @Override
    public UserInventory getAuthenticatedUser(Authentication authentication) {
        return userInventoryRepository.findByLogin(authentication.getName());
    }
}
