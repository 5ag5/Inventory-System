package inventarios.com.Sistema.Inventarios.DTOs;

import inventarios.com.Sistema.Inventarios.Models.*;

import java.util.Set;
import java.util.stream.Collectors;

public class ParameterDTO {

    private Long id;
    private String parameterDescription;
    private  boolean parameterStatus;
    private String nameParameter;
    private String  valueParameter;
    private Set<AuditDTO> audits;
    private Set<CategoryDTO> categories;
    private Set<ProductDTO> products;
    private Set<UserDTO> users;

    public ParameterDTO(Parameter parameter) {
        this.id = parameter.getId();
        this.parameterDescription = parameter.getParameterDescription();
        this.parameterStatus = parameter.getParameterStatus();
        this.nameParameter = parameter.getNameParameter();
        this.valueParameter = parameter.getValueParameter();
        this.audits = parameter.getAudits().stream().map(audit-> new AuditDTO(audit)).collect(Collectors.toSet());
        this.categories = parameter.getCategories().stream().map(category -> new CategoryDTO(category)).collect(Collectors.toSet());
        this.products = parameter.getProducts().stream().map(product -> new ProductDTO(product)).collect(Collectors.toSet());
        this.users = parameter.getUsers().stream().map(user -> new UserDTO(user)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getParameterDescription() {
        return parameterDescription;
    }

    public boolean isParameterStatus() {
        return parameterStatus;
    }

    public String getNameParameter() {
        return nameParameter;
    }

    public String getValueParameter() {
        return valueParameter;
    }

    public Set<AuditDTO> getAudits() {
        return audits;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }
}
