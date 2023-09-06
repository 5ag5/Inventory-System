package inventarios.com.Sistema.Inventarios.Controllers;


import inventarios.com.Sistema.Inventarios.DTOs.ParameterDTO;
import inventarios.com.Sistema.Inventarios.Models.*;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.ParameterService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import inventarios.com.Sistema.Inventarios.Utils.ParameterUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ParameterController {
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

        return new ResponseEntity<>("Parameter Modified", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/api/parameter/deleteParameter")
    public ResponseEntity<Object> deleteParameter(Authentication authenticated, @RequestParam String nameParameter) throws UnknownHostException {
        Parameter parameterTemp = parameterService.findParameterByName(nameParameter);
        UserInventory userTemp = userInventoryService.getAuthenticatedUser(authenticated);

        if(parameterTemp == null){
            new ResponseEntity<>("Parameter Doesn't Exist", HttpStatus.FORBIDDEN);
        }

        parameterTemp.setParameterStatus(false);
        parameterService.saveParameter(parameterTemp);

        AuditRegisterDeleteParameter(userTemp.getLogin());
        return new ResponseEntity<>("Parameter Erased", HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/user/changes")
    public ResponseEntity<Object> changeInfo(Authentication authentication,  @RequestParam Long id,
                                             @RequestParam String parameterDescription,
                                             @RequestParam boolean parameterStatus,
                                             @RequestParam String nameParameter,
                                             @RequestParam String  valueParameter){
        Parameter parameter=parameterService.findById(id);
        if(parameter!=null){
            parameter.setParameterDescription(parameterDescription);
            parameter.setParameterStatus(parameterStatus);
            parameter.setNameParameter(nameParameter);
            parameter.setValueParameter(valueParameter);
            parameterService.saveParameter(parameter);
            return new ResponseEntity<>("the information has been modified", HttpStatus.OK);
        }
       return  new ResponseEntity<>("the product does not exist", HttpStatus.FORBIDDEN);
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
