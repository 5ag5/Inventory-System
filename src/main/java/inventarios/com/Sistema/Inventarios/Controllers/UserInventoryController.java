package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.DTOs.EmailDTO;

import inventarios.com.Sistema.Inventarios.DTOs.ProductDTO;

import inventarios.com.Sistema.Inventarios.DTOs.UserDTO;
import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.PDFFiles.ProductPDFExporter;
import inventarios.com.Sistema.Inventarios.PDFFiles.UserPDFExporter;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.EmailService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.ExcelFiles.UserExcelExporter;
import inventarios.com.Sistema.Inventarios.Utils.UserInventoryUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.crypto.Cipher.getInstance;


@RestController
public class UserInventoryController {

    private static final Logger logger = LogManager.getLogger(UserInventoryController.class);

    @Autowired
    UserInventoryService userInventoryService;

    @Autowired
    AuditService auditService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("api/userInventory/loginRegister")
    public ResponseEntity<Object> registerLogin(@RequestParam String login) throws UnknownHostException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey myDesKey = keyGenerator.generateKey();
        Cipher desCipher = getInstance("AES");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

        byte []bytesEncrypted = desCipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
        String textEncrypted = new String(bytesEncrypted);

        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditLogin = new Audit(
                ActionAudit.LOGIN,
                textEncrypted,
                LocalDate.now(),
                tableNames.LOGIN.getIdTable(),
                userTemp.getId(),
                tableNames.LOGIN);

