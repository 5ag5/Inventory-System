package inventarios.com.Sistema.Inventarios.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String parameterDescription;
    private  boolean parameterStatus;
    private String nameParameter;
    private String  valueParameter;

    @OneToMany(mappedBy = "parameter", fetch = FetchType.EAGER)
    private Set<Audit> audits = new HashSet<>();
    @OneToMany(mappedBy = "parameter", fetch = FetchType.EAGER)
    private Set<Category> categories = new HashSet<>();
    @OneToMany(mappedBy = "parameter", fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>();
    @OneToMany(mappedBy = "parameter", fetch = FetchType.EAGER)
    private Set<UserInventory> userInventories = new HashSet<>();

    public Parameter(){

    }

    public Parameter(String parameterDescription, boolean parameterStatus, String nameParameter, String valueParameter) {
        this.parameterDescription = parameterDescription;
        this.parameterStatus = parameterStatus;
        this.nameParameter = nameParameter;
        this.valueParameter = valueParameter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameterDescription() {
        return parameterDescription;
    }

    public void setParameterDescription(String parameterDescription) {
        this.parameterDescription = parameterDescription;
    }

    public boolean getParameterStatus() {
        return parameterStatus;
    }

    public void setParameterStatus(boolean parameterStatus) {
        this.parameterStatus = parameterStatus;
    }

    public String getNameParameter() {
        return nameParameter;
    }

    public void setNameParameter(String nameParameter) {
        this.nameParameter = nameParameter;
    }

    public String getValueParameter() {
        return valueParameter;
    }

    public void setValueParameter(String valueParameter) {
        this.valueParameter = valueParameter;
    }

    public Set<Audit> getAudits() {
        return audits;
    }

    public void setAudits(Set<Audit> audits) {
        this.audits = audits;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<UserInventory> getUsers() {
        return userInventories;
    }

    public void setUsers(Set<UserInventory> userInventories) {
        this.userInventories = userInventories;
    }

    public void addAudit(Audit audit){
        audit.setParameter(this);
        audits.add(audit);
    }


   public void addProduct(Product product){
        product.setParameter(this);
        products.add(product);
    }


    public void addCategory(Category category){
        category.setParameter(this);
        categories.add(category);
    }

}
