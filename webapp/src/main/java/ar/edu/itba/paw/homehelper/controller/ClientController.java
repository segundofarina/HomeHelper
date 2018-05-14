package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.homehelper.form.CreateSProviderForm;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Status;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
public class ClientController {

    @Autowired
    private SProviderService sProviderService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private HHUserDetailsService userDetailsService;

    @Autowired
    private AppointmentService appointmentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);


    @ModelAttribute("createSProviderForm")
    public CreateSProviderForm createSProviderForm() {
        return new CreateSProviderForm();
    }


   /* Este metodo no andaa */
   @RequestMapping(value = "/client/getSendAppointment", method = RequestMethod.GET)
   public ModelAndView getSendAppointment(@ModelAttribute("appointmentForm") final AppointmentForm form, RedirectAttributes redrAttr, HttpServletRequest request) {
       if(form == null) {
           //throw new ForbiddenException();
       }

       //System.out.println(form.getDate());

       redrAttr.addFlashAttribute("appointmentForm", form);
       //request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
       return new ModelAndView("forward:/client/sendAppointment");
   }

   @RequestMapping(value = "/client/sendAppointment", method = RequestMethod.POST)
   public ModelAndView sendAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @ModelAttribute("appointmentForm") final AppointmentForm form) {
       if(form == null) {
           //throw exception
       }

       Appointment ap= appointmentService.addAppointment(loggedInUser.getId(), form.getProviderId(), form.getServiceTypeId(), form.getDate(),  "", form.getDescription());
       chatService.sendAppointmentMsg(loggedInUser.getId(), form.getProviderId());

       String redirect = "redirect:/client/appointmentConfirmed?appt=" + ap.getAppointmentId();

       return new ModelAndView(redirect);
   }

   @RequestMapping("/client/appointmentConfirmed")
   public ModelAndView appointmentConfirmed(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(value = "appt") final int apId) {
       final ModelAndView mav = new ModelAndView("appointmentConfirmed");
       mav.addObject("user", loggedInUser);
       mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

       mav.addObject("appointment", appointmentService.getAppointment(apId));
       return mav;
   }

    @RequestMapping(value = "/client/messages/{providerId}", method = { RequestMethod.POST })
    public ModelAndView sendMessagePost(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") int providerId, @RequestParam("msg") String msg) {
        final int userId = loggedInUser.getId();

        chatService.sendMsg(userId, providerId, msg);

        return new ModelAndView("redirect:/client/messages/" + providerId);
    }

    @RequestMapping(value = "/client/messages/{providerId}", method = {RequestMethod.GET})
    public ModelAndView messages(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") final int providerId) {
        final ModelAndView mav = new ModelAndView("clientMessages");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

        List<Chat> list= chatService.getChatsOf(loggedInUser.getId());
        mav.addObject("chats", list);

        LOGGER.debug("List of chats size: {} for user {}",list.size(),getUserString(loggedInUser));

        mav.addObject("currentChat", chatService.getChat(loggedInUser.getId(), providerId));

        return mav;
    }

    @RequestMapping("/client/messages")
    public ModelAndView messagesGeneral(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final int userId = loggedInUser.getId();
        return new ModelAndView("redirect:/client/messages/" + chatService.getLastMsgThread(userId));
    }

    @RequestMapping(value = "/client/createSProvider", method = {RequestMethod.GET})
    public ModelAndView createProvider(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(required = false, value = "error", defaultValue = "n") final String error) {
        final ModelAndView mav = new ModelAndView("createSProvider");

        mav.addObject("user", loggedInUser);
        mav.addObject("serviceTypes",sProviderService.getServiceTypes());

        if(error.equals("n")) {
            mav.addObject("hasError", 0);
        } else {
            mav.addObject("hasError", 1);
        }

        return mav;
    }

    @RequestMapping(value = "/client/createSProvider", method = RequestMethod.POST)
    public ModelAndView postCreateProvider(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("createSProviderForm") final CreateSProviderForm form, final BindingResult errors, final RedirectAttributes redrAttr) {
        if (errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.createSProviderForm", errors);
            redrAttr.addFlashAttribute("createSProviderForm", form);
            return new ModelAndView("redirect:/client/createSProvider?error=y");
        }

        /* Save as service provider and add service provider privilages */
        sProviderService.create(loggedInUser.getId(), form.getProfileDesc());
        sProviderService.addAptitude(loggedInUser.getId(), form.getServiceTypeId(), form.getAptDesc());
        //update user

        UserDetails userDetails = userDetailsService.loadUserByUsername(loggedInUser.getUsername());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ModelAndView("redirect:/sprovider");
    }

    @RequestMapping("/client/appointments")
    public ModelAndView appointments(@ModelAttribute("loggedInUser") final User loggedInUser) {
       final ModelAndView mav = new ModelAndView("client/appointments");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

        mav.addObject("appointmentsPending", appointmentService.getPendingAppointmentWithUserId(loggedInUser.getId()));
        mav.addObject("appointmentsDone", appointmentService.getAppointmentsByUserId(loggedInUser.getId(), Status.Done));

       return mav;
    }

    private int getUserId(User user) {
        if(user == null) {
            return -1;
        }
        return user.getId();
    }

    private String getUserString(User user){
        if (user == null){
            return "Annonymous";
        }else{
            return user.getUsername()+"["+user.getId()+"]";
        }
    }
}





