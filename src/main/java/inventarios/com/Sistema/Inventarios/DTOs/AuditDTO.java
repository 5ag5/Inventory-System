package inventarios.com.Sistema.Inventarios.DTOs;

import inventarios.com.Sistema.Inventarios.Models.*;

import java.time.LocalDate;

public class AuditDTO {
    private Long id;
    private ActionAudit actionAudit;
    private String direccionID;
    private LocalDate fechaAuditoria;
    private Long idTabla;
    private Long idUsuario;
    private NombreTabla nombreTabla;
    private Category category;
    private Parameter parameter;
    private Product product;
    private UserInventory user;

    public AuditDTO(Audit audit) {
        this.id = audit.getId();
        this.actionAudit = audit.getActionAudit();
        this.direccionID = audit.getDireccionID();
        this.fechaAuditoria = audit.getFechaAuditoria();
        this.idTabla = audit.getIdTabla();
        this.idUsuario = audit.getIdUsuario();
        this.nombreTabla = audit.getNombreTabla();
        this.category = audit.getCategory();
        this.parameter = audit.getParameter();
        this.product = audit.getProduct();
        this.user = audit.getUser();
    }

    public Long getId() {
        return id;
    }

    public ActionAudit getActionAudit() {
        return actionAudit;
    }

    public String getDireccionID() {
        return direccionID;
    }

    public LocalDate getFechaAuditoria() {
        return fechaAuditoria;
    }

    public Long getIdTabla() {
        return idTabla;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public NombreTabla getNombreTabla() {
        return nombreTabla;
    }

    public Category getCategory() {
        return category;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public Product getProduct() {
        return product;
    }

    public UserInventory getUser() {
        return user;
    }
}
