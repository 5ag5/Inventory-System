package inventarios.com.Sistema.Inventarios.DTOs;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EmailDTO {

    private String email;

    private String content;

    private String subject;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public  EmailDTO(){}

    public EmailDTO(String password, String email){
        this.email=email;
        this.content= "Your password is " +password;
        this.subject="Automatically generated password";
    }

    public String getEmail() {
        return email;
    }

    public String getContent() {
        return content;
    }

    public String getSubject() {
        return subject;
    }
}
