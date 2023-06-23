package inventarios.com.Sistema.Inventarios.Services;

import inventarios.com.Sistema.Inventarios.DTOs.ParameterDTO;
import inventarios.com.Sistema.Inventarios.Models.Parameter;

import java.util.List;

public interface ParamertService {

    Parameter findById(Long id);

    List<ParameterDTO> getAllParameterDTO();

    void saveParameter(Parameter parameter);
}
