package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.daos.VerifyEmailDao;
import ar.edu.itba.paw.interfaces.services.MailService;
import ar.edu.itba.paw.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;

@Service
public class MailServiceImpl implements MailService{


    private final String serverEmail ="noreply.homehelper";
    private final String serverPassword ="dulcedeleche";
    private final String subject ="Confirm your account at HomeHelper";

    @Autowired
    UserDao userDao;

    @Autowired
    JavaMailSender mailSender;


    @Autowired
    Configuration freemarkerConfiguration;

    @Autowired
    VerifyEmailDao verifyEmailDao;

    @Override
    @Async
    public void sendConfirmationEmail(String email, int userId) {
        Optional<User> user = userDao.findById(userId);
        if(!user.isPresent()){
            System.out.println("Tried to send email but user with id "+userId+"does not exist");
            return;
        }

        MimeMessagePreparator preparator = getMessagePreparator(email,user.get());

        try {
            mailSender.send(preparator);
            verifyEmailDao.insert(userId,""+userId);
            System.out.println("Message has been sent.............................");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }


    private MimeMessagePreparator getMessagePreparator(final String email, final User user){

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(subject);
            helper.setFrom(serverEmail);
            helper.setTo(email);



            Map<String, Object> model = new HashMap<String, Object>();
            model.put("user", user);
            model.put("verifyurl","http://localhost:8080/users/verify/"+user.getId());

            String text = geFreeMarkerTemplateContent(model);//Use Freemarker or Velocity
            System.out.println("Template content : "+text);

            // use the true flag to indicate you need a multipart message
            helper.setText(text, true);

            //Additionally, let's add a resource as an attachment as well.
            //helper.addAttachment("HHLogo.png", new ClassPathResource("/mailTemplates/HHLogo.png"));

        };
        return preparator;
    }




    public String geFreeMarkerTemplateContent(Map<String, Object> model){
        StringBuffer content = new StringBuffer();
        try{
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate("confirmation.ftl"),model));
            return content.toString();
        }catch(Exception e){
            System.out.println("Exception occured while processing fmtemplate:"+e.getMessage());
        }
        return "";
    }

}
