package inventarios.com.Sistema.Inventarios.Utils;

import inventarios.com.Sistema.Inventarios.DTOs.ParameterDTO;
import inventarios.com.Sistema.Inventarios.Models.Parameter;

import java.util.List;
import java.util.Objects;

public class ParameterUtils {

    public static boolean foundParameterSearch(List<ParameterDTO> list, Parameter parameter){

        for(ParameterDTO indexParameter: list){
            if(indexParameter.getNameParameter().equals(parameter.getNameParameter())){
                return true;
            }
        }
        return false;
    }
}
