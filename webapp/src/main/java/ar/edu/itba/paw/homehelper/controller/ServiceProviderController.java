package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.form.AptitudeForm;
import ar.edu.itba.paw.homehelper.form.ProfileGeneralInfo;
import ar.edu.itba.paw.homehelper.form.UpdateAptitudeForm;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.Status;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


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

    @Autowired
    private AptitudeService aptitudeService;

    @ModelAttribute("profileGeneralInfo")
    public ProfileGeneralInfo profileGeneralInfo(@ModelAttribute("loggedInUser") final User loggedInUser) {
        ProfileGeneralInfo profileGeneralInfo = new ProfileGeneralInfo();
        profileGeneralInfo.setGeneralDescription(sProviderService.getServiceProviderWithUserId(loggedInUser.getId()).getDescription());

        return profileGeneralInfo;
    }

    @ModelAttribute("aptitudeForm")
    public AptitudeForm aptitudeForm() {
        return new AptitudeForm();
    }

    @RequestMapping("/sprovider")
    public ModelAndView provider(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("serviceProviderControlPanel");

        if(loggedInUser == null) {
            //exception 403
        }

        mav.addObject("providerId", loggedInUser.getId());
        mav.addObject("providerName", loggedInUser.getFirstname());

        /*mav.addObject("provider", sProviderService.getServiceProviderWithUserId(providerId));
        mav.addObject("serviceTypes",sTypeService.getServiceTypes());*/

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

        mav.addObject("reviews", sProviderService.getReviewsOfServiceProvider(loggedInUser.getId()));

        return mav;
    }

    @RequestMapping(value = "/sprovider/acceptAppointment", method = RequestMethod.POST)
    public ModelAndView acceptAppointment(@RequestParam(value = "appointmentId") final int appointmentId) {
        appointmentService.confirmAppointment(appointmentId);

        return new ModelAndView("redirect:/sprovider/appointments");
    }

    @RequestMapping(value = "/sprovider/rejectAppointment", method = RequestMethod.POST)
    public ModelAndView rejectAppointment(@RequestParam(value = "appointmentId") final int appointmentId) {
        //appointmentService.rejectAppointment(appointmentId);

        return new ModelAndView("redirect:/sprovider/appointments");
    }

    @RequestMapping(value = "/sprovider/completeAppointment", method = RequestMethod.POST)
    public ModelAndView completeAppointment(@RequestParam(value = "appointmentId") final int appointmentId) {
        appointmentService.completedAppointment(appointmentId);

        return new ModelAndView("redirect:/sprovider/appointments");
    }

    @RequestMapping("/sprovider/editProfile")
    public ModelAndView providerPosts(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(required = false, value = "error", defaultValue = "-1") final int elemErrorId, Model model) {
        final ModelAndView mav = new ModelAndView("serviceProviderCPEditProfile");
        if(loggedInUser == null) {
            //Exception
        }
        final int providerId = loggedInUser.getId();

        /* Empty model attribute since its not uses in this view */
        model.addAttribute("updateAptitudeForm", new UpdateAptitudeForm());

        mav.addObject("providerId", providerId);
        mav.addObject("providerName", loggedInUser.getFirstname());
        mav.addObject("provider", sProviderService.getServiceProviderWithUserId(providerId));
        mav.addObject("serviceTypes",sTypeService.getServiceTypes());

        mav.addObject("errorElemId", elemErrorId);
        mav.addObject("editAptitude", -1);

        /* Profile picture */


        return mav;
    }

    @RequestMapping(value = "/sprovider/editProfile/editGeneralInfo", method = RequestMethod.POST)
    public ModelAndView editGeneralInfo(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("profileGeneralInfo") final ProfileGeneralInfo form, BindingResult errors, RedirectAttributes redrAttr) {
        if(errors.hasErrors()) {
            System.out.println("has errors");
            System.out.println(form.getGeneralDescription());
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.profileGeneralInfo", errors);
            redrAttr.addFlashAttribute("profileGeneralInfo", form);
            String redirect = "redirect:/sprovider/editProfile?error=" + form.getElemId();
            return new ModelAndView(redirect);
        }

        //update

        return new ModelAndView("redirect:/sprovider/editProfile");
    }

    @RequestMapping(value = "/sprovider/editProfile/addAptitude", method = RequestMethod.POST)
    public ModelAndView addAptitude(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("aptitudeForm") final AptitudeForm form, final BindingResult errors, RedirectAttributes redrAttr) {
        if(errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.aptitudeForm", errors);
            redrAttr.addFlashAttribute("aptitudeForm", form);
            String redirect = "redirect:/sprovider/editProfile?error=" + form.getElemId();
            return new ModelAndView(redirect);
        }

        sProviderService.addAptitude(loggedInUser.getId(), form.getServiceTypeId(), form.getAptDescription());

        return new ModelAndView("redirect:/sprovider/editProfile");
    }



    @RequestMapping("/sprovider/editProfile/updateAptitude/{aptitudeId}")
    public ModelAndView updateAptitudeId(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("aptitudeId") final int aptitudeId, Model model) {
        final ModelAndView mav = new ModelAndView("serviceProviderCPEditProfile");
        if(loggedInUser == null) {
            //Exception
        }
        final int providerId = loggedInUser.getId();
        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        if(!model.containsAttribute("updateAptitudeForm")) {
            UpdateAptitudeForm updateAptitudeForm = new UpdateAptitudeForm();
            updateAptitudeForm.setAptDescription(aptitudeService.getAptitude(aptitudeId).getDescription());
            model.addAttribute("updateAptitudeForm", updateAptitudeForm);
        }

        mav.addObject("providerId", providerId);
        mav.addObject("providerName", loggedInUser.getFirstname());

        mav.addObject("provider", provider);
        mav.addObject("serviceTypes", sTypeService.getServiceTypes());

        mav.addObject("errorElemId", -1);
        mav.addObject("editAptitude", aptitudeId);

        return mav;
    }

    @RequestMapping(value = "/sprovider/editProfile/updateAptitude", method = RequestMethod.POST)
    public ModelAndView updateAptitude(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("updateAptitudeForm") final UpdateAptitudeForm form, final BindingResult errors, final RedirectAttributes redrAttr) {
        if(errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.updateAptitudeForm", errors);
            redrAttr.addFlashAttribute("updateAptitudeForm", form);

            String redirect = "redirect:/sprovider/editProfile/updateAptitude/" + form.getAptitutdeId() + "?error=y";
            return new ModelAndView(redirect);
        }

        final int providerId = loggedInUser.getId();

        if(form.getAction().equals("delete")) {
            sProviderService.removeAptitude(providerId, form.getServiceType());//cambaiar a aptitude id
        } else {
            sProviderService.updateDescriptionOfAptitude(form.getAptitutdeId(), form.getAptDescription());
        }

        return new ModelAndView("redirect:/sprovider/editProfile");
    }
}
