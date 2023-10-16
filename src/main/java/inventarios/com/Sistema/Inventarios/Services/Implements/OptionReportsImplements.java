package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.Models.OptionsReports;
import inventarios.com.Sistema.Inventarios.Repositories.OptionsReportsRepository;
import inventarios.com.Sistema.Inventarios.Services.OptionsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionReportsImplements implements OptionsReportService {

    @Autowired
    OptionsReportsRepository optionsReportsRepository;
    @Override
    public void saveReportOption(OptionsReports optionsReports) {
        optionsReportsRepository.save(optionsReports);
    }

    @Override
    public List<OptionsReports> getAllReportsInformation() {
        return optionsReportsRepository.findAll();
    }

    @Override
    public OptionsReports findById(Long id) {
        OptionsReports options = optionsReportsRepository.findById(id).orElse(null);
        return options;
    }

    @Override
    public OptionsReports findByEntityAndReport(String entity, String report) {
        return optionsReportsRepository.findByentityTypeAndReportType(entity, report);
    }

    @Override
    public void deleteOptionReport(OptionsReports optionsReports) {
        optionsReportsRepository.delete(optionsReports);
    }

    @Override
    public void modifyOptionReport(OptionsReports optionsReports) {

    }
}
