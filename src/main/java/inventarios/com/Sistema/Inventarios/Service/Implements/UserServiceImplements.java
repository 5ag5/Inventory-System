package inventarios.com.Sistema.Inventarios.Service.Implements;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Repositories.UserRepository;
import inventarios.com.Sistema.Inventarios.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplements implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void inputUser(UserInventory userInventory) {
        userRepository.save(userInventory);
    }

    @Override
    public void modifyUser(UserInventory userInventory) {
        userRepository.save(userInventory);
    }

    @Override
    public void deleteUser(String login) {
        UserInventory userInventory = userRepository.findByLogin(login);
        userInventory.setStatus(false);
        userRepository.save(userInventory);
    }

    @Override
    public UserInventory findUser(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<UserInventory> findAllUsers() {
        return userRepository.findAll();
    }
}
