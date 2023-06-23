package inventarios.com.Sistema.Inventarios.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nameCategory;
    private boolean stateCategory;
    @ManyToOne(fetch = FetchType.EAGER)
    private Parameter parameter;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<Audit> audits;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<Product> products; //One to Many

    public Category() {
    }

    public Category(String nameCategory, boolean stateCategory) {
        this.nameCategory = nameCategory;
        this.stateCategory = stateCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Set<Audit> getAudits() {
        return audits;
    }

    public void setAudits(Set<Audit> audits) {
        this.audits = audits;
    }

    public boolean getStateCategory() {
        return stateCategory;
    }

    public void setStateCategory(boolean stateCategory) {
        this.stateCategory = stateCategory;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public  void addAudit(Audit audit){
        audit.setCategory(this);
        audits.add(audit);

    }

    public void addProduct(Product product){
        product.setCategory(this);
        products.add(product);

    }
}
