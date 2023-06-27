package inventarios.com.Sistema.Inventarios.Controllers;

import ch.qos.logback.classic.db.names.TableName;
import inventarios.com.Sistema.Inventarios.Models.ActionAudit;
import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.tableNames;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.ProductService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    AuditService auditService;
    @Autowired
    UserInventoryService userInventoryService;

    //=================================AUDIT METHODS==============================//

    private void registerProductAudit(String login) throws UnknownHostException {
        UserInventory user = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.INSERT,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.PRODUCT.getIdTable(),
                user.getId(),
                tableNames.PRODUCT
        );

        user.addAudit(auditTemp);
        userInventoryService.modifyUser(user);
        auditService.saveAudit(auditTemp);
    }

    private void modifyProductAudit(String login) throws UnknownHostException{
        UserInventory user = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.UPDATE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.PRODUCT.getIdTable(),
                user.getId(),
                tableNames.PRODUCT
        );

        user.addAudit(auditTemp);
        userInventoryService.modifyUser(user);
        auditService.saveAudit(auditTemp);
    }

    private void deleteProductAudit(String login) throws UnknownHostException{
        UserInventory user = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.DELETE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.PRODUCT.getIdTable(),
                user.getId(),
                tableNames.PRODUCT
        );

        user.addAudit(auditTemp);
        userInventoryService.modifyUser(user);
        auditService.saveAudit(auditTemp);
    }

}
