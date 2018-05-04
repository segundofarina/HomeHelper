package ar.edu.itba.paw.homehelper;

import ar.edu.itba.paw.interfaces.ChatService;
import ar.edu.itba.paw.interfaces.SProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ServiceProviderController {

    @Autowired
    private SProviderService sProviderService;
    @Autowired
    private ChatService chatService;

    @RequestMapping("/sprovider/{providerId}")
    public ModelAndView provider(@PathVariable("providerId") int providerId) {

        final ModelAndView mav = new ModelAndView("serviceProviderControlPanel");

        mav.addObject("providerId", providerId);

        return mav;
    }

    @RequestMapping("/sprovider/{providerId}/editProfile")
    public ModelAndView providerPosts(@PathVariable("providerId") int providerId) {

        final ModelAndView mav = new ModelAndView("serviceProviderCPEditProfile");

        mav.addObject("providerId", providerId);

        return mav;
    }

    @RequestMapping(value = "/sprovider/{providerId}/messages/{clientId}", method = { RequestMethod.POST })
    public ModelAndView sendMessagePost(@PathVariable("providerId") int providerId, @PathVariable("clientId") int clientId, @RequestParam("msg") String msg) {

        chatService.sendMsg(providerId, clientId, msg);

        return new ModelAndView("redirect:/sprovider/" + providerId + "/messages/" + clientId);
    }

    @RequestMapping(value = "/sprovider/{providerId}/messages/{clientId}", method = { RequestMethod.GET })
    public ModelAndView providerMessages(@PathVariable("providerId") int providerId, @PathVariable("clientId") int clientId) {

        final ModelAndView mav = new ModelAndView("serviceProviderCPMessages");

        mav.addObject("providerId", providerId);
        mav.addObject("chats", chatService.getChatsOf(providerId));
        mav.addObject("currentChat", chatService.getChat(providerId, clientId));


        return mav;
    }

    @RequestMapping(value = "/sprovider/{providerId}/messages", method = { RequestMethod.GET })
    public ModelAndView providerMessagesGeneral(@PathVariable("providerId") int providerId) {
        return new ModelAndView("redirect:/sprovider/" + providerId + "/messages/" + chatService.getLastMsgThread(providerId));
    }

    @RequestMapping("/sprovider/{providerId}/appointments")
    public ModelAndView providerAppointments(@PathVariable("providerId") int providerId) {

        final ModelAndView mav = new ModelAndView("serviceProviderCPAppointments");

        mav.addObject("providerId", providerId);

        return mav;
    }

    @RequestMapping("/sprovider/{providerId}/reviews")
    public ModelAndView providerReviews(@PathVariable("providerId") int providerId) {

        final ModelAndView mav = new ModelAndView("serviceProviderCPReviews");

        mav.addObject("providerId", providerId);

        return mav;
    }

}
