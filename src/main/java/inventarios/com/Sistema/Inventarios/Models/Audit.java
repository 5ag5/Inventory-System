package inventarios.com.Sistema.Inventarios.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private ActionAudit actionAudit;
    private String direccionID;
    private LocalDate fechaAuditoria;
    private Long idTabla;
    private Long idUsuario;
    private tableNames tableNames;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;  // Many to One
    @ManyToOne(fetch = FetchType.EAGER)
    private Parameter parameter;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserInventory userInventory; // Many to One

    public  Audit(){

    }

    public Audit(ActionAudit actionAudit, String direccionID,LocalDate fechaAuditoria,  Long idTabla, Long idUsuario, tableNames tableNames) {
        this.actionAudit=actionAudit;
        this.direccionID=direccionID;
        this.fechaAuditoria=fechaAuditoria;
        this.idTabla=idTabla;
        this.idUsuario=idUsuario;
        this.tableNames = tableNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActionAudit getActionAudit() {
        return actionAudit;
    }

    public void setActionAudit(ActionAudit actionAudit) {
        this.actionAudit = actionAudit;
    }

    public String getDireccionID() {
        return direccionID;
    }

    public void setDireccionID(String direccionID) {
        this.direccionID = direccionID;
    }

    public LocalDate getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(LocalDate fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public Long getIdTabla() {
        return idTabla;
    }

    public void setIdTabla(Long idTabla) {
        this.idTabla = idTabla;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public tableNames getNombreTabla() {
        return tableNames;
    }

    public void setNombreTabla(tableNames tableNames) {
        this.tableNames = tableNames;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UserInventory getUser() {
        return userInventory;
    }

    public void setUser(UserInventory userInventory) {
        this.userInventory = userInventory;
    }
}
