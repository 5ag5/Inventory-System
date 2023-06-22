package inventarios.com.Sistema.Inventarios.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.nio.MappedByteBuffer;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name="native",strategy = "native")
    @Id
    private Long id;
    private String descriptionProduct;
    private boolean statusProduct;
    private int cantidadProduct;
    private double precioCompra;
    private double precioVenta;
    private int minimumStock;
    private int maximumStock;
    private boolean includesIVA;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_ID")
    private Category category;

    @OneToMany(mappedBy ="audit", fetch=FetchType.EAGER)
    private Set<Audit> auditories = new HashSet<>();

    public Product(){}

    public Product(String descriptionProduct, int cantidadProduct, double precioCompra, double precioVenta, int minimumStock, int maximumStock, boolean includesIVA) {
        this.descriptionProduct = descriptionProduct;
        this.statusProduct = true;
        this.cantidadProduct = cantidadProduct;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.minimumStock = minimumStock;
        this.maximumStock = maximumStock;
        this.includesIVA = includesIVA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public boolean isStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(boolean statusProduct) {
        this.statusProduct = statusProduct;
    }

    public int getCantidadProduct() {
        return cantidadProduct;
    }

    public void setCantidadProduct(int cantidadProduct) {
        this.cantidadProduct = cantidadProduct;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(int minimumStock) {
        this.minimumStock = minimumStock;
    }

    public int getMaximumStock() {
        return maximumStock;
    }

    public void setMaximumStock(int maximumStock) {
        this.maximumStock = maximumStock;
    }

    public boolean isIncludesIVA() {
        return includesIVA;
    }

    public void setIncludesIVA(boolean includesIVA) {
        this.includesIVA = includesIVA;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Audit> getAuditories() {
        return auditories;
    }

    public void setAuditories(Set<Audit> auditories) {
        this.auditories = auditories;
    }

    public void addProduct(Audit audit){
        audit.setProduct(this);
        auditories.add(audit);
    }
}
