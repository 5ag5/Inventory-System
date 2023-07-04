package inventarios.com.Sistema.Inventarios.Controllers;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Services.AuditService;
import inventarios.com.Sistema.Inventarios.Services.ProductService;
import inventarios.com.Sistema.Inventarios.Services.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class GraficiosControlador {
    @Autowired
    UserInventoryService userInventoryService;
    @Autowired
    AuditService auditService;

    @Autowired
    ProductService productService;

    @GetMapping("api/graphs/UserGraphsCount")
    public String [][]  listaPrueba(@RequestParam String nameArgument){

        List<UserInventory> namesCategories =  userInventoryService.findAllUsers();
        Set <String> namesArgument = new HashSet<>();
        List<String> elementsArguments = new ArrayList<>();
        int sizeArray =0;

        switch(nameArgument){
            case "Date Registered":
                for(UserInventory userTemp:namesCategories) {
                    namesArgument.add(String.valueOf(userTemp.getDateRegistered()));
                    elementsArguments.add(String.valueOf(userTemp.getDateRegistered()));
                }
            break;
            case "status":
                for(UserInventory userTemp:namesCategories) {
                    namesArgument.add(String.valueOf(userTemp.isStatus()));
                    elementsArguments.add(String.valueOf(userTemp.isStatus()));
                }
                break;
            case "Last Registered Date":
                for(UserInventory userTemp:namesCategories) {
                    namesArgument.add(String.valueOf(userTemp.getLastRegisteredPassword()));
                    elementsArguments.add(String.valueOf(userTemp.getLastRegisteredPassword()));
                }
                break;
            case "User Type":
                for(UserInventory userTemp:namesCategories) {
                    namesArgument.add(String.valueOf(userTemp.getUserType()));
                    elementsArguments.add(String.valueOf(userTemp.getUserType()));
                }
                break;
        }

        if(namesArgument.size() == 1) {
            sizeArray = namesArgument.size()+1;
        }else{
            sizeArray = namesArgument.size();
        }

        String[][] arrayAnswer = new String[sizeArray][2];

        arrayAnswer[0][0] = nameArgument;
        arrayAnswer[0][1] = "count";

        ArrayList <String> arrayTemp = new ArrayList<>(namesArgument);

        arrayAnswer[1][0] = arrayTemp.get(0);

        for(int i=1; i < arrayTemp.size();i++){
                arrayAnswer[i][0] = arrayTemp.get(i);
                arrayAnswer[i][1] = "0";
        }

        if(arrayAnswer[1][1] == null){
            arrayAnswer[1][1] = "0";
        }

        for(String element: elementsArguments){
            for(int i=1; i < arrayAnswer.length;i++) {
                if(arrayAnswer[i][0].equals(element)){
                    arrayAnswer[i][1] = String.valueOf(Integer.parseInt(arrayAnswer[i][1]) +1);
                }
            }
        }

        return arrayAnswer;
    }

    

    /*
    @GetMapping("/api/graficos/graficoLineas")
    public Object[][] listaMesesFinal() {
        List<HistoriaClinicaDTO> listaHistorias = historiaClinicaServicios.todasLasHistoriasClincias();
        List<Integer> listaMeses = new ArrayList<>();

        for (HistoriaClinicaDTO historia:listaHistorias) {
            listaMeses.add(historia.getFechaConsulta().getMonthValue());
        }

        Object[][] arrayCompleto = new Object[listaMeses.size()][2];


        int febrero = 0, marzo = 0, abril = 0, mayo = 0, junio = 0;

        for (Integer mes:listaMeses) {
            switch (mes) {
                case 2:
                    febrero = febrero + 1;
                    break;
                case 3:
                    marzo = marzo + 1;
                    break;
                case 4:
                    abril = abril + 1;
                    break;
                case 5:
                    mayo = mayo + 1;
                    break;
                case 6:
                    junio = junio + 1;
                    break;
            }
        }

        arrayCompleto[0][0] = "mes";
        arrayCompleto[1][0] = "febrero";
        arrayCompleto[2][0] = "marzo";
        arrayCompleto[3][0] = "abril";
        arrayCompleto[4][0] = "mayo";
        arrayCompleto[5][0] = "junio";

        arrayCompleto[0][1] = "numero";
        arrayCompleto[1][1] = febrero;
        arrayCompleto[2][1] = marzo;
        arrayCompleto[3][1] = abril;
        arrayCompleto[4][1] = mayo;
        arrayCompleto[5][1] = junio;

        return arrayCompleto;
    }

    @GetMapping("/api/graficos/graficoPie1")
    public Object[][] graficoHistorias() {
        List<UsuarioDTO> listaUsrs = usuariosServicios.getTodosLosUsuarios();
        List<UsuarioTipo> listaTipo = new ArrayList<>();

        for (UsuarioDTO usuarioTipo:listaUsrs) {
            listaTipo.add(usuarioTipo.getUsuarioTipo());
        }

        Object[][] arrayCompleto = new Object[listaTipo.size()][2];

        int admin = 0, doctor = 0, enfermera = 0, supervisor = 0;

        for (UsuarioTipo tipo:listaTipo) {
            switch (tipo) {
                case ADMIN:
                    admin = admin + 1;
                    break;
                case DOCTOR:
                    doctor = doctor + 1;
                    break;
                case ENFERMERA:
                    enfermera = enfermera + 1;
                    break;
                case SUPERVISOR:
                    supervisor = supervisor + 1;
                    break;
            }
        }

        arrayCompleto[0][0] = "Tipo";
        arrayCompleto[1][0] = "admin";
        arrayCompleto[2][0] = "doctor";
        arrayCompleto[3][0] = "enfermera";
        arrayCompleto[4][0] = "supervisor";

        arrayCompleto[0][1] = "numero";
        arrayCompleto[1][1] = admin;
        arrayCompleto[2][1] = doctor;
        arrayCompleto[3][1] = enfermera;
        arrayCompleto[4][1] = supervisor;

        return arrayCompleto;
    }

    @GetMapping("/api/graficos/graficoPie2")
    public Object[][] graficoTipos() {
        List<AuditoriaDTO> listaAuditorias = auditoriaServicios.todasAuditorias();
        List<AccionTipo> listaTipos = new ArrayList<>();

        for (AuditoriaDTO auditoria : listaAuditorias) {
            listaTipos.add(auditoria.getTipoDeAccion());
        }

        int insert = 0, update = 0, delete = 0, login = 0, exit = 0;
        Object[][] arrayCompleto = new Object[6][2];

        for (AccionTipo tipo:listaTipos) {
            switch (tipo) {
                case INSERT:
                    insert = insert + 1;
                    break;
                case DELETE:
                    delete = delete + 1;
                    break;
                case UPDATE:
                    update = update + 1;
                    break;
                case EXIT:
                    exit = exit + 1;
                    break;
                case LOGIN:
                    login = login + 1;
                    break;
            }
        }
        arrayCompleto[0][0] = "Tipo";
        arrayCompleto[1][0] = "INSERT";
        arrayCompleto[2][0] = "DELETE";
        arrayCompleto[3][0] = "UPDATE";
        arrayCompleto[4][0] = "EXIT";
        arrayCompleto[5][0] = "LOGIN";

        arrayCompleto[0][1] = "Suma";
        arrayCompleto[1][1] = insert;
        arrayCompleto[2][1] = delete;
        arrayCompleto[3][1] = update;
        arrayCompleto[4][1] = exit;
        arrayCompleto[5][1] = login;

        return arrayCompleto;
    }*/
}
