package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.STypeService;
import ar.edu.itba.paw.model.ServiceType;
import ar.edu.itba.paw.model.Status;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ServiceProviderController {

    @Autowired
    private SProviderService sProviderService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private STypeService sTypeService;
    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping("/sprovider")
    public ModelAndView provider(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("serviceProviderControlPanel");

        if(loggedInUser == null) {
            //exception 403
        }

        mav.addObject("providerId", loggedInUser.getId());
        mav.addObject("providerName", loggedInUser.getFirstname());

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
        mav.addObject("providerName", loggedInUser.getFirstname());
        mav.addObject("provider", sProviderService.getServiceProviderWithUserId(providerId));
        mav.addObject("serviceTypes",sTypeService.getServiceTypes());

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
        mav.addObject("providerName", loggedInUser.getFirstname());
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

        return new ModelAndView("redirect:/sprovider/messages/" + chatService.getLastMsgThread(providerId));
    }

    @RequestMapping("/sprovider/appointments")
    public ModelAndView providerAppointments(@ModelAttribute("loggedInUser") final User loggedInUser) {
        if(loggedInUser == null) {
            //Exception
        }
        final int providerId = loggedInUser.getId();
        final ModelAndView mav = new ModelAndView("serviceProviderCPAppointments");

        mav.addObject("providerId", providerId);
        mav.addObject("providerName", loggedInUser.getFirstname());
        mav.addObject("appointmentsPending", appointmentService.getPendingAppointmentWithProviderId(providerId));
        mav.addObject("appointmentsDone", appointmentService.getAppointmentsByProviderId(providerId, Status.Done));

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
        mav.addObject("providerName", loggedInUser.getFirstname());

        //mav.addObject("reviews", );

        return mav;
    }

    @RequestMapping("/sprovider/acceptAppointment")
    public ModelAndView acceptAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(value = "appointmentId") final int appointmentId) {
        appointmentService.confirmAppointment(appointmentId);

        return new ModelAndView("redirect:/sprovider/appointments");
    }

    @RequestMapping("/sprovider/rejectAppointment")
    public ModelAndView rejectAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(value = "appointmentId") final int appointmentId) {
        //appointmentService.rejectAppointment(appointmentId);

        return new ModelAndView("redirect:/sprovider/appointments");
    }

    @RequestMapping("/sprovider/completeAppointment")
    public ModelAndView completeAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(value = "appointmentId") final int appointmentId) {
        appointmentService.completedAppointment(appointmentId);

        return new ModelAndView("redirect:/sprovider/appointments");
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
