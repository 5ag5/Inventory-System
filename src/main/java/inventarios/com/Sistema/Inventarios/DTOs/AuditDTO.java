package inventarios.com.Sistema.Inventarios.DTOs;

import inventarios.com.Sistema.Inventarios.Models.ActionAudit;
import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.NombreTabla;

import java.time.LocalDate;

public class AuditDTO {

    private Long id;
    private ActionAudit actionAudit;
    private String direccionID;
    private LocalDate fechaAuditoria;
    private Long idTabla;
    private Long idUsuario;
    private NombreTabla nombreTabla;

    public AuditDTO(){}

    public AuditDTO(Audit audit) {
    }
}
