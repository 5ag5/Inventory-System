package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.ActionAudit;
import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.tableNames;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.CategoryService;
import inventarios.com.Sistema.Inventarios.Services.ProductService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;

@RestController
public class CategoryController {
@Autowired
AuditService auditService;

@Autowired
    CategoryService categoryService;

@Autowired
    UserInventoryService userInventoryService;

//=========================================AUDIT METHODS=======================================//

    private void registroIngresarCategory(String login) throws UnknownHostException {
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
        auditService.saveAudit(auditTemp);     }

    private void registroModificarCategory(String login) throws UnknownHostException {
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

    private void registroBorrarCategory(String login) throws UnknownHostException {
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
        auditService.saveAudit(auditTemp);
    }
}
