package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.GraphicsOptionsService;
import inventarios.com.Sistema.Inventarios.Services.ProductService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.Utils.GraphsUtils;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.weaver.loadtime.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class GraphicsController {
    private static final Logger logger = LogManager.getLogger(UserInventoryController.class);

    @Autowired
    UserInventoryService userInventoryService;
    @Autowired
    AuditService auditService;

    @Autowired
    ProductService productService;

    @Autowired
    GraphicsOptionsService graphicsOptionsService;

    @GetMapping("/testGraph")
    public String showHome() {
        return "index";
    }

    @GetMapping("api/graphs/optionsTests")
    public List<OptionsGraphs> optiionsGraphsx(){
        return graphicsOptionsService.findAllGraphOptions();
    }

    @GetMapping("api/graphs/optionsLineGraphs")
    public Map<String, String []> optionsGraphs(){
        Map<String, String []> options = new HashMap<>();
        options.put("Users", new String[] {"Date Registered", "status", "Last Registered Date", "User Type"});
        options.put("Products", new String[] {"Audit", "ID User", "Audit Date"});
        options.put("Audits", new String[] {"Status Product", "Quantity Product", "Price Purchase", "Price Sell", "Minimum Stock", "Maximum Stock", "Includes IVA"});
        return options;
    }

    @GetMapping("api/graphs/optionsPieChart")
    public  Map<String, String []> optionsPieChart(){
        Map<String, String []> options = new HashMap<>();
        options.put("Users", new String[] {"User Type", "status"});
        options.put("Products", new String[] {"Audit", "ID User", "Audit Date"});
        options.put("Audits", new String[] {"Status", "Includes IVA", "Category Product"});

        return options;
    }

    @GetMapping("api/graphs/UserGraphsCount/{nameArgument}")
    public String[][] userGraph(@PathVariable String nameArgument) {

        List<UserInventory> namesCategories = userInventoryService.findAllUsers();
        Set<String> namesArgument = new HashSet<>();
        List<String> elementsArguments = new ArrayList<>();

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
            case "Audit Date":
                for (Audit auditTemp : namesCategories) {
                    namesArgument.add(String.valueOf(auditTemp.getFechaAuditoria()));
                    elementsArguments.add(String.valueOf(auditTemp.getFechaAuditoria()));
                }
                break;
            case "ID Table":
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
            case "Computer IPs":
                for(Audit audit: namesCategories){
                    namesArgument.add(String.valueOf(audit.getDireccionID()));
                    elementsArguments.add(String.valueOf(audit.getDireccionID()));
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
            case "Price Sell":
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

            case "Status":
                for (UserInventory user : listUsers) {
                    pieInfo.add(String.valueOf(user.isStatus()));
                    nameParameters.add(String.valueOf(user.isStatus()));
                }
            break;
        }

        return GraphsUtils.getPieChart(pieInfo,nameParameters);
    }

    @GetMapping("api/graphs/AuditPieChart/{nameArgument}")
    public List<slicePie> getPieAuditInfo(@PathVariable String nameArgument){
        List<Audit> listAudits = auditService.findAllAudit();
        List<String> pieInfo = new ArrayList<>();
        Set<String> nameParameters = new HashSet<>();

        switch (nameArgument){
            case "Action":
                for(Audit audit: listAudits){
                    pieInfo.add(String.valueOf(audit.getActionAudit()));
                    nameParameters.add(String.valueOf(audit.getActionAudit()));
                }
            break;

            case "ID User":
                for(Audit audit: listAudits){
                    pieInfo.add(String.valueOf(audit.getIdUsuario()));
                    nameParameters.add(String.valueOf(audit.getIdUsuario()));
                }
            break;

            case "Audit Date":
                for(Audit audit: listAudits){
                    pieInfo.add(String.valueOf(audit.getFechaAuditoria()));
                    nameParameters.add(String.valueOf(audit.getFechaAuditoria()));
                }
            break;
        }
        return GraphsUtils.getPieChart(pieInfo,nameParameters);
    }

    @GetMapping("api/graphs/ProductPieChart/{nameArgument}")
    public List<slicePie> getPieProductInfo(@PathVariable String nameArgument){
    List<Product> listProducts = productService.findAllProducts();
    List<String> pieInfo = new ArrayList<>();
    Set<String> nameParameters = new HashSet<>();

    switch(nameArgument){
        case "Status":
            for(Product product: listProducts){
                pieInfo.add(String.valueOf(product.isStatusProduct()));
                nameParameters.add(String.valueOf(product.isStatusProduct()));
            }
        break;
        case "Includes IVA":
            for(Product product: listProducts){
                pieInfo.add(String.valueOf(product.isIncludesIVA()));
                nameParameters.add(String.valueOf(product.isIncludesIVA()));
            }
        break;
        case "Category Product":
            for(Product product:listProducts){
                pieInfo.add(String.valueOf(product.getCategory()));
                nameParameters.add(String.valueOf(product.getCategory()));
            }
        break;
    }
    return GraphsUtils.getPieChart(pieInfo, nameParameters);
    }


}