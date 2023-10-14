package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.Models.OptionsGraphs;
import inventarios.com.Sistema.Inventarios.Repositories.OptionsGraphsRepository;
import inventarios.com.Sistema.Inventarios.Services.GraphicsOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionsGraphsImplements implements GraphicsOptionsService {
    @Autowired
    OptionsGraphsRepository optionsGraphsRepository;

    @Override
    public List<OptionsGraphs> findAllGraphOptions() {
        return optionsGraphsRepository.findAll();
    }

    @Override
    public OptionsGraphs findById(Long id) {
        OptionsGraphs optionObject = optionsGraphsRepository.findById(id).orElse(null);
        return optionObject;
    }

    @Override
    public void addOptionsGraphic(OptionsGraphs optionsGraphs, String option) {
        OptionsGraphs optionObject = findById(optionsGraphs.getId());
        optionObject.addOptionsList(option);
        saveGraphOptions(optionObject);
    }

    @Override
    public void modifyGraphOptions(OptionsGraphs optionsGraphs) {

    }

    @Override
    public void eliminateGraphOptions(OptionsGraphs optionsGraphs) {
        optionsGraphsRepository.delete(optionsGraphs);
    }

    @Override
    public void saveGraphOptions(OptionsGraphs optionsGraphs) {
        optionsGraphsRepository.save(optionsGraphs);
    }
}
