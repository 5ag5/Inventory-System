package inventarios.com.Sistema.Inventarios.Controllers;


import inventarios.com.Sistema.Inventarios.DTOs.ParameterDTO;
import inventarios.com.Sistema.Inventarios.DTOs.ProductDTO;
import inventarios.com.Sistema.Inventarios.ExcelFiles.ParameterExcelExporter;
import inventarios.com.Sistema.Inventarios.ExcelFiles.UserExcelExporter;
import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.PDFFiles.ParameterPDFExporter;
import inventarios.com.Sistema.Inventarios.PDFFiles.ProductPDFExporter;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.ParameterService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.Utils.ParameterUtils;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ParameterController {
    private static final Logger logger = LogManager.getLogger(UserInventoryController.class);

    @Autowired
    AuditService auditService;

    @Autowired
    UserInventoryService userInventoryService;

    @Autowired
    ParameterService parameterService;

    @GetMapping("api/parameter/resultTest")
        public boolean getValueTest(@RequestBody Parameter parameter, Authentication authenticated){
        List<ParameterDTO> listParameters = parameterService.getAllParameterDTO();
        return ParameterUtils.foundParameterSearch(listParameters,parameter);
    }

    @GetMapping("api/parameter/allParameters")
    public List<ParameterDTO> allParametersEndPoint(){
        return parameterService.getAllParameterDTO();
    }

    @PostMapping("api/parameter/createParameter")
    public ResponseEntity<Object> createParameter(@RequestBody Parameter parameter, Authentication authenticated) throws UnknownHostException {
        List<ParameterDTO> listParameters = parameterService.getAllParameterDTO();

        UserInventory userLoggedIn = userInventoryService.getAuthenticatedUser(authenticated);

        if(parameter.getParameterDescription().isBlank() || parameter.getNameParameter().isBlank() || parameter.getValueParameter().isBlank()){
            return new ResponseEntity<>("Not all fields are full", HttpStatus.FORBIDDEN);
        }

        if(parameter == null){
            return new ResponseEntity<>("Parameter to me created is null or empty",HttpStatus.CONFLICT);
        }

        if(ParameterUtils.foundParameterSearch(listParameters, parameter) == true){
            return new ResponseEntity<>("Parameter already exists, cannot create it", HttpStatus.FORBIDDEN);
        }

        parameterService.saveParameter(parameter);
        AuditRegisterInputParameter(userLoggedIn.getLogin());

        logger.info("PARAMETER CREATED FOR" + parameter);
        
        return new ResponseEntity<>("Parameter Created", HttpStatus.ACCEPTED);
    }

    @PatchMapping("api/parameter/modifyParameter")
    public ResponseEntity<Object> modifyParamter(@RequestParam String nameParameter, @RequestParam String valueParameter,
                                                 Authentication authenticated) throws UnknownHostException {

        List<ParameterDTO> listParameters = parameterService.getAllParameterDTO();
        UserInventory userLoggedIn = userInventoryService.getAuthenticatedUser(authenticated);
        Parameter parameterTemp = parameterService.findParameterByName(nameParameter);

        if(nameParameter.isBlank() || valueParameter.isBlank()){
            return new ResponseEntity<>("Both values must be filled in",HttpStatus.FORBIDDEN);
        }

        if(parameterTemp == null){
            return new ResponseEntity<>("Parameter to me modified wasn't found",HttpStatus.CONFLICT);
        }

        Parameter parameterToModify = parameterService.findParameterByName(nameParameter);

        parameterToModify.setValueParameter(valueParameter);
        parameterService.saveParameter(parameterToModify);
        AuditRegisterModifyParameter(userLoggedIn.getLogin());

        logger.info("PARAMETER MODIFIED FOR" + nameParameter);

        return new ResponseEntity<>("Parameter Modified", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("api/parameter/deleteParameter")
    public ResponseEntity<Object> deleteParameter(Authentication authenticated, @RequestParam String nameParameter) throws UnknownHostException {
        Parameter parameterTemp = parameterService.findParameterByName(nameParameter);
        UserInventory userTemp = userInventoryService.getAuthenticatedUser(authenticated);

        if(parameterTemp == null){
            new ResponseEntity<>("Parameter Doesn't Exist", HttpStatus.FORBIDDEN);
        }

        parameterTemp.setParameterStatus(false);
        parameterService.saveParameter(parameterTemp);

        AuditRegisterDeleteParameter(userTemp.getLogin());
        logger.info("DELETE PARAMETER FOR" + nameParameter);

        return new ResponseEntity<>("Parameter Erased", HttpStatus.ACCEPTED);
    }

    @GetMapping("/parameter/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Parameter> listParameter = parameterService.findAllParameters();

        ParameterExcelExporter excelExporter = new ParameterExcelExporter(listParameter);

        logger.info("EXPORTED EXCEL OF PARAMETERS");

        excelExporter.export(response);
    }

    @PostMapping(path="/parameter/parameterPDF")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_.pdf";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        /*LocalDateTime dateTime1 = LocalDateTime.parse(startDate + " 00:00", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate + " 23:59", formatter);*/

        response.setHeader(headerKey,headerValue);

        Set<ParameterDTO> parameterList = parameterService.findAllParameters().stream().map(parameter -> new ParameterDTO(parameter)).collect(Collectors.toSet());

        ParameterPDFExporter exporter = new ParameterPDFExporter(parameterList);

        logger.info("EXPORTED PDF OF PARAMETERS");

        exporter.export(response);
    }

    //=========================================AUDIT METHODS=======================================//

    private void AuditRegisterInputParameter(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.INSERT,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.CATEGORY.getIdTable(),
                userTemp.getId(),
                tableNames.CATEGORY
        );

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);
    }

    private void AuditRegisterModifyParameter(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.UPDATE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.CATEGORY.getIdTable(),
                userTemp.getId(),
                tableNames.CATEGORY
        );

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);     }

    private void AuditRegisterDeleteParameter(String login) throws UnknownHostException {
        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.DELETE,
                String.valueOf(InetAddress.getLocalHost()),
                LocalDate.now(),
                tableNames.CATEGORY.getIdTable(),
                userTemp.getId(),
                tableNames.CATEGORY
        );

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);     }

}
