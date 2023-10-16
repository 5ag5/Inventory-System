package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.OptionsReports;
import inventarios.com.Sistema.Inventarios.Services.OptionsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportsController {
@Autowired
OptionsReportService optionsReportService;

@GetMapping("api/reports/getReportsInformation")
public List<OptionsReports> getAllOptionsReports(){
    return optionsReportService.getAllReportsInformation();
}

}
