package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.homehelper.form.ImageForm;
import ar.edu.itba.paw.homehelper.form.SignUpForm;
import ar.edu.itba.paw.interfaces.services.DefaultImagesService;
import ar.edu.itba.paw.interfaces.services.MailService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TestController {

    @Autowired
    private MailService mailService;

    @Autowired
    private SProviderService sProviderService;

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultImagesService defaultImagesService;


    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/upload",method = {RequestMethod.GET})
    public ModelAndView formCompletion(@ModelAttribute("imageForm") final ImageForm form) {

        final ModelAndView mav = new ModelAndView("upload");
        return mav;
    }

    @RequestMapping("/sendEmail")
    public ModelAndView sendEmail(@ModelAttribute("loggedInUser") final User loggedInUser, @ModelAttribute("signUpForm") final SignUpForm form) {
        mailService.sendConfirmationEmail(1,"dummykey");
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/upload",method = {RequestMethod.POST})
    public ModelAndView formCompletion2(@ModelAttribute("imageForm") final ImageForm form) {

        byte[] image= new byte[10];
        try {
            //user =userService.create("segundo","123456","segundo","farina","segundo","23","cuba 2546",form.getProfilePicture().getBytes());
            image= form.getProfilePicture().getBytes();
        }catch (Exception e){
            e.printStackTrace();
        }

        defaultImagesService.insertImage(1,image);

        //SProvider sProvider = sProviderService.create(user.getId(),"hola que tal");

        final ModelAndView mav = new ModelAndView("redirect:/profile/-1/profileimage");
        return mav;
    }

}
