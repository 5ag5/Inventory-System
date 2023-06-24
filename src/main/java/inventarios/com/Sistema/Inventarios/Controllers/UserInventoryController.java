package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.ActionAudit;
import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.tableNames;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
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

    @PostMapping("userInventory/loginRegister")
    public ResponseEntity<Object> registerLogin(@RequestParam String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditLogin = new Audit(
                ActionAudit.LOGIN,
               String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.LOGIN.getIdTable(),
                userTemp.getId(),
                tableNames.LOGIN);

        auditService.saveAudit(auditLogin);

        return new ResponseEntity<>("login registration complete",HttpStatus.ACCEPTED);
    }

    //====================================AUDIT METHODS===============================//♠
    private void registerModifyUser(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);
        Audit auditLogin = new Audit(
                ActionAudit.UPDATE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.USERINVENTORY.getIdTable(),
                userTemp.getId(),
                tableNames.USERINVENTORY);

        auditService.saveAudit(auditLogin);
    }

    private void registerNewUserUser(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);
        Audit auditLogin = new Audit(
                ActionAudit.INSERT,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.USERINVENTORY.getIdTable(),
                0L,
                tableNames.USERINVENTORY);

        auditService.saveAudit(auditLogin);
    }

    private void registerDeleteUser(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);
        Audit auditLogin = new Audit(
                ActionAudit.DELETE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.USERINVENTORY.getIdTable(),
                0L,
                tableNames.USERINVENTORY);

        auditService.saveAudit(auditLogin);
    }


}
