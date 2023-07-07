package inventarios.com.Sistema.Inventarios.Services;

import inventarios.com.Sistema.Inventarios.DTOs.EmailDTO;

public interface EmailService {

    public boolean sendEmail(EmailDTO emailDTO);
}
