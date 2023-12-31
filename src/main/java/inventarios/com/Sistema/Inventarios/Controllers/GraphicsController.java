package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.Product;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.slicePie;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.ProductService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.Utils.GraphsUtils;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class GraphicsController {
    private static final Logger logger = LogManager.getLogger(UserInventoryController.class);

    @Autowired
    UserInventoryService userInventoryService;
    @Autowired
    AuditService auditService;

    @Autowired
    ProductService productService;

    @GetMapping("/testGraph")
    public String showHome() {
        return "index";
    }

    @GetMapping("api/graphs/UserGraphsCount/{nameArgument}")
    public String[][] listaPrueba(@PathVariable String nameArgument) {

        List<UserInventory> namesCategories = userInventoryService.findAllUsers();
        Set<String> namesArgument = new HashSet<>();
        List<String> elementsArguments = new ArrayList<>();
        int sizeArray = 0;

        switch (nameArgument) {
            case "Date Registered":
                for (UserInventory userTemp : namesCategories) {
                    namesArgument.add(String.valueOf(userTemp.getDateRegistered()));
                    elementsArguments.add(String.valueOf(userTemp.getDateRegistered()));
                }
                break;
            case "status":
                for (UserInventory userTemp : namesCategories) {
                    namesArgument.add(String.valueOf(userTemp.isStatus()));
                    elementsArguments.add(String.valueOf(userTemp.isStatus()));
                }
                break;
            case "Last Registered Date":
                for (UserInventory userTemp : namesCategories) {
                    namesArgument.add(String.valueOf(userTemp.getLastRegisteredPassword()));
                    elementsArguments.add(String.valueOf(userTemp.getLastRegisteredPassword()));
                }
                break;
            case "User Type":
                for (UserInventory userTemp : namesCategories) {
                    namesArgument.add(String.valueOf(userTemp.getUserType()));
                    elementsArguments.add(String.valueOf(userTemp.getUserType()));
                }
                break;
        }

        logger.info("User Graphs Created");

        return GraphsUtils.get2DGraph(namesArgument, elementsArguments, nameArgument);
    }

    @GetMapping("api/graphs/auditGraphsCount/{nameArgument}")
    public String[][] graphAudit(@PathVariable String nameArgument) {

        List<Audit> namesCategories = auditService.findAllAudit();
        Set<String> namesArgument = new HashSet<>();
        List<String> elementsArguments = new ArrayList<>();
        int sizeArray = 0;

        switch (nameArgument) {
            case "Action Audit":
                for (Audit auditTemp : namesCategories) {
                    namesArgument.add(String.valueOf(auditTemp.getActionAudit()));
                    elementsArguments.add(String.valueOf(auditTemp.getActionAudit()));
                }
                break;
            case "Fecha Auditoria":
                for (Audit auditTemp : namesCategories) {
                    namesArgument.add(String.valueOf(auditTemp.getFechaAuditoria()));
                    elementsArguments.add(String.valueOf(auditTemp.getFechaAuditoria()));
                }
                break;
            case "id Table":
                for (Audit auditTemp : namesCategories) {
                    namesArgument.add(String.valueOf(auditTemp.getIdTabla()));
                    elementsArguments.add(String.valueOf(auditTemp.getIdTabla()));
                }
                break;
            case "ID User":
                for (Audit auditTemp : namesCategories) {
                    namesArgument.add(String.valueOf(auditTemp.getIdUsuario()));
                    elementsArguments.add(String.valueOf(auditTemp.getIdUsuario()));
                }
                break;
            case "Table Names":
                for (Audit auditTemp : namesCategories) {
                    namesArgument.add(String.valueOf(auditTemp.getNombreTabla()));
                    elementsArguments.add(String.valueOf(auditTemp.getNombreTabla()));
                }
                break;
        }

        logger.info("Audit Graphs Created");

        return GraphsUtils.get2DGraph(namesArgument, elementsArguments, nameArgument);
    }

    @GetMapping("api/graphs/ProductGraphsCount/{nameArgument}")
    public String[][] getProductsCounts(@PathVariable String nameArgument) {

        List<Product> namesCategories = productService.findAllProducts();
        Set<String> namesArgument = new HashSet<>();
        List<String> elementsArguments = new ArrayList<>();
        int sizeArray = 0;

        switch (nameArgument) {
            case "Status Product":
                for (Product productTemp : namesCategories) {
                    namesArgument.add(String.valueOf(productTemp.isStatusProduct()));
                    elementsArguments.add(String.valueOf(productTemp.isStatusProduct()));
                }
                break;
            case "Quantity Product":
                for (Product productTemp : namesCategories) {
                    namesArgument.add(String.valueOf(productTemp.getCantidadProduct()));
                    elementsArguments.add(String.valueOf(productTemp.getCantidadProduct()));
                }
                break;
            case "Price Purchase":
                for (Product productTemp : namesCategories) {
                    namesArgument.add(String.valueOf(productTemp.getPrecioCompra()));
                    elementsArguments.add(String.valueOf(productTemp.getPrecioCompra()));
                }
                break;
            case "Precio Venta":
                for (Product productTemp : namesCategories) {
                    namesArgument.add(String.valueOf(productTemp.getPrecioVenta()));
                    elementsArguments.add(String.valueOf(productTemp.getPrecioVenta()));
                }
                break;
            case "Minimum Stock":
                for (Product productTemp : namesCategories) {
                    namesArgument.add(String.valueOf(productTemp.getMinimumStock()));
                    elementsArguments.add(String.valueOf(productTemp.getMinimumStock()));
                }
                break;

            case "Maximum Stock":
                for (Product productTemp : namesCategories) {
                    namesArgument.add(String.valueOf(productTemp.getMaximumStock()));
                    elementsArguments.add(String.valueOf(productTemp.getMaximumStock()));
                }
                break;

            case "Includes IVA":
                for (Product productTemp : namesCategories) {
                    namesArgument.add(String.valueOf(productTemp.isIncludesIVA()));
                    elementsArguments.add(String.valueOf(productTemp.isIncludesIVA()));
                }
                break;
        }

        logger.info("Audit Products Created");

        return GraphsUtils.get2DGraph(namesArgument, elementsArguments, nameArgument);

    }

    @GetMapping("api/graphs/UserPieGraph/{nameArgument}")
    public List<slicePie> getPieUsersInfo(@PathVariable String nameArgument) {
        List<UserInventory> listUsers = userInventoryService.findAllUsers();
        List<String> pieInfo = new ArrayList<>();
        Set<String> nameParameters = new HashSet<>();


        switch (nameArgument) {
            case "User Type":
                for (UserInventory user : listUsers) {
                    pieInfo.add(String.valueOf(user.getUserType()));
                    nameParameters.add(String.valueOf(user.getUserType()));
                }
            break;

            case "status":
                for (UserInventory user : listUsers) {
                    pieInfo.add(String.valueOf(user.isStatus()));
                    nameParameters.add(String.valueOf(user.getUserType()));
                }
            break;
        }

        return GraphsUtils.getPieChart(pieInfo,nameParameters);
    }
}