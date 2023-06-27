package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.DTOs.ParameterDTO;
import inventarios.com.Sistema.Inventarios.Models.Parameter;
import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Repositories.ParameterRepository;
import inventarios.com.Sistema.Inventarios.Services.ParamertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParameterServiceImplements implements ParamertService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public Parameter findById(Long id) {
        return parameterRepository.findById(id).orElse(null);
    }

    @Override
    public List<ParameterDTO> getAllParameterDTO() {
        return parameterRepository.findAll().stream().map(parameter -> new ParameterDTO(parameter)).collect(Collectors.toList());
    }

    @Override
    public void saveParameter(Parameter parameter) {
        parameterRepository.save(parameter);
    }


}
