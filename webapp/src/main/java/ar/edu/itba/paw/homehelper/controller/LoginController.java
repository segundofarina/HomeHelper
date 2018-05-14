package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @RequestMapping("/login") public ModelAndView login(HttpServletRequest request, final RedirectAttributes redrAttr, @ModelAttribute("appointmentForm") final AppointmentForm appointmentForm) {
        String referer = request.getHeader("Referer");
        String redirect = referer;

        if(referer !=null && referer.contains("/profile")) {
            redrAttr.addFlashAttribute("appointmentForm", appointmentForm);
            //System.out.println(appointmentForm.getDate());
            redirect = "/client/getSendAppointment";
        }

        /* Avoid redirecting to login after login error */
        if(referer !=null && referer.contains("/login")) {
            redirect = (String) request.getSession().getAttribute("url_prior_login");
        }

        request.getSession().setAttribute("url_prior_login", redirect);

        return new ModelAndView("login");
    }

}
