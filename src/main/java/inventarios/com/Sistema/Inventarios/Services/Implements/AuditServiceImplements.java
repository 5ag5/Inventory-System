package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.DTOs.AuditDTO;
import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Repositories.AuditRepository;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditServiceImplements implements AuditService {

    @Autowired
    private AuditRepository auditRepository;
    @Override
    public Audit findById(Long id) {
        return auditRepository.findById(id).orElse(null);
    }

    @Override
    public List<AuditDTO> getAllAuditDTO() {
        return auditRepository.findAll().stream().map(audit-> new AuditDTO(audit)).collect(Collectors.toList());
    }

    @Override
    public List<Audit> findAllAudit(){
        return auditRepository.findAll();
    }

    @Override
    public void saveAudit(Audit audit) {
        auditRepository.save(audit);
    }
}
