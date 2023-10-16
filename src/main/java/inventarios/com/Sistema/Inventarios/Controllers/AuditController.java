package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.DTOs.UserDTO;
import inventarios.com.Sistema.Inventarios.ExcelFiles.AuditExcelExporter;
import inventarios.com.Sistema.Inventarios.Models.Audit;
import inventarios.com.Sistema.Inventarios.Models.OptionsReports;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.PDFFiles.AuditPDFExporter;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.OptionsReportService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;


@RestController
public class AuditController {
    private static final Logger logger = LogManager.getLogger(UserInventoryController.class);

    @Autowired
    AuditService auditService;

    @Autowired
    UserInventoryService userInventoryService;

    @Autowired
    OptionsReportService optionsReportService;

    @GetMapping("/audit/allAudits")
    public List<Audit> getAllAudits(){
        return auditService.findAllAudit();
    }

    @GetMapping("audit/auditPerUser")
    public UserDTO getAuditsUser(String userLogin){
        return userInventoryService.findUserDTO(userLogin);
    }

    @GetMapping("api/audit/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        String currentDateTime = dateFormatter.format(new Date());


        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=audit_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);


        List<Audit> listAudit = auditService.findAllAudit();

        AuditExcelExporter excelExporter = new AuditExcelExporter(listAudit);

        logger.info("EXPORTED EXCEL OF AUDIT");

        excelExporter.export(response);
    }

    @PostMapping(path="api/audit/auditPDF")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=audits_.pdf";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        /*LocalDateTime dateTime1 = LocalDateTime.parse(startDate + " 00:00", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate + " 23:59", formatter);*/

        response.setHeader(headerKey,headerValue);

        List<Audit> userAudits = auditService.findAllAudit();
       //Set<Transaction> setTransactions = transactionService.findAccountsByAccountAndDateBetween(account,dateTime1,dateTime2);
        //Set<Transaction> setTransactions = transactionRepository.findAccountsByAccountAndDateBetween(account,dateTime1,dateTime2);

        AuditPDFExporter exporter = new AuditPDFExporter(userAudits);

        logger.info("EXPORTED PDF AUDIT");

        exporter.export(response);
    }


}
