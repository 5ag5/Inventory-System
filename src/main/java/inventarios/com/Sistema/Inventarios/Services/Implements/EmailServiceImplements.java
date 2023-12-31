package inventarios.com.Sistema.Inventarios.Services.Implements;

import inventarios.com.Sistema.Inventarios.Controllers.UserInventoryController;
import inventarios.com.Sistema.Inventarios.DTOs.EmailDTO;
import inventarios.com.Sistema.Inventarios.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImplements implements EmailService {

    //private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private static final Logger logger = LogManager.getLogger(UserInventoryController.class);

    @Autowired
    private JavaMailSender sender;

    @Override
    public boolean sendEmail(EmailDTO emailDTO) {
        logger.info("EmailBody: {}", emailDTO.toString());
        return sendEmailTool(emailDTO.getContent(),emailDTO.getEmail(), emailDTO.getSubject());
    }

    private boolean sendEmailTool(String textMessage, String email,String subject) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            send = true;
            logger.info("Mail enviado!");
        } catch (MessagingException e) {
            logger.error("Hubo un error al enviar el mail: {}", e);
        }
        return send;
    }

}