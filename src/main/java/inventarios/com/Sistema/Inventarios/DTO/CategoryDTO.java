package inventarios.com.Sistema.Inventarios.DTO;

import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.Category;
import inventarios.com.Sistema.Inventarios.Models.Parameter;
import inventarios.com.Sistema.Inventarios.Models.Product;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryDTO {

    private Long id;

    private String nameCategory;

    private boolean stateCategory;

    private Parameter parameter;

    private Set<AuditDTO> audits;

   // private Set<ProductDTO> products; //One to Many

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.nameCategory = category.getNameCategory();
        this.stateCategory = category.getStateCategory();
        this.parameter = category.getParameter();
        this.audits = category.getAudits().stream().map(audit-> new AuditDTO(audit)).collect(Collectors.toSet());
       // this.products =category.getProducts().stream().map(product -> new ProductDTO(product)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public boolean isStateCategory() {
        return stateCategory;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public Set<AuditDTO> getAudits() {
        return audits;
    }

   /* public Set<ProductDTO> getProducts() {
        return products;
    }*/
}
