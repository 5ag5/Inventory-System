package inventarios.com.Sistema.Inventarios.DTOs;

import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.Product;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDTO {

    private Long id;
    private String descriptionProduct;
    private boolean statusProduct;
    private int cantidadProduct;
    private double precioCompra;
    private double precioVenta;
    private int minimumStock;
    private int maximumStock;
    private boolean includesIVA;
    private Set<AuditDTO> auditories = new HashSet<>();

    public ProductDTO(){}

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.descriptionProduct = product.getDescriptionProduct();
        this.statusProduct = product.isStatusProduct();
        this.cantidadProduct = product.getCantidadProduct();
        this.precioCompra = product.getPrecioCompra();
        this.precioVenta = product.getPrecioVenta();
        this.minimumStock = product.getMinimumStock();
        this.maximumStock = product.getMaximumStock();
        this.includesIVA = product.isIncludesIVA();
        this.auditories = product.getAuditories().stream().map(audit -> new AuditDTO(audit)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public Set<AuditDTO> getAuditories() {
        return auditories;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public boolean isStatusProduct() {
        return statusProduct;
    }

    public int getCantidadProduct() {
        return cantidadProduct;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public int getMaximumStock() {
        return maximumStock;
    }

    public boolean isIncludesIVA() {
        return includesIVA;
    }
}
