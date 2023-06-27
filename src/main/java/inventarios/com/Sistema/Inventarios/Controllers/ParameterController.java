package inventarios.com.Sistema.Inventarios.Controllers;


import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;

@RestController
public class ParameterController {
    @Autowired
    AuditService auditService;

    @Autowired
    UserInventoryService userInventoryService;

    @PostMapping
    public ResponseEntity<Object> createParameter(@RequestBody Parameter parameter){

        if(parameter.getParameterDescription().isBlank() || parameter.getNameParameter().isBlank() || parameter.getValueParameter().isBlank()){
            return new ResponseEntity<>("Not all fields are full", HttpStatus.FORBIDDEN);
        }
        
        return new ResponseEntity<>("Parameter Created", HttpStatus.ACCEPTED);
    }

    //=========================================AUDIT METHODS=======================================//

    private void registroIngresarParameter(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.INSERT,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.CATEGORY.getIdTable(),
                userTemp.getId(),
                tableNames.CATEGORY
        );

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);
    }

    private void registroModificarParameter(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.UPDATE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.CATEGORY.getIdTable(),
                userTemp.getId(),
                tableNames.CATEGORY
        );

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);     }

    private void registroBorrarParameter(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.DELETE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.CATEGORY.getIdTable(),
                userTemp.getId(),
                tableNames.CATEGORY
        );

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);     }

}
