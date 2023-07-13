package inventarios.com.Sistema.Inventarios.Services;

import inventarios.com.Sistema.Inventarios.DTOs.AuditDTO;
import inventarios.com.Sistema.Inventarios.Models.Audit;

import java.util.List;

public interface AuditService {

    Audit findById(Long id);

    AuditDTO findByIdDTO(Long id);

    List<AuditDTO> getAllAuditDTO();

    List<Audit> findAllAudit();

    void saveAudit(Audit audit);


}
