package inventarios.com.Sistema.Inventarios.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class GraphsUtils {

    public static String [][] get2DGraph(Set<String>namesArgument,  List<String> elementsArguments, String nameArgument){
        int sizeArray =0;


        if(namesArgument.size() == 1) {
            sizeArray = namesArgument.size()+1;
        }else{
            sizeArray = namesArgument.size();
        }


        String[][] arrayAnswer = new String[sizeArray][2];

        arrayAnswer[0][0] = nameArgument;
        arrayAnswer[0][1] = "count";

        ArrayList<String> arrayTemp = new ArrayList<>(namesArgument);

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

}
