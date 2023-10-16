package inventarios.com.Sistema.Inventarios.Services;

import inventarios.com.Sistema.Inventarios.Models.OptionsGraphs;
import inventarios.com.Sistema.Inventarios.Models.OptionsReports;

import java.util.List;

public interface OptionsReportService {
    void saveReportOption(OptionsReports optionsReports);
    List<OptionsReports> getAllReportsInformation();
    OptionsReports findById(Long id);

    OptionsReports findByEntityAndReport(String entity, String report);

    void deleteOptionReport(OptionsReports optionsReports);

    void modifyOptionReport(OptionsReports optionsReports);


}
