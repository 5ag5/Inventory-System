package inventarios.com.Sistema.Inventarios.Services;

import inventarios.com.Sistema.Inventarios.Models.OptionsGraphs;

import java.util.List;

public interface GraphicsOptionsService {
    List<OptionsGraphs> findAllGraphOptions();

    OptionsGraphs findById(Long id);

    void addOptionsGraphic(OptionsGraphs optionsGraphs, String opcion);

    void modifyGraphOptions(OptionsGraphs optionsGraphs);

    void eliminateGraphOptions(OptionsGraphs optionsGraphs);

    void saveGraphOptions(OptionsGraphs optionsGraphs);
}
