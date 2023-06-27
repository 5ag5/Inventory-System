package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.UserType;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.Utilities.UserInventoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired UserInventoryService userInventoryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/users")
    public ResponseEntity<Object> register(@RequestParam String login,
                                           @RequestParam  String lastName,
                                           @RequestParam String email,
                                           @RequestParam UserType userType){
        if(login.isBlank()){
            return new ResponseEntity<>("The name can't be empty", HttpStatus.FORBIDDEN);
        }
        if(lastName.isBlank()){
            return new ResponseEntity<>("The last name can't be empty", HttpStatus.FORBIDDEN);
        }
        if(email.isBlank()){
            return new ResponseEntity<>("The password can't be empty", HttpStatus.FORBIDDEN);
        }
        if(!email.contains("@")){
            return new ResponseEntity<>("The email is not valid", HttpStatus.FORBIDDEN);
        }
        if(userInventoryService.findUser(login)!=null){
            return new ResponseEntity<>("The user name is already in use", HttpStatus.FORBIDDEN);
        }
        if(userInventoryService.findByEmail(email)!=null){
            return new ResponseEntity<>("The email already in use", HttpStatus.FORBIDDEN);
        }
        if(userType.equals(null)){
            return new ResponseEntity<>("The user type can't be empty", HttpStatus.FORBIDDEN);
        }
        UserInventory userInventory= new UserInventory(login,lastName, passwordEncoder.encode(UserInventoryUtils.generateRandomPassword(8)),email,userType);
        userInventoryService.inputUser(userInventory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
