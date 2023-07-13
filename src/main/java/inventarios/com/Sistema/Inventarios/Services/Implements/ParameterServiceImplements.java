package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.DTOs.ParameterDTO;
import inventarios.com.Sistema.Inventarios.Models.Parameter;
import inventarios.com.Sistema.Inventarios.Repositories.ParameterRepository;
import inventarios.com.Sistema.Inventarios.Services.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParameterServiceImplements implements ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public Parameter findById(Long id) {
        return parameterRepository.findById(id).orElse(null);
    }

    public Parameter findParameterByName(String nameParameter){
        return parameterRepository.findBynameParameter(nameParameter);
    }

    @Override
    public List<Parameter> findAllParameters() {
        return parameterRepository.findAll();
    }

    @Override
    public List<ParameterDTO> getAllParameterDTO() {
        List<ParameterDTO> getAllParameters = parameterRepository.findAll().stream().map(parameter -> new ParameterDTO(parameter)).collect(Collectors.toList());
        return getAllParameters.stream().filter(element -> element.isParameterStatus()).collect(Collectors.toList());
    }

    @Override
    public void saveParameter(Parameter parameter) {
        parameterRepository.save(parameter);
    }


}
