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
    private tableNames tableNames;

    public AuditDTO(Audit audit) {
        this.id = audit.getId();
        this.actionAudit = audit.getActionAudit();
        this.direccionID = audit.getDireccionID();
        this.fechaAuditoria = audit.getFechaAuditoria();
        this.idTabla = audit.getIdTabla();
        this.idUsuario = audit.getIdUsuario();
        this.tableNames = audit.getNombreTabla();
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

    public tableNames getNombreTabla() {
        return tableNames;
    }
}
