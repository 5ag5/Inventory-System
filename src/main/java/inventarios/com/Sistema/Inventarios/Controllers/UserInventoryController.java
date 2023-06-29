package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.Utils.UserInventoryUtils;
import inventarios.com.Sistema.Inventarios.Utils.LogType;
import inventarios.com.Sistema.Inventarios.Utils.UtilesLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;

@RestController
public class UserInventoryController {
    @Autowired
    UserInventoryService userInventoryService;

    @Autowired
    AuditService auditService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //private static final Logger logger = LogManager.getLogger(UserInventoryController.class);

    @PostMapping("api/userInventory/loginRegister")
    public ResponseEntity<Object> registerLogin(@RequestParam String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditLogin = new Audit(
                ActionAudit.LOGIN,
               String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.LOGIN.getIdTable(),
                userTemp.getId(),
                tableNames.LOGIN);

        userTemp.addAudit(auditLogin);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditLogin);

        UtilesLog.registratinInfo(UserInventory.class, LogType.INFO,"Usuario Logeado");
        UtilesLog.registratinInfo(UserInventory.class, LogType.DEBUG,"Usuario Logeado");
        UtilesLog.registratinInfo(UserInventory.class, LogType.WARNING,"Usuario Logeado");
        UtilesLog.registratinInfo(UserInventory.class, LogType.ERROR,"Usuario Logeado");
        UtilesLog.registratinInfo(UserInventory.class, LogType.FATAL,"Usuario Logeado");

        //logger.debug("esta funcionando");

        return new ResponseEntity<>("login registration complete",HttpStatus.ACCEPTED);
    }



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

    //====================================AUDIT METHODS===============================//♠
    private void registerModifyUser(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);
        Audit auditTemp = new Audit(
                ActionAudit.UPDATE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.USERINVENTORY.getIdTable(),
                userTemp.getId(),
                tableNames.USERINVENTORY);

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);
    }

    private void registerNewUserUser(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);
        Audit auditTemp = new Audit(
                ActionAudit.INSERT,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.USERINVENTORY.getIdTable(),
                0L,
                tableNames.USERINVENTORY);

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);
    }

    private void registerDeleteUser(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);
        Audit auditTemp = new Audit(
                ActionAudit.DELETE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.USERINVENTORY.getIdTable(),
                0L,
                tableNames.USERINVENTORY);

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);
    }



}
