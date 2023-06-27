package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.UserType;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
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
                                           @RequestParam String password,
                                           @RequestParam String email,
                                           @RequestParam UserType userType){
        if(login.isBlank()){
            return new ResponseEntity<>("The name can't be empty", HttpStatus.FORBIDDEN);
        }
        if(lastName.isBlank()){
            return new ResponseEntity<>("The last name can't be empty", HttpStatus.FORBIDDEN);
        }
        if(password.isBlank()){
            return new ResponseEntity<>("The password can't be empty", HttpStatus.FORBIDDEN);
        }
        if(password.length()<5 || password.length()>8){
            return new ResponseEntity<>("The password cannot be less than 5 or more than 8 characters long.", HttpStatus.FORBIDDEN);
        }
        if(!password.matches(".*[0-9].*")){
            return new ResponseEntity<>("The password must contain at least one numeral.", HttpStatus.FORBIDDEN);
        }
        if(!password.matches(".*[A-Z].*")){
            return new ResponseEntity<>("The password must contain at least one capital letter.", HttpStatus.FORBIDDEN);
        }
        if(!password.matches(".*[a-z].*")){
            return new ResponseEntity<>("The password must contain at least one lowercase letter.", HttpStatus.FORBIDDEN);
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
        UserInventory userInventory= new UserInventory(login,lastName, passwordEncoder.encode(password),email,userType);
        userInventoryService.inputUser(userInventory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
