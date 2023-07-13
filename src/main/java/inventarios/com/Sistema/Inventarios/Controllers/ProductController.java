package inventarios.com.Sistema.Inventarios.Controllers;

//import ch.qos.logback.classic.db.names.TableName;
import inventarios.com.Sistema.Inventarios.DTOs.ProductDTO;
import inventarios.com.Sistema.Inventarios.ExcelFiles.ProductExcelExporter;
import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.PDFFiles.AuditPDFExporter;
import inventarios.com.Sistema.Inventarios.PDFFiles.ProductPDFExporter;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.ProductService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.ExcelFiles.UserExcelExporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private static final Logger logger = LogManager.getLogger(UserInventoryController.class);
    @Autowired
    ProductService productService;
    @Autowired
    AuditService auditService;
    @Autowired
    UserInventoryService userInventoryService;

    @GetMapping("/product/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=product_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Product> listProduct = productService.findAllProducts();

        ProductExcelExporter excelExporter = new ProductExcelExporter(listProduct);

        logger.info("EXPORTED EXCEL OF PRODUCTS");

        excelExporter.export(response);
    }

    @PostMapping(path="/product/productPDF")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_.pdf";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        /*LocalDateTime dateTime1 = LocalDateTime.parse(startDate + " 00:00", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate + " 23:59", formatter);*/

        response.setHeader(headerKey,headerValue);

        Set<ProductDTO> productList = productService.findAllProducts().stream().map(product -> new ProductDTO(product)).collect(Collectors.toSet());

        ProductPDFExporter exporter = new ProductPDFExporter(productList);

        logger.info("EXPORTED PDF OF PRODUCTS");

        exporter.export(response);
    }

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
