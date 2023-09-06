package inventarios.com.Sistema.Inventarios.Controllers;

//import ch.qos.logback.classic.db.names.TableName;
import inventarios.com.Sistema.Inventarios.DTOs.ProductDTO;
import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.ProductService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/api/products")
    public List<ProductDTO> getProducts(){
        return productService.getProducts();
    }

    @PatchMapping("/api/products/state")
    public ResponseEntity<Object> changeProductStatus( @RequestParam Long id){
        Product product= productService.finProducById(id);
        if(product==null){
            return new ResponseEntity<>("The product does not exist ", HttpStatus.FORBIDDEN);
        }
        product.setStatusProduct(false);
        productService.inputProduct(product);
        return new ResponseEntity<>("The product was eliminated",HttpStatus.OK);
    }

    @PostMapping("/api/products")
    public ResponseEntity<Object> newProduct(Authentication authentication, @RequestParam String descriptionProduct, @RequestParam int cantidadProduct, @RequestParam double precioCompra, @RequestParam double precioVenta,@RequestParam int minimumStock,@RequestParam int maximumStock,@RequestParam boolean includesIVA){
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
                                                @RequestParam boolean includesIVA){
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
                return new ResponseEntity<>("the information has been modified", HttpStatus.OK);
        }
            return new ResponseEntity<>("the product does not exist", HttpStatus.FORBIDDEN);

    }
}
