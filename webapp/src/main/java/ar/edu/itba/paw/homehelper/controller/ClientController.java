package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class ClientController {

    @Autowired
    private SProviderService sProviderService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    ChatService chatService;


   /* Este metodo no andaa */
   @RequestMapping(value = "/client/getSendAppointment", method = RequestMethod.GET)
   public ModelAndView getSendAppointment(@ModelAttribute("appointmentForm") final AppointmentForm form, RedirectAttributes redrAttr, HttpServletRequest request) {
       if(form == null) {
           //throw new ForbiddenException();
       }

       System.out.println(form.getDate());

       redrAttr.addFlashAttribute("appointmentForm", form);
       //request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
       return new ModelAndView("forward:/client/sendAppointment");
   }

   @RequestMapping(value = "/client/sendAppointment", method = RequestMethod.POST)
   public ModelAndView sendAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @ModelAttribute("appointmentForm") final AppointmentForm form) {
       if(form == null) {
           //throw exception
       }

       appointmentService.addAppointment(loggedInUser.getId(), form.getProviderId(), form.getServiceTypeId(), form.getDate(),  "", form.getDescription());

       String redirect = "redirect:/client/appointmentConfirmed?appt=" + 1;

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

        mav.addObject("chats", chatService.getChatsOf(loggedInUser.getId()));
        mav.addObject("currentChat", chatService.getChat(loggedInUser.getId(), providerId));

        return mav;
    }

    @RequestMapping("/client/messages")
    public ModelAndView messagesGeneral(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final int userId = loggedInUser.getId();
        return new ModelAndView("redirect:/client/messages/" + chatService.getLastMsgThread(userId));
    }

    private int getUserId(User user) {
        if(user == null) {
            return -1;
        }
        return user.getId();
    }
}
