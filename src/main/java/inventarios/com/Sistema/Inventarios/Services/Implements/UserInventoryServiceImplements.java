package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.DTOs.UserDTO;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Repositories.UserInventoryRepository;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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

    @Override
    public UserDTO getUserDTO(Authentication authentication) {
        return new UserDTO(userInventoryRepository.findByLogin(authentication.getName()));
    }

    @Override
    public List<UserDTO> getUsersDTO() {
        return userInventoryRepository.findAll().stream().map(userInventory -> new UserDTO(userInventory)).collect(Collectors.toList());
    }


}
