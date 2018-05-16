package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @RequestMapping("/login") public ModelAndView login(HttpServletRequest request, @RequestParam(required = false, value = "error", defaultValue = "n") final String error) {
        String referer = request.getHeader("Referer");
        String redirect = referer;

        if(referer !=null && referer.contains("/profile")) {
            redirect = "/client/getSendAppointment";//no siempre hay que redirigir puedo poner un param que me diga si o no
        }

        /* Avoid redirecting to login after login error */
        if(referer !=null && referer.contains("/login")) {
            redirect = (String) request.getSession().getAttribute("url_prior_login");
        }

        request.getSession().setAttribute("url_prior_login", redirect);

        final ModelAndView mav = new ModelAndView("login");
        if(error.equals("y")) {
            mav.addObject("error", true);
        } else {
            mav.addObject("error", false);
        }

        return mav;
    }

}
