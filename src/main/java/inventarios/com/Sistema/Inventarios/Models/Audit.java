package inventarios.com.Sistema.Inventarios.Models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parent;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


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
    private NombreTabla nombreTabla;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;  // Many to One
    private Parameter parameter;
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user; // Many to One

    public  Audit(){

    }

    public Audit(ActionAudit actionAudit, String direccionID,LocalDate fechaAuditoria,  Long idTabla, Long idUsuario, NombreTabla nombreTabla) {
        this.actionAudit=actionAudit;
        this.direccionID=direccionID;
        this.fechaAuditoria=fechaAuditoria;
        this.idTabla=idTabla;
        this.idUsuario=idUsuario;
        this.nombreTabla=nombreTabla;

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

    public NombreTabla getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(NombreTabla nombreTabla) {
        this.nombreTabla = nombreTabla;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
