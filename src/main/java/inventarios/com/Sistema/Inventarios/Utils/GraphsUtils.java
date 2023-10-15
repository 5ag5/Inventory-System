package inventarios.com.Sistema.Inventarios.Utils;

import inventarios.com.Sistema.Inventarios.Models.slicePie;

import java.util.*;

public final class GraphsUtils {

    public static String [][] get2DGraph(Set<String>namesArgument,  List<String> elementsArguments, boolean typeOfArray){
        if(typeOfArray == true){
            return get2DGraphNoCounting(namesArgument,  elementsArguments);
        }

        Map<String, Integer> seen = new HashMap<>();
        List<String> countValues = new ArrayList<>();

        for(String argument: elementsArguments){
            seen.put(argument,seen.getOrDefault(argument, 0) + 1);
        }

        for(int val: seen.values()){
            countValues.add(String.valueOf(val));
        }
        return new String[][]{namesArgument.toArray(new String[0]), countValues.toArray(new String[0])};
    }

    public static String[][] get2DGraphNoCounting(Set<String>namesArgument,  List<String> elementsArguments){
        return new String[][]{namesArgument.toArray(new String[0]),elementsArguments.toArray(new String[0])};
    }

    public static List<slicePie>getPieChart(List<String> elementsArguments, Set<String> nameParameters){
        Map<String, Double> seen = new HashMap<>();
        List<slicePie> dataPie = new ArrayList<>();

        for(String arg: elementsArguments){
            seen.put(arg, seen.getOrDefault(arg,0.0)+1);
        }

        for(String value: nameParameters){
            double valuePercentage = seen.get(value)/elementsArguments.size();
            slicePie obj = new slicePie(value,valuePercentage,false, false);
            dataPie.add(obj);
        }

        slicePie objTrue = dataPie.get(0);
        objTrue.setSlice(true);
        objTrue.setSelected(true);
        dataPie.remove(0);
        dataPie.add(objTrue);

        return dataPie;
    }

}
