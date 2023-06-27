package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.ActionAudit;
import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.tableNames;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.Utils.LogType;
import inventarios.com.Sistema.Inventarios.Utils.UtilesLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
