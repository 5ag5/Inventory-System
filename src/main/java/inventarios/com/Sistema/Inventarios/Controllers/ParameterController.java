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

import javax.crypto.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.crypto.Cipher.getInstance;

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

    @GetMapping("/api/parameter/allParameters")
    public List<ParameterDTO> allParametersEndPoint(){
        return parameterService.getAllParameterDTO();
    }

    @PostMapping("/api/parameter/createParameter")
    public ResponseEntity<Object> createParameter(@RequestBody Parameter parameter, Authentication authenticated) throws UnknownHostException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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
    public ResponseEntity<Object> modifyParameter(@RequestParam String nameParameter, @RequestParam String valueParameter,
                                                 Authentication authenticated) throws UnknownHostException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

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

    @PatchMapping("/api/parameter/deleteParameter")
    public ResponseEntity<Object> deleteParameter(Authentication authenticated, @RequestParam String nameParameter) throws UnknownHostException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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

    @GetMapping("api/parameter/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=parameters_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Parameter> listParameter = parameterService.findAllParameters();

        ParameterExcelExporter excelExporter = new ParameterExcelExporter(listParameter);

        logger.info("EXPORTED EXCEL OF PARAMETERS");

        excelExporter.export(response);
    }

    @PostMapping(path="api/parameter/parameterPDF")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=parameters_.pdf";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        /*LocalDateTime dateTime1 = LocalDateTime.parse(startDate + " 00:00", formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate + " 23:59", formatter);*/

        response.setHeader(headerKey, headerValue);

        Set<ParameterDTO> parameterList = parameterService.findAllParameters().stream().map(parameter -> new ParameterDTO(parameter)).collect(Collectors.toSet());

        ParameterPDFExporter exporter = new ParameterPDFExporter(parameterList);

        logger.info("EXPORTED PDF OF PARAMETERS");

        exporter.export(response);
    }

    @PutMapping(path="/api/user/changes")
    public ResponseEntity<Object> changeInfo(Authentication authentication,
                                             @RequestParam Long id,
                                             @RequestParam String parameterDescription,
                                             @RequestParam boolean parameterStatus,
                                             @RequestParam String nameParameter,
                                             @RequestParam String  valueParameter) throws UnknownHostException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserInventory user = userInventoryService.getAuthenticatedUser(authentication);

        Parameter parameter=parameterService.findById(id);
        if(parameter!=null){
            parameter.setParameterDescription(parameterDescription);
            parameter.setParameterStatus(parameterStatus);
            parameter.setNameParameter(nameParameter);
            parameter.setValueParameter(valueParameter);
            parameterService.saveParameter(parameter);
            AuditRegisterModifyParameter(user.getLogin());

            return new ResponseEntity<>("the information has been modified", HttpStatus.OK);
        }
       return  new ResponseEntity<>("the product does not exist", HttpStatus.FORBIDDEN);

    }

    //=========================================AUDIT METHODS=======================================//

    private void AuditRegisterInputParameter(String login) throws UnknownHostException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey myDesKey = keyGenerator.generateKey();
        Cipher desCipher = getInstance("AES");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

        byte []bytesEncrypted = desCipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
        String textEncrypted = new String(bytesEncrypted);

        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.INSERT,
                textEncrypted,
                LocalDate.now(),
                tableNames.CATEGORY.getIdTable(),
                userTemp.getLogin(),
                tableNames.CATEGORY
        );

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);
    }

    private void AuditRegisterModifyParameter(String login) throws UnknownHostException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey myDesKey = keyGenerator.generateKey();
        Cipher desCipher = getInstance("AES");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

        byte []bytesEncrypted = desCipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
        String textEncrypted = new String(bytesEncrypted);

        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.UPDATE,
                textEncrypted,
                LocalDate.now(),
                tableNames.CATEGORY.getIdTable(),
                userTemp.getLogin(),
                tableNames.CATEGORY
        );

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);     }

    private void AuditRegisterDeleteParameter(String login) throws UnknownHostException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey myDesKey = keyGenerator.generateKey();
        Cipher desCipher = getInstance("AES");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

        byte []bytesEncrypted = desCipher.doFinal(String.valueOf(InetAddress.getLocalHost()).getBytes());
        String textEncrypted = new String(bytesEncrypted);

        UserInventory userTemp = userInventoryService.findUser(login);

        Audit auditTemp = new Audit(
                ActionAudit.DELETE,
                textEncrypted,
                LocalDate.now(),
                tableNames.CATEGORY.getIdTable(),
                userTemp.getLogin(),
                tableNames.CATEGORY
        );

        userTemp.addAudit(auditTemp);
        userInventoryService.modifyUser(userTemp);
        auditService.saveAudit(auditTemp);     }

}
