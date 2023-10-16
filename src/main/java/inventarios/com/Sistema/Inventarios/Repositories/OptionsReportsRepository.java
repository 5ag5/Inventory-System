package inventarios.com.Sistema.Inventarios.Repositories;

import inventarios.com.Sistema.Inventarios.Models.OptionsReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OptionsReportsRepository extends JpaRepository<OptionsReports, Long> {
    OptionsReports findByentityTypeAndReportType(String entity, String reportType);

}
