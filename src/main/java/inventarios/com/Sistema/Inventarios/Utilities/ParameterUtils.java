package inventarios.com.Sistema.Inventarios.Utilities;

import inventarios.com.Sistema.Inventarios.Models.Parameter;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public final class ParameterUtils {

    public ParameterUtils(){}

    static public boolean validateDate(UserInventory userInventory, Parameter parameter){
        int valueInt=convertStringToNumber(parameter.getValueParameter());
        long days=ChronoUnit.DAYS.between(userInventory.getLastRegisteredPassword(),userInventory.getDateRegistered());
        if(days>= valueInt){
            return true;
        }else{
            return false;
        }
    }

    static public  int convertStringToNumber(String valueParameter){
        int valueParameterInt= Integer.parseInt(valueParameter);
        return valueParameterInt;
    }
}
