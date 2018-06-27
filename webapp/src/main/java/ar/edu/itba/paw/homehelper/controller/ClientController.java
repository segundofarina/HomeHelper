package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.exceptions.InvalidQueryException;
import ar.edu.itba.paw.homehelper.exceptions.InvalidUsernameException;
import ar.edu.itba.paw.homehelper.exceptions.NotFoundException;
import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.homehelper.form.ReviewForm;
import ar.edu.itba.paw.homehelper.form.SettingsForm;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.homehelper.form.CreateSProviderForm;
import ar.edu.itba.paw.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private STypeService sTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private TempImagesService tempImagesService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);


    @ModelAttribute("createSProviderForm")
    public CreateSProviderForm createSProviderForm() {
        return new CreateSProviderForm();
    }

    @ModelAttribute("reviewForm")
    public ReviewForm reviewForm() {
        return new ReviewForm();
    }


    /* Este metodo deberia ser post pero solo post no puedo hacerle la redireccion */
   @RequestMapping(value = "/client/getSendAppointment", method ={RequestMethod.POST, RequestMethod.GET})
   public ModelAndView getSendAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, final HttpServletRequest request, final HttpServletResponse response) throws InvalidQueryException {

       LOGGER.debug("User in getSendAppointment");

       AppointmentForm form = getAppointmentFromCookies(request);
       removeFormCookies(response);

       if(form == null) {
           LOGGER.debug("Cookies failed");
           throw new InvalidQueryException();
       }

       Appointment ap= appointmentService.addAppointment(loggedInUser.getId(), form.getProviderId(), form.getServiceTypeId(), form.getDate(),  loggedInUser.getAddress(), form.getDescription());
       chatService.sendAppointmentMsg(loggedInUser.getId(), form.getProviderId(), form.getDate(), form.getDescription());

       String redirect = "redirect:/client/appointmentConfirmed?appt=" + ap.getAppointmentId();

       return new ModelAndView(redirect);
   }

   @RequestMapping(value = "/client/sendAppointment", method = RequestMethod.POST)
   public ModelAndView sendAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @ModelAttribute("appointmentForm") final AppointmentForm form) throws InvalidQueryException {
       if(form == null) {
           throw new InvalidQueryException();
       }

       Appointment ap= appointmentService.addAppointment(loggedInUser.getId(), form.getProviderId(), form.getServiceTypeId(), form.getDate(),  loggedInUser.getAddress(), form.getDescription());
       chatService.sendAppointmentMsg(loggedInUser.getId(), form.getProviderId(), form.getDate(), form.getDescription());

       String redirect = "redirect:/client/appointmentConfirmed?appt=" + ap.getAppointmentId();

       return new ModelAndView(redirect);
   }

   @RequestMapping("/client/appointmentConfirmed")
   public ModelAndView appointmentConfirmed(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(value = "appt") final int apId) throws NotFoundException {
       Appointment lastAp = appointmentService.getLastAppointment(loggedInUser.getId());
       if(lastAp == null || lastAp.getAppointmentId() != apId) {
           throw new NotFoundException();
       }

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
    public ModelAndView messages(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") final int providerId) throws NotFoundException {
        final ModelAndView mav = new ModelAndView("clientMessages");

        /* Get current chat and validate it has messages */
        Chat currentChat = chatService.getChat(loggedInUser.getId(), providerId);
        if(currentChat == null || currentChat.getMessages().size() == 0) {
            throw new NotFoundException();
        }


        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

        List<Chat> list= chatService.getChatsOf(loggedInUser.getId());
        mav.addObject("chats", list);

        LOGGER.debug("List of chats size: {} for user {}",list.size(),getUserString(loggedInUser));

        mav.addObject("currentChat", currentChat);

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
        mav.addObject("serviceTypes",sTypeService.getServiceTypes());

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

        /* Add working zone coords */
        String[] coordsList = form.getAptMap().split(";");
        for(int i=0; i < coordsList.length ; i++) {
            String[] coord = coordsList[i].split(",",2);
            double lat = Double.parseDouble(coord[0]);
            double lng = Double.parseDouble(coord[1]);
            //add working zone
        }

        final int userId = loggedInUser.getId();
        userService.updateFirstNameOfUser(userId, form.getFirstname());
        userService.updateLastNameOfUser(userId, form.getLastname());
        userService.updateEmailOfUser(userId, form.getEmail());
        userService.updatePhoneOfUser(userId, form.getPhone());

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
        mav.addObject("appointmentsDone", appointmentService.getCompleteAppointmentWithUserId(loggedInUser.getId()));
        mav.addObject("rejectStatus", Status.Reject.getNumVal());

       return mav;
    }

    @RequestMapping("/client/writeReview/{appointmentId}")
    public ModelAndView writeReview(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("appointmentId") final int appointmentId) throws NotFoundException {
       /* Validate params */
       Appointment appointment = appointmentService.getAppointment(appointmentId);
       if(appointment == null || appointment.getStatus() != Status.Done || appointment.getClient().getId() != loggedInUser.getId() || appointment.isClientReview()) {
           throw new NotFoundException();
       }

       final ModelAndView mav = new ModelAndView("client/writeReview");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

        mav.addObject("appointment", appointment);

       return mav;
    }

    @RequestMapping(value = "/client/sendReview", method = RequestMethod.POST)
    public ModelAndView sendReview(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("reviewForm") final ReviewForm form, final BindingResult errors, final RedirectAttributes redrAttr) throws InvalidQueryException {
       if(form == null) {
           throw new InvalidQueryException();
       }

       if(errors.hasErrors()) {
           redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.reviewForm", errors);
           redrAttr.addFlashAttribute("reviewForm", form);
           String redirect = "redirect:/client/writeReview/" + form.getAppointmentId();
           return new ModelAndView(redirect);
       }

       Appointment ap = appointmentService.getAppointment(form.getAppointmentId());
       if(ap == null) {
           throw new InvalidQueryException();
       }
       /* ap.getServiceType().getServiceTypeId() eso esta mal tengo que mandar el id de la aptitud */
       appointmentService.reviewAppointment(form.getAppointmentId(),form.getProviderId(), ap.getServiceType().getServiceTypeId(), form.getQualityInt(), form.getCleannesInt(), form.getPriceInt(), form.getPunctualityInt(), form.getTreatmentInt(), form.getMsg());

       return new ModelAndView("redirect:/client/appointments");
    }

    @RequestMapping("/client/settings")
    public ModelAndView settings(@ModelAttribute("loggedInUser") final User loggedInUser, Model model, @RequestParam(required = false, value="img", defaultValue = "-1")final int img) {
       final ModelAndView mav = new ModelAndView("settings");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("img", img);

        if(!model.containsAttribute("settingsForm")) {
            SettingsForm settingsForm = new SettingsForm();
            settingsForm.setFirstname(loggedInUser.getFirstname());
            settingsForm.setLastname(loggedInUser.getLastname());
            settingsForm.setEmail(loggedInUser.getEmail());
            settingsForm.setPhone(loggedInUser.getPhone());
            model.addAttribute("settingsForm", settingsForm);
        }

        mav.addObject("username", loggedInUser.getUsername());

       return mav;
    }

    @RequestMapping("/client/settings/update")
    public ModelAndView settingsUpdate(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("settingsForm") final SettingsForm form, final BindingResult errors, final RedirectAttributes redrAttr) {
       byte[] image = null;
       if(errors.hasErrors()) {

           String redirect = persistImage(form.getProfilePicture(),"redirect:/client/settings",form.getSavedImgId());



            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.settingsForm", errors);
            redrAttr.addFlashAttribute("settingsForm", form);
            return new ModelAndView(redirect);
        }

        final int userId = loggedInUser.getId();


        userService.updateFirstNameOfUser(userId, form.getFirstname());
        userService.updateLastNameOfUser(userId, form.getLastname());
        userService.updateEmailOfUser(userId, form.getEmail());
        userService.updatePhoneOfUser(userId, form.getPhone());

        if(!form.getPasswordForm().getPassword().isEmpty()) {
            userService.updatePasswordOfUser(userId, form.getPasswordForm().getPassword());
        }

        //update profile picture

        /* Check if image is uploaded */
        image = retriveImage(form.getProfilePicture(),form.getSavedImgId());
        if(image != null){
            userService.updateImageOfUser(userId,image);
        }


        return new ModelAndView("redirect:/");
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

    private AppointmentForm getAppointmentFromCookies(HttpServletRequest request) {
       boolean provId = false, sType = false, date = false, desc = false;
       AppointmentForm form = new AppointmentForm();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ApForm-ProviderId")) {
                form.setProviderId( Integer.parseInt(cookie.getValue()) );
                provId = true;
            }
            if (cookie.getName().equals("ApForm-ServiceType")) {
                form.setServiceType( cookie.getValue() );
                sType = true;
            }
            if (cookie.getName().equals("ApForm-Date")) {
                form.setDate( cookie.getValue() );
                date = true;
            }
            if (cookie.getName().equals("ApForm-Description")) {
                form.setDescription( cookie.getValue() );
                desc = true;
            }
        }

        if(provId && sType && date && desc) {
            return form;
        }

        return null;
    }

    private void removeFormCookies(HttpServletResponse response) {
        List<String> cookies = new ArrayList<>(Arrays.asList("ApForm-ProviderId", "ApForm-ServiceType", "ApForm-Date", "ApForm-Description"));

        for(String cookieName : cookies) {
            Cookie cookie = new Cookie(cookieName, "");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    public String persistImage(MultipartFile imageFile,String redirect,int savedId){
       byte[] image=null;
        if(imageFile.getSize() > 0) {
            try {
                image = imageFile.getBytes();
            } catch (IOException e) {
                image = null;
            }

            if(image != null && image.length !=0) {
                TemporaryImage img = tempImagesService.insertImage(image);
                redirect += "?img=" + img.getImageId();
            }
        } else if(savedId != -1) {
            redirect += "?img=" + savedId;
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





