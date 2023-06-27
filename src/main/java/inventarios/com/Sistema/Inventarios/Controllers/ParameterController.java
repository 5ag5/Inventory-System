package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.Parameter;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Services.ParamertService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.Utilities.ParameterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParameterController {

    @Autowired
    private UserInventoryService userInventoryService;

    @Autowired
    private ParamertService paramertService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PatchMapping("/api/users/password")
    public ResponseEntity<Object> changePassword(Authentication authentication, @RequestParam String password){
        UserInventory userInventory=userInventoryService.getAuthenticatedUser(authentication);
            if (password.length() < 5 || password.length() > 8) {
                return new ResponseEntity<>("The password cannot be less than 5 or more than 8 characters long.", HttpStatus.FORBIDDEN);
            }
            if (!password.matches(".*[0-9].*")) {
                return new ResponseEntity<>("The password must contain at least one numeral.", HttpStatus.FORBIDDEN);
            }
            if (!password.matches(".*[A-Z].*")) {
                return new ResponseEntity<>("The password must contain at least one capital letter.", HttpStatus.FORBIDDEN);
            }
            if (!password.matches(".*[a-z].*")) {
                return new ResponseEntity<>("The password must contain at least one lowercase letter.", HttpStatus.FORBIDDEN);
            }
            if(password.equals(userInventory.getPassword())){
                return new ResponseEntity<>("The password must be different.", HttpStatus.FORBIDDEN);
            }
            userInventory.setPassword(password);
            userInventoryService.inputUser(userInventory);
            return new ResponseEntity<>("password has been changed", HttpStatus.OK);
    }



}
