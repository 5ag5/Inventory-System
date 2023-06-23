package inventarios.com.Sistema.Inventarios.Services;

import inventarios.com.Sistema.Inventarios.DTO.AuditDTO;
import inventarios.com.Sistema.Inventarios.Models.Audit;

import java.util.List;

public interface AuditService {

    Audit findById(Long id);

    List<AuditDTO> getAllAuditDTO();

    void saveAudit(Audit audit);


}
