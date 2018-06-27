package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.homehelper.exceptions.InvalidQueryException;
import ar.edu.itba.paw.homehelper.exceptions.ProviderNotFoundException;
import ar.edu.itba.paw.homehelper.exceptions.UploadException;
import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.homehelper.form.SearchForm;
import ar.edu.itba.paw.homehelper.form.SignUpForm;
import ar.edu.itba.paw.homehelper.validators.EqualsUsernameValidator;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.TemporaryImage;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.Base64;
import java.util.List;


@Controller
public class PublicController {
    @Autowired
    private SProviderService sProviderService;

    @Autowired
    private NeighborhoodService neighborhoodService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private EqualsUsernameValidator equalsUsernameValidator;

    @Autowired
    private HHUserDetailsService userDetailsService;

    @Autowired
    private STypeService sTypeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TempImagesService tempImagesService;

    @Autowired
    private DefaultImagesService defaultImagesService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PublicController.class);


    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return new SearchForm();
    }

    @ModelAttribute("appointmentForm")
    public AppointmentForm appointmentForm() {
        return new AppointmentForm();
    }

    @ModelAttribute("signUpForm")
    public SignUpForm signUpForm() {
        return new SignUpForm();
    }


    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("loggedInUser") final User loggedInUser, @CookieValue(value = "HH-LastPost", defaultValue = "") final String cookieVal) {
        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("serviceTypes", sTypeService.getServiceTypes());
        //mav.addObject("neighborhoods", neighborhoodService.getAllNeighborhoods());

        if(!cookieVal.equals("")) {
            String[] vals = cookieVal.split("--");//vals[0] is provider id ; vals[1] is serviceTypeId
            //mav.addObject("lastPost", cookieVal);
            mav.addObject("lastPostProvider", sProviderService.getServiceProviderWithUserId( Integer.parseInt(vals[0]) ));
            mav.addObject("lastPostServiceType", vals[1]);
        }
        return mav;
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView processSearch(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("searchForm") final SearchForm form, final BindingResult errors, final RedirectAttributes redrAttr, @RequestHeader(value = "referer", required = false, defaultValue = "/") final String referer, @RequestParam(required = false, value = "st", defaultValue = "-1") final int serviceTypeId, @RequestParam(required = false, value = "llat", defaultValue = "-1") final double lat, @RequestParam(required = false, value = "llng", defaultValue = "-1") final double lng, @RequestParam(required = false, value = "addr", defaultValue = "") final String address64) {

        if(errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.searchForm", errors);
            redrAttr.addFlashAttribute("searchForm", form);
            String redirect = "redirect:";

            if(referer.contains("/searchResults")) {
                /* Referer page is searchResults, send it back there */
                redirect += "/searchResults?st=" + serviceTypeId + "&llat=" + lat + "&llng=" + lng + "&addr=" + address64;
            } else {
                /* Referer page is index */
                redirect += "/";
            }

            LOGGER.info("User {} search had the following errors {} ", getUserString(loggedInUser), errors);

            return new ModelAndView(redirect);
        }

        redrAttr.addFlashAttribute("searchForm", form);
        String redirect = "redirect:/searchResults?st=" + form.getServiceTypeId() + "&llat=" + form.getLatDouble() + "&llng=" + form.getLngDouble() + "&addr=" + Base64.getUrlEncoder().encodeToString(form.getAddressField().getBytes());
        LOGGER.info("User {} searched for service type [{}] in city [{}]",getUserString(loggedInUser),form.getServiceTypeId(),1);
        return new ModelAndView(redirect);
    }

    @RequestMapping(value = "/searchResults", method = RequestMethod.GET)
    public ModelAndView searchProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(required = false, value = "st", defaultValue = "-1") final int serviceTypeId, @RequestParam(required = false, value = "llat", defaultValue = "-1") final double lat, @RequestParam(required = false, value = "llng", defaultValue = "-1") final double lng, @RequestParam(required = false, value = "addr", defaultValue = "") final String address64) throws InvalidQueryException {
        final ModelAndView mav = new ModelAndView("profileSearch");

        /* Lanzar excepcion cuando serviceTypeId es -1 o cuando cityId es -1 */
        if(serviceTypeId == -1 || lat == -1 || lng == -1 || address64.isEmpty()) {
            throw new InvalidQueryException();
        }

        String address = "";
        try {
            address = new String(Base64.getUrlDecoder().decode(address64));
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryException();
        }


        final List<SProvider> list = sProviderService.getServiceProvidersByNeighborhoodAndServiceType(lat, lng, serviceTypeId, getUserId(loggedInUser) );

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

        mav.addObject("list",list);

        mav.addObject("serviceTypes", sTypeService.getServiceTypes());

        /* Current params showing */
        mav.addObject("serviceTypeId", serviceTypeId);
        mav.addObject("llat", lat);
        mav.addObject("llng", lng);
        mav.addObject("addr", address);
        mav.addObject("b64addr", address64);

        return mav;
    }

    @RequestMapping("/profile/{providerId}")
    public ModelAndView providerProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") int providerId, @RequestParam(required = false, value = "st", defaultValue = "-1") final int serviceTypeId, final HttpServletResponse response) throws InvalidQueryException {
        final ModelAndView mav = new ModelAndView("profile");

        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        if(provider == null) {
            LOGGER.info("{} tried to access to provider with id {} which does not exist.",getUserString(loggedInUser),providerId);
            throw new ProviderNotFoundException();
        }

        if(serviceTypeId == -1) {
            throw new InvalidQueryException();
        }

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("provider", provider);

        mav.addObject("serviceTypeId", serviceTypeId);

        mav.addObject("currentAptitude", sProviderService.getAptitudeOfProvider(serviceTypeId, provider));
        mav.addObject("otherAptitudes", sProviderService.getAllAptitudesExcept(serviceTypeId, provider));

        // get coords from db
        mav.addObject("workingZonesCoords", "-34.557176,-58.430436;-34.588696,-58.431428;-34.575376,-58.403839");

        /* Save post in cookie */
        addLastPostCookie(providerId, serviceTypeId, response);

        LOGGER.info("{} accessed to provider's profile with id {} .",getUserString(loggedInUser),providerId);
        return mav;
    }



    @RequestMapping(value = "/profile/{userId}/profileimage", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] providerProfileImage(@PathVariable("userId") int userId) {
        byte[] img = null;
        img = userService.getProfileImage(userId);

        return img;
    }


    @RequestMapping(value = "/profile/sendAppointment", method = RequestMethod.POST)
    public ModelAndView sendAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("appointmentForm") final AppointmentForm form, final BindingResult errors, final RedirectAttributes redrAttr, final HttpServletResponse response) {
        if(errors.hasErrors()) {
            /* Back to form */
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.appointmentForm", errors);
            redrAttr.addFlashAttribute("appointmentForm", form);

            String redirect = "redirect:/profile/" + form.getProviderId();
            LOGGER.info("user {} tried to make an appointment and had the following errors in form {}",getUserString(loggedInUser),errors);
            return new ModelAndView(redirect);
        }

        redrAttr.addFlashAttribute("appointmentForm", form);

        if(loggedInUser == null) {
            saveFormAsCookies(form, response);

            String redirect = "redirect:/login?sAp=true";
            LOGGER.info("user {} tried to make an appointment but was not logged in.", getUserString(loggedInUser));
            return new ModelAndView(redirect);
        }

        if(!loggedInUser.isVerified()) {
            return new ModelAndView("redirect:/unverified/user");
        }

        removeLastPostCookie(response);

        return new ModelAndView("forward:/client/sendAppointment");
    }

    @RequestMapping("/signup")
    public ModelAndView signup(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(required = false,value="img",defaultValue = "-1")final int img) {
        final ModelAndView mav = new ModelAndView("signup");

        mav.addObject("user", loggedInUser);
        mav.addObject("img", img);

        return mav;
    }


    @RequestMapping(value = "/createUser", method = { RequestMethod.POST })
    public ModelAndView createUser(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("signUpForm") final SignUpForm form, final BindingResult errors, final RedirectAttributes redrAttr) throws UploadException {

        byte[] image = null;
        User invalidUser =  userService.findByUsername(form.getUsername());

        /* Check for duplicate username */
        if(invalidUser != null) {
            equalsUsernameValidator.validate(EqualsUsernameValidator.buildUserNamePair(form.getUsername(),invalidUser.getUsername()), errors);
        }

        /* Check for error in form */
        if (errors.hasErrors()) {
            /* Back to form */
            String redirect = "redirect:/signup";

            /* If image was uploaded keep it in a table of temporary img */
            if(form.getProfilePicture().getSize() > 0) {
                try {
                    image = form.getProfilePicture().getBytes();
                }catch (Exception e){
                    image = null;
                }

                if(image != null && image.length !=0) {
                    TemporaryImage img = tempImagesService.insertImage(image);
                    redirect += "?img=" + img.getImageId();
                }
            } else if(form.getSavedImgId() != -1) {
                redirect += "?img=" + form.getSavedImgId();
            }

            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.signUpForm", errors);
            redrAttr.addFlashAttribute("signUpForm", form);

            return new ModelAndView(redirect);
        }

        /* Form without errors */

        /* Check if image is uploaded */
        if(form.getProfilePicture().getSize() > 0) {
            try{
                image = form.getProfilePicture().getBytes();
            }catch (Exception e){
                throw new UploadException();

            }
        } else if(form.getSavedImgId() != -1) { /* If not check if image was uploaded before */
            image = tempImagesService.getImage(form.getSavedImgId()).getImage();
            tempImagesService.deleteImage(form.getSavedImgId());
        }

        LOGGER.info("{} user was created.",getUserString(loggedInUser));

        /* Save user */
        User user = userService.create(form.getUsername(), passwordEncoder.encode(form.getPasswordForm().getPassword()), form.getFirstname(), form.getLastname(), form.getEmail(), form.getPhone(), form.getEmail(), image);

        /* Send email */
        String key = Base64.getUrlEncoder().encodeToString(passwordEncoder.encode(user.getId()+user.getUsername()+"CRONOS").getBytes());
        mailService.sendConfirmationEmail( user.getId(),key);

        /* Log in user */
        Authentication auth = loginUser(user);

        if(isUnverifiedUser(auth)) {
            return new ModelAndView("redirect:/unverified/user");
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/users/verify/{key}")
    public ModelAndView verifyAccount(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("key") final String key) {
        final ModelAndView mav = new ModelAndView("userVerify");

        User user = mailService.verifyUserKey(key);

        LOGGER.debug("Tried to verify account");
        LOGGER.debug("MailService verifyUserKey was:{}",getUserString(user));

        mav.addObject("user",user);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId( user )));

        if(user != null) {
            loginUser(user);
        }

        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/tempImg/{imgId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] getTempImg(@PathVariable("imgId") final int imgId, @RequestParam(required = false, value = "userId", defaultValue = "-1") final int userId) {
        if(imgId == -1) {
            return providerProfileImage(userId);
        }

        TemporaryImage temp = tempImagesService.getImage(imgId);

        if(temp == null) {
            return providerProfileImage(userId);
        }
        return temp.getImage();
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

    private void saveFormAsCookies(AppointmentForm form, HttpServletResponse response) {
        Cookie cookie = new Cookie("ApForm-ProviderId", String.valueOf(form.getProviderId()));
        cookie.setPath("/");
        response.addCookie(cookie);

        cookie = new Cookie("ApForm-ServiceType", form.getServiceType() );
        cookie.setPath("/");
        response.addCookie(cookie);

        cookie = new Cookie("ApForm-Date", form.getDate() );
        cookie.setPath("/");
        response.addCookie(cookie);

        cookie = new Cookie("ApForm-Description", form.getDescription() );
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    private boolean isUnverifiedUser(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_UNVERIFIED_USER"));
    }

    private Authentication loginUser(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    private void addLastPostCookie(int providerId, int serviceTypeId, HttpServletResponse response) {
        String str = providerId + "--" + serviceTypeId;
        Cookie cookie = new Cookie("HH-LastPost", str);
        cookie.setPath("/");
        cookie.setMaxAge(250000);

        response.addCookie(cookie);
    }

    private void removeLastPostCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("HH-LastPost", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
