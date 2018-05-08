package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.AptitudeForm;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class ServiceProviderController {

    @Autowired
    private SProviderService sProviderService;
    @Autowired
    private ChatService chatService;

    @RequestMapping("/sprovider")
    public ModelAndView provider(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("serviceProviderControlPanel");

        if(loggedInUser == null) {
            //exception 403
        }

        mav.addObject("providerId", loggedInUser.getId());

        return mav;
    }

    @RequestMapping("/sprovider/editProfile")
    public ModelAndView providerPosts(@ModelAttribute("loggedInUser") final User loggedInUser/*,@ModelAttribute("registerForm") final AptitudeForm form*/) {

        final ModelAndView mav = new ModelAndView("serviceProviderCPEditProfile");
        if(loggedInUser == null) {
            //Exception
        }
        final int providerId = loggedInUser.getId();

        mav.addObject("providerId", providerId);
        mav.addObject("provider", sProviderService.getServiceProviderWithUserId(providerId));
        mav.addObject("serviceTypes",sProviderService.getServiceTypes());

        return mav;
    }

    @RequestMapping(value = "/sprovider/messages/{clientId}", method = { RequestMethod.POST })
    public ModelAndView sendMessagePost(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("clientId") int clientId, @RequestParam("msg") String msg) {
        if(loggedInUser == null) {
            //Exception
        }
        final int providerId = loggedInUser.getId();

        chatService.sendMsg(providerId, clientId, msg);

        return new ModelAndView("redirect:/sprovider/messages/" + clientId);
    }

    @RequestMapping(value = "/sprovider/messages/{clientId}", method = { RequestMethod.GET })
    public ModelAndView providerMessages(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("clientId") int clientId) {
        if(loggedInUser == null) {
            //Exception
        }
        final int providerId = loggedInUser.getId();
        final ModelAndView mav = new ModelAndView("serviceProviderCPMessages");

        mav.addObject("providerId", providerId);
        mav.addObject("chats", chatService.getChatsOf(providerId));
        mav.addObject("currentChat", chatService.getChat(providerId, clientId));


        return mav;
    }

    @RequestMapping(value = "/sprovider/messages", method = { RequestMethod.GET })
    public ModelAndView providerMessagesGeneral(@ModelAttribute("loggedInUser") final User loggedInUser) {
        if(loggedInUser == null) {
            //Exception
        }
        final int providerId = loggedInUser.getId();
        return new ModelAndView("redirect:/sprovider/" + providerId + "/messages/" + chatService.getLastMsgThread(providerId));
    }

    @RequestMapping("/sprovider/appointments")
    public ModelAndView providerAppointments(@ModelAttribute("loggedInUser") final User loggedInUser) {
        if(loggedInUser == null) {
            //Exception
        }
        final int providerId = loggedInUser.getId();
        final ModelAndView mav = new ModelAndView("serviceProviderCPAppointments");

        mav.addObject("providerId", providerId);

        return mav;
    }

    @RequestMapping("/sprovider/reviews")
    public ModelAndView providerReviews(@ModelAttribute("loggedInUser") final User loggedInUser) {
        if(loggedInUser == null) {
            //Exception
        }
        final int providerId = loggedInUser.getId();
        final ModelAndView mav = new ModelAndView("serviceProviderCPReviews");

        mav.addObject("providerId", providerId);

        return mav;
    }


/*
    @RequestMapping(value = "/sprovider/{providerId}/editProfile/updateAptitudes", method = { RequestMethod.POST })
    public ModelAndView updateAptitudes(@Valid @ModelAttribute("registerForm") final AptitudeForm form, final BindingResult errors) {
        if (errors.hasErrors()) { return providerPosts(form.getServiceProviderId());
        }
        ArrayList<AptitudeForm> list =new ArrayList<AptitudeForm>();
        list.add(form);
        sProviderService.updateAptitudes(form.getServiceProviderId(),list);
       // final User u = us.create(form.getUsername(), form .getPassword());


        return providerPosts(form.getServiceProviderId());
    }
    */

}