        userTemp.addAudit(auditLogin);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditLogin);

        /*UtilesLog.registratinInfo(UserInventory.class, LogType.INFO,"Usuario Logeado");
        UtilesLog.registratinInfo(UserInventory.class, LogType.DEBUG,"Usuario Logeado");
        UtilesLog.registratinInfo(UserInventory.class, LogType.WARNING,"Usuario Logeado");
        UtilesLog.registratinInfo(UserInventory.class, LogType.ERROR,"Usuario Logeado");
        UtilesLog.registratinInfo(UserInventory.class, LogType.FATAL,"Usuario Logeado");
         */

        //logger.debug("esta funcionando");

        logger.info("USER REGISTERED");

        return new ResponseEntity<>("login registration complete",HttpStatus.ACCEPTED);
    }



    @PostMapping("/api/users")
    public ResponseEntity<Object> register(@RequestParam String login,
                                           @RequestParam  String lastName,
                                           @RequestParam String email,
                                           @RequestParam UserType userType) throws UnknownHostException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String passwordBeforeEncrypting=UserInventoryUtils.generateRandomPassword(8);
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
        UserInventory userInventory= new UserInventory(login,lastName, passwordEncoder.encode(passwordBeforeEncrypting),email,userType);
        userInventoryService.inputUser(userInventory);
        EmailDTO emailDTO= new EmailDTO(passwordBeforeEncrypting, email);
        emailService.sendEmail(emailDTO);

        logger.info("NEW USER REGISTERED");
        registerNewUserUser(login);

        return new ResponseEntity<>("Se envio la contraseña a su correo "+ passwordBeforeEncrypting,HttpStatus.CREATED);
    }



    @PatchMapping("/api/users/password")
    public ResponseEntity<Object> changePassword(Authentication authentication, @RequestParam String password, @RequestParam String oldPassword) throws UnknownHostException {
        UserInventory userInventory=userInventoryService.getAuthenticatedUser(authentication);
        if(!passwordEncoder.matches(oldPassword,userInventory.getPassword())){
            return new ResponseEntity<>("Incorrect password above, please correct it..", HttpStatus.FORBIDDEN);
        }
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
        userInventory.setPassword(passwordEncoder.encode(password));
        userInventoryService.inputUser(userInventory);

        logger.info("PASSWORD CHANGED");
        registerModifyUser(userInventory.getLogin());

        return new ResponseEntity<>("password has been changed", HttpStatus.OK);
    }

    @PatchMapping("/api/users/validateAttempts")

    public ResponseEntity<Object> validateAttempts(@RequestParam String login){

        UserInventory userInventory= userInventoryService.findUser(login);
        if(userInventory.getNumberOfLoginTries()>=3){
            userInventory.setStatus(false);
            userInventoryService.inputUser(userInventory);
            return  new ResponseEntity<>("Exceeded the allowed attempts. Your account has been blocked.", HttpStatus.FORBIDDEN);

        }

        int updatedAttempts=userInventory.getNumberOfLoginTries()+1;
        userInventory.setNumberOfLoginTries(updatedAttempts);
        userInventoryService.inputUser(userInventory);
        logger.info("VALIDATED LOGIN ATTEMTPS");

        return  new ResponseEntity<>("Failed login attempts." +userInventory.getNumberOfLoginTries(), HttpStatus.FORBIDDEN);
    }

    @PatchMapping("api/users/blockUser")
    public ResponseEntity<Object> blockUser(@RequestParam String login) throws UnknownHostException {
        UserInventory userInventory= userInventoryService.findUser(login);

        if(userInventory == null){
            new ResponseEntity<>("User Not Found", HttpStatus.FORBIDDEN);
        }

        userInventory.setStatus(false);
        userInventoryService.inputUser(userInventory);

        logger.info("USER BLOCKED");
        registerModifyUser(login);

        return  new ResponseEntity<>("User Blocked" +userInventory.getNumberOfLoginTries(), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/api/users/activateAccount ")
    public ResponseEntity<Object> activateAccount(String login) throws UnknownHostException {
        UserInventory userInventory= userInventoryService.findUser(login);
        if(!userInventory.isStatus()){
            userInventory.setStatus(true);
            userInventory.setNumberOfLoginTries(0);
            userInventoryService.inputUser(userInventory);
            return  new ResponseEntity<>("The account has been activated", HttpStatus.FORBIDDEN);

        }

        logger.info("ADMIN ACTIVATED ACCOUNT");
        registerModifyUser(login);

        return  new ResponseEntity<>("The account is active." +userInventory.getNumberOfLoginTries(), HttpStatus.FORBIDDEN);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<UserInventory> listUsers = userInventoryService.findAllUsers();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        logger.info("USERS EXPORTED TO EXCEL");
        excelExporter.export(response);
    }

    @PostMapping(path="/user/usersPDF")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_.pdf";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        /*LocalDateTime dateTime1 = LocalDateTime.parse(startDate + " 00:00", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate + " 23:59", formatter);*/

        response.setHeader(headerKey,headerValue);

        Set<UserDTO> userList = userInventoryService.findAllUsers().stream().map(user -> new UserDTO(user)).collect(Collectors.toSet());

        UserPDFExporter exporter = new UserPDFExporter(userList);
        exporter.export(response);
    }

    @GetMapping("/api/users")
    public List<UserDTO> getUsers(){
        return userInventoryService.getUsersDTO();
    }

    @GetMapping("/api/user/current")
    public UserDTO getUser(Authentication authentication){
        return userInventoryService.getUserDTO(authentication);
    }


    //====================================AUDIT METHODS==========================================
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

    private void registerNewUserUser(String login) throws UnknownHostException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey myDesKey = keyGenerator.generateKey();
        Cipher desCipher = getInstance("AES");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

        byte []bytesEncrypted = desCipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
        String textEncrypted = new String(bytesEncrypted);

        UserInventory userTemp = userInventoryService.findUser(login);
        Audit auditTemp = new Audit(
                ActionAudit.INSERT,
                textEncrypted,
                LocalDate.now(),
                tableNames.USERINVENTORY.getIdTable(),
                0L,
                tableNames.USERINVENTORY);

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);
    }

    private void registerDeleteUser(String login) throws UnknownHostException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey myDesKey = keyGenerator.generateKey();
        Cipher desCipher = getInstance("AES");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

        byte []bytesEncrypted = desCipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
        String textEncrypted = new String(bytesEncrypted);

        UserInventory userTemp = userInventoryService.findUser(login);
        Audit auditTemp = new Audit(
                ActionAudit.DELETE,
                textEncrypted,
                LocalDate.now(),
                tableNames.USERINVENTORY.getIdTable(),
                0L,
                tableNames.USERINVENTORY);

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);
    }



}
