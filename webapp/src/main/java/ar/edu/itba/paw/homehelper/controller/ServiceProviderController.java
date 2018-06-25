package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.exceptions.InvalidUsernameException;
import ar.edu.itba.paw.homehelper.form.AddWZForm;
import ar.edu.itba.paw.homehelper.form.AptitudeForm;
import ar.edu.itba.paw.homehelper.form.ProfileGeneralInfo;
import ar.edu.itba.paw.homehelper.form.UpdateAptitudeForm;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.Status;
import ar.edu.itba.paw.model.TemporaryImage;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;


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

    @Autowired
    private WorkingZonesService workingZonesService;

    @Autowired
    private NeighborhoodService neighborhoodService;

    @Autowired
    private TempImagesService tempImagesService;

    @Autowired
    private UserService userService;

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

    @ModelAttribute("addWZForm")
    public AddWZForm addWZForm() {
        return new AddWZForm();
    }

    @RequestMapping("/sprovider")
    public ModelAndView provider(@ModelAttribute("loggedInUser") final User loggedInUser) throws InvalidUsernameException {
        final ModelAndView mav = new ModelAndView("serviceProviderControlPanel");

        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }

        final int providerId = loggedInUser.getId();
        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        mav.addObject("provider", loggedInUser);

        mav.addObject("chats", chatService.getLatestChatsOf(providerId));
        mav.addObject("appointments", appointmentService.getLatestPendingAppointmentWithProviderId(providerId));
        mav.addObject("reviews", sProviderService.getLatestReviewsOfServiceProvider(providerId));

        mav.addObject("totalAp", provider.getAptitudes().size());
        mav.addObject("generalCal", provider.getGeneralCalification());
        mav.addObject("pendingAp", appointmentService.getPendingAppointmentWithProviderId(providerId).size());
        mav.addObject("doneAp", appointmentService.getAppointmentsByProviderId(providerId, Status.Done).size());


        return mav;
    }

    @RequestMapping(value = "/sprovider/messages/{clientId}", method = { RequestMethod.POST })
    public ModelAndView sendMessagePost(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("clientId") int clientId, @RequestParam("msg") String msg) throws InvalidUsernameException {
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();

        chatService.sendMsg(providerId, clientId, msg);

        return new ModelAndView("redirect:/sprovider/messages/" + clientId);
    }

    @RequestMapping(value = "/sprovider/messages/{clientId}", method = { RequestMethod.GET })
    public ModelAndView providerMessages(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("clientId") int clientId) throws InvalidUsernameException {
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();
        final ModelAndView mav = new ModelAndView("serviceProviderCPMessages");

        mav.addObject("provider", loggedInUser);

        mav.addObject("chats", chatService.getChatsOf(providerId));
        mav.addObject("currentChat", chatService.getChat(providerId, clientId));


        return mav;
    }

    @RequestMapping(value = "/sprovider/messages", method = { RequestMethod.GET })
    public ModelAndView providerMessagesGeneral(@ModelAttribute("loggedInUser") final User loggedInUser) throws InvalidUsernameException {
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();

        return new ModelAndView("redirect:/sprovider/messages/" + chatService.getLastMsgThread(providerId));
    }

    @RequestMapping("/sprovider/appointments")
    public ModelAndView providerAppointments(@ModelAttribute("loggedInUser") final User loggedInUser) throws InvalidUsernameException {
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();
        final ModelAndView mav = new ModelAndView("serviceProviderCPAppointments");

        mav.addObject("provider", loggedInUser);

        mav.addObject("appointmentsPending", appointmentService.getPendingAppointmentWithProviderId(providerId));
        mav.addObject("appointmentsDone", appointmentService.getCompleteAppointmentWithProviderId(providerId));

        return mav;
    }

    @RequestMapping("/sprovider/reviews")
    public ModelAndView providerReviews(@ModelAttribute("loggedInUser") final User loggedInUser) throws InvalidUsernameException {
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final ModelAndView mav = new ModelAndView("serviceProviderCPReviews");

        mav.addObject("provider", loggedInUser);

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
        appointmentService.rejectAppointment(appointmentId);

        return new ModelAndView("redirect:/sprovider/appointments");
    }

    @RequestMapping(value = "/sprovider/completeAppointment", method = RequestMethod.POST)
    public ModelAndView completeAppointment(@RequestParam(value = "appointmentId") final int appointmentId) {
        appointmentService.completedAppointment(appointmentId);

        return new ModelAndView("redirect:/sprovider/appointments");
    }

    @RequestMapping("/sprovider/editProfile")
    public ModelAndView providerPosts(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(required = false, value = "error", defaultValue = "-1") final int elemErrorId, Model model,@RequestParam(required = false,value="img",defaultValue = "-1")final int img) throws InvalidUsernameException {
        final ModelAndView mav = new ModelAndView("serviceProviderCPEditProfile");
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();

        /* Empty model attribute since its not uses in this view */
        model.addAttribute("updateAptitudeForm", new UpdateAptitudeForm());

        mav.addObject("provider", loggedInUser);

        mav.addObject("serviceProvider", sProviderService.getServiceProviderWithUserId(providerId));
        mav.addObject("serviceTypes",sTypeService.getServiceTypes());

        mav.addObject("errorElemId", elemErrorId);
        mav.addObject("editAptitude", -1);
        mav.addObject("img", img);

        mav.addObject("workingZones", workingZonesService.getWorkingZonesOfProvider(providerId));
        mav.addObject("neightbourhoods", neighborhoodService.getAllNeighborhoods());

        /* Profile picture */


        return mav;
    }

    @RequestMapping(value = "/sprovider/editProfile/editGeneralInfo", method = RequestMethod.POST)
    public ModelAndView editGeneralInfo(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("profileGeneralInfo") final ProfileGeneralInfo form, BindingResult errors, RedirectAttributes redrAttr) {
        if(errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.profileGeneralInfo", errors);
            redrAttr.addFlashAttribute("profileGeneralInfo", form);

            String red =persistImage(form.getProfilePicture(),"",form.getSavedImgId());

            String redirect = "redirect:/sprovider/editProfile?error=" + form.getElemId()+red;
            return new ModelAndView(redirect);
        }



        sProviderService.updateDescriptionOfServiceProvider(loggedInUser.getId(), form.getGeneralDescription());
        //update Image

        byte[] image =null;
        /* Check if image is uploaded */
        image = retriveImage(form.getProfilePicture(),form.getSavedImgId());
        if(image != null){
            userService.updateImageOfUser(loggedInUser.getId(),image);
        }


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
    public ModelAndView updateAptitudeId(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("aptitudeId") final int aptitudeId, Model model) throws InvalidUsernameException {
        final ModelAndView mav = new ModelAndView("serviceProviderCPEditProfile");
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();
        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        if(!model.containsAttribute("updateAptitudeForm")) {
            UpdateAptitudeForm updateAptitudeForm = new UpdateAptitudeForm();
            updateAptitudeForm.setAptDescription(aptitudeService.getAptitude(aptitudeId).get().getDescription());
            model.addAttribute("updateAptitudeForm", updateAptitudeForm);
        }

        mav.addObject("provider", loggedInUser);

        mav.addObject("serviceProvider", provider);
        mav.addObject("serviceTypes", sTypeService.getServiceTypes());

        mav.addObject("errorElemId", -1);
        mav.addObject("editAptitude", aptitudeId);

        mav.addObject("img", -1);

        mav.addObject("workingZones", workingZonesService.getWorkingZonesOfProvider(providerId));
        mav.addObject("neightbourhoods", neighborhoodService.getAllNeighborhoods());

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

        if(form.getAction().equals("delete")) {
            aptitudeService.removeAptitude(form.getAptitutdeId());
        } else {
            sProviderService.updateDescriptionOfAptitude(form.getAptitutdeId(), form.getAptDescription());
        }

        return new ModelAndView("redirect:/sprovider/editProfile");
    }

    @RequestMapping(value = "/sprovider/editProfile/deleteWorkingZone", method = RequestMethod.POST)
    public ModelAndView deleteWZ(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(value = "ngId") final int ngId) {
        workingZonesService.removeWorkingZoneOfProvider(loggedInUser.getId(), ngId);

        return new ModelAndView("redirect:/sprovider/editProfile");
    }

    @RequestMapping(value = "/sprovider/editProfile/addNg", method = RequestMethod.POST)
    public ModelAndView addWZ(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("addWZForm") final AddWZForm form, final BindingResult errors, final RedirectAttributes redrAttr) {
        if(errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.addWZForm", errors);
            redrAttr.addFlashAttribute("addWZForm", form);
            String redirect = "redirect:/sprovider/editProfile?error=3";
            return new ModelAndView(redirect);
        }

        workingZonesService.insertWorkingZoneOfProvider(loggedInUser.getId(), form.getNgId());

        return new ModelAndView("redirect:/sprovider/editProfile");
    }

    public String persistImage(MultipartFile imageFile, String redirect, int savedId){
        byte[] image=null;
        if(imageFile.getSize() > 0) {
            try {
                image = imageFile.getBytes();
            } catch (IOException e) {
                image = null;
            }

            if(image != null && image.length !=0) {
                TemporaryImage img = tempImagesService.insertImage(image);
                redirect += "&img=" + img.getImageId();
            }
        } else if(savedId != -1) {
            redirect += "&img=" + savedId;
        }

        return redirect;
    }

    public byte[] retriveImage(MultipartFile imageFile,int savedId){
        byte[] image= null;
        /* Check if image is uploaded */
        if(imageFile.getSize() > 0) {
            try{
                image = imageFile.getBytes();
            }catch (Exception e){
                //Handle exception?
                image = null;
            }
        } else if(savedId != -1) { /* If not check if image was uploaded before */
            image = tempImagesService.getImage(savedId).getImage();
            tempImagesService.deleteImage(savedId);
        }
        return image;
    }
}
