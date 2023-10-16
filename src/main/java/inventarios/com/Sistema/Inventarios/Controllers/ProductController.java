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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
public class ProductController {
    private static final Logger logger = LogManager.getLogger(UserInventoryController.class);
    @Autowired
    ProductService productService;
    @Autowired
    AuditService auditService;
    @Autowired
    UserInventoryService userInventoryService;

    @GetMapping("api/product/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Product> listProduct = productService.findAllProducts();

        ProductExcelExporter excelExporter = new ProductExcelExporter(listProduct);

        logger.info("EXPORTED EXCEL OF PRODUCTS");

        excelExporter.export(response);
    }

    @PostMapping(path="api/product/productPDF")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products_.pdf";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        /*LocalDateTime dateTime1 = LocalDateTime.parse(startDate + " 00:00", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate + " 23:59", formatter);*/

        response.setHeader(headerKey,headerValue);

        Set<ProductDTO> productList = productService.findAllProducts().stream().map(product -> new ProductDTO(product)).collect(Collectors.toSet());

        ProductPDFExporter exporter = new ProductPDFExporter(productList);

        logger.info("EXPORTED PDF OF PRODUCTS");

        exporter.export(response);
    }



    @GetMapping("/api/products")
    public List<ProductDTO> getProducts(){
        return productService.getProducts();
    }

    @PatchMapping("/api/products/state")
    public ResponseEntity<Object> changeProductStatus(Authentication authentication, @RequestParam Long id) throws UnknownHostException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserInventory user = userInventoryService.getAuthenticatedUser(authentication);
        Product product= productService.finProducById(id);
        if(product==null){
            return new ResponseEntity<>("The product does not exist ", HttpStatus.FORBIDDEN);
        }
        product.setStatusProduct(false);
        productService.inputProduct(product);
        deleteProductAudit(user.getLogin());
        return new ResponseEntity<>("The product was eliminated",HttpStatus.OK);
    }

    @PostMapping("/api/products")
    public ResponseEntity<Object> newProduct(Authentication authentication, @RequestParam String descriptionProduct, @RequestParam int cantidadProduct, @RequestParam double precioCompra, @RequestParam double precioVenta,@RequestParam int minimumStock,@RequestParam int maximumStock,@RequestParam boolean includesIVA) throws UnknownHostException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserInventory user = userInventoryService.getAuthenticatedUser(authentication);
        if(descriptionProduct.isBlank()){
            return new ResponseEntity<>("The description cannot be empty",HttpStatus.FORBIDDEN);
        }
        if(cantidadProduct<=0){
            return new ResponseEntity<>("The description cannot be empty",HttpStatus.FORBIDDEN);
        }
        if(precioCompra<=0){
            return new ResponseEntity<>("The description cannot be empty",HttpStatus.FORBIDDEN);
        }
        if(precioVenta<=0){
            return new ResponseEntity<>("The description cannot be empty",HttpStatus.FORBIDDEN);
        }
        if(minimumStock<=0){
            return new ResponseEntity<>("The description cannot be empty",HttpStatus.FORBIDDEN);
        }
        if(maximumStock<=0){
            return new ResponseEntity<>("The description cannot be empty",HttpStatus.FORBIDDEN);
        }
        if( !((Object)includesIVA).getClass().getSimpleName().equals("Boolean")){
            return new ResponseEntity<>("IVA cannot be empty",HttpStatus.FORBIDDEN);
        }
        Product product=new Product(descriptionProduct,  cantidadProduct,  precioCompra,  precioVenta, minimumStock,  maximumStock,  includesIVA);
        productService.inputProduct(product);
        registerProductAudit(user.getLogin());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/products/modifyProduct")
    public ResponseEntity<Object> modifyProduct(Authentication authentication, @RequestParam Long id,
                                                @RequestParam String descriptionProduct,
                                                @RequestParam boolean statusProduct,
                                                @RequestParam int cantidadProduct,
                                                @RequestParam double precioCompra,
                                                @RequestParam double precioVenta,
                                                @RequestParam int minimumStock,
                                                @RequestParam  int maximumStock,
                                                @RequestParam boolean includesIVA) throws UnknownHostException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        UserInventory user = userInventoryService.getAuthenticatedUser(authentication);

        Product product= productService.finProducById(id);
        if(product!=null){
                product.setDescriptionProduct(descriptionProduct);
                product.setStatusProduct(statusProduct);
                product.setCantidadProduct(cantidadProduct);
                product.setPrecioCompra(precioCompra);
                product.setPrecioVenta(precioVenta);
                product.setMaximumStock(minimumStock);
                product.setMinimumStock(maximumStock);
                product.setIncludesIVA(includesIVA);
                productService.inputProduct(product);
                modifyProductAudit(user.getLogin());

                return new ResponseEntity<>("the information has been modified", HttpStatus.OK);
        }
            return new ResponseEntity<>("the product does not exist", HttpStatus.FORBIDDEN);

    }

    //=================================AUDIT METHODS==============================//

    private void registerProductAudit(String login) throws UnknownHostException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte [] bytesEncrypted = cipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
        String textEncrypted = new String(bytesEncrypted);

        UserInventory user = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.INSERT,
                textEncrypted,
                LocalDate.now(),
                tableNames.PRODUCT.getIdTable(),
                user.getLogin(),
                tableNames.PRODUCT
        );

        user.addAudit(auditTemp);
        userInventoryService.modifyUser(user);
        auditService.saveAudit(auditTemp);
    }

    private void modifyProductAudit(String login) throws UnknownHostException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte [] bytesEncrypted = cipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
        String textEncrypted = new String(bytesEncrypted);

        UserInventory user = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.UPDATE,
                textEncrypted,
                LocalDate.now(),
                tableNames.PRODUCT.getIdTable(),
                user.getLogin(),
                tableNames.PRODUCT
        );

        user.addAudit(auditTemp);
        userInventoryService.modifyUser(user);
        auditService.saveAudit(auditTemp);
    }

    private void deleteProductAudit(String login) throws UnknownHostException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte [] encryptedBytes = cipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
        String textEncrypted = new String(encryptedBytes);

        UserInventory user = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.DELETE,
                textEncrypted,
                LocalDate.now(),
                tableNames.PRODUCT.getIdTable(),
                user.getLogin(),
                tableNames.PRODUCT
        );

        user.addAudit(auditTemp);
        userInventoryService.modifyUser(user);
        auditService.saveAudit(auditTemp);
    }
}
