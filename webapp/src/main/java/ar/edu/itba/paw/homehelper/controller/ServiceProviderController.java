package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.exceptions.InvalidUsernameException;
import ar.edu.itba.paw.homehelper.exceptions.NotFoundException;
import ar.edu.itba.paw.homehelper.form.AddWZForm;
import ar.edu.itba.paw.homehelper.form.AptitudeForm;
import ar.edu.itba.paw.homehelper.form.ProfileGeneralInfo;
import ar.edu.itba.paw.homehelper.form.UpdateAptitudeForm;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    @Autowired
    private CoordenatesService coordenatesService;

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
    public ModelAndView provider(@ModelAttribute("loggedInUser") final User loggedInUser, HttpServletResponse response) throws InvalidUsernameException {
        /* Remove last post cookie */
        removeLastPostCookie(response);

        final ModelAndView mav = new ModelAndView("serviceProviderControlPanel");

        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }

        final int providerId = loggedInUser.getId();
        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        mav.addObject("provider", loggedInUser);

        mav.addObject("chats", chatService.getLatestChatsOfProvider(providerId));
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

        chatService.sendMessageToUser(providerId, clientId, msg);

        return new ModelAndView("redirect:/sprovider/messages/" + clientId);
    }

    @RequestMapping(value = "/sprovider/messages/{clientId}", method = { RequestMethod.GET })
    public ModelAndView providerMessages(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("clientId") int clientId) throws InvalidUsernameException, NotFoundException {
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();

        /* Get current chat and validate it has messages */
        Chat currentChat = chatService.getChatOfProvider(providerId, clientId);
        if(currentChat == null || currentChat.getMessages().size() == 0) {
            throw new NotFoundException();
        }

        final ModelAndView mav = new ModelAndView("serviceProviderCPMessages");

        mav.addObject("provider", loggedInUser);


        mav.addObject("chats", chatService.getChatsOfProvider(providerId));
        mav.addObject("currentChat", currentChat);


        return mav;
    }

    @RequestMapping(value = "/sprovider/messages", method = { RequestMethod.GET })
    public ModelAndView providerMessagesGeneral(@ModelAttribute("loggedInUser") final User loggedInUser) throws InvalidUsernameException {
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();

        return new ModelAndView("redirect:/sprovider/messages/" + chatService.getLastMsgThreadProvider(providerId));
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

        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        mav.addObject("serviceProvider", provider);
        mav.addObject("serviceTypes",sTypeService.getServiceTypes());

        mav.addObject("errorElemId", elemErrorId);
        mav.addObject("editAptitude", -1);
        mav.addObject("img", img);

        // get coords from db
        StringBuilder sb = new StringBuilder();
        for(CoordenatesPoint coordenatesPoint : provider.getCoordenates()) {
            sb.append(coordenatesPoint.getLat());
            sb.append(",");
            sb.append(coordenatesPoint.getLng());
            sb.append(";");
        }

        mav.addObject("workingZonesCoords", sb.toString());
        //mav.addObject("workingZones", workingZonesService.getWorkingZonesOfProvider(providerId));
        //mav.addObject("neightbourhoods", neighborhoodService.getAllNeighborhoods());

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
    public ModelAndView updateAptitudeId(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("aptitudeId") final int aptitudeId, Model model) throws InvalidUsernameException, NotFoundException {
        final ModelAndView mav = new ModelAndView("serviceProviderCPEditProfile");
        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();
        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        /* Check if valid aptitudeId */
        if(!isValidAptitude(aptitudeId, provider.getAptitudes())) {
            throw new NotFoundException();
        }

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

    @RequestMapping(value = "/sprovider/editProfile/updateWorkingZone", method = RequestMethod.POST)
    public ModelAndView addWZ(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("addWZForm") final AddWZForm form, final BindingResult errors, final RedirectAttributes redrAttr) {
        if(errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.addWZForm", errors);
            redrAttr.addFlashAttribute("addWZForm", form);
            String redirect = "redirect:/sprovider/editProfile?error=3";
            return new ModelAndView(redirect);
        }

        /* Add working zone coords */
        Set<CoordenatesPoint> coordenatesSet = new HashSet<>();

        String[] coordsList = form.getCoordsStr().split(";");
        for(int i=0; i < coordsList.length ; i++) {
            String[] coord = coordsList[i].split(",",2);
            double lat = Double.parseDouble(coord[0]);
            double lng = Double.parseDouble(coord[1]);
            //add working zone
            coordenatesSet.add(new CoordenatesPoint(i,lat, lng));
        }
        coordenatesService.insertCoordenatesOfProvider(loggedInUser.getId(), coordenatesSet);

        return new ModelAndView("redirect:/sprovider/editProfile");
    }


    @RequestMapping(value = "/sprovider/profilePreview", method = RequestMethod.GET)
    public ModelAndView getProfilePreview(@ModelAttribute("loggedInUser") final User loggedInUser) throws InvalidUsernameException {
        final ModelAndView mav = new ModelAndView("providerPreview");

        if(loggedInUser == null) {
            throw new InvalidUsernameException();
        }
        final int providerId = loggedInUser.getId();
        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        if(provider == null) {
            throw new InvalidUsernameException();
        }

        mav.addObject("provider", provider);
        mav.addObject("aptitudes", sProviderService.getAptitudesOfUser(providerId));

        mav.addObject("workingZonesCoords", "-34.557176,-58.430436;-34.588696,-58.431428;-34.575376,-58.403839");


        return mav;
    }

    private boolean isValidAptitude(int aptitudeId, Set<Aptitude> aptitudeList) {
        for(Aptitude ap : aptitudeList) {
            if(ap.getId() == aptitudeId) {
                return true;
            }
        }
        return false;
    }

    private void removeLastPostCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("HH-LastPost", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    private String persistImage(MultipartFile imageFile, String redirect, int savedId){
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

    private byte[] retriveImage(MultipartFile imageFile,int savedId){
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
