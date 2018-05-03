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
        //mav.addObject("postList", sProviderService.getPosts(providerId));

        return mav;
    }

    @RequestMapping("/sprovider/{providerId}/posts")
    public ModelAndView providerPosts(@PathVariable("providerId") int providerId) {

        final ModelAndView mav = new ModelAndView("serviceProviderCPPosts");

        mav.addObject("providerId", providerId);
        mav.addObject("postList", sProviderService.getPosts(providerId));

        return mav;
    }

    @RequestMapping(value = "/sprovider/{providerId}/messages", method = { RequestMethod.POST })
    public ModelAndView sendMessagePost(@PathVariable("providerId") int providerId, @RequestParam("msg") String msg) {

        //final ModelAndView mav = new ModelAndView("serviceProviderCPMessages");

        //mav.addObject("providerId", providerId);
        //mav.addObject("chats", chatService.getChatsOf(providerId));
        chatService.sendMsg(4,2,msg);

        return new ModelAndView("redirect:/sprovider/" + providerId + "/messages");
    }

    @RequestMapping(value = "/sprovider/{providerId}/messages", method = { RequestMethod.GET })
    public ModelAndView providerMessages(@PathVariable("providerId") int providerId) {

        final ModelAndView mav = new ModelAndView("serviceProviderCPMessages");

        mav.addObject("providerId", providerId);
        mav.addObject("chats", chatService.getChatsOf(providerId));

        return mav;
    }

    @RequestMapping("/sprovider/{providerId}/appointments")
    public ModelAndView providerAppointments(@PathVariable("providerId") int providerId) {

        final ModelAndView mav = new ModelAndView("serviceProviderCPAppointments");

        mav.addObject("providerId", providerId);
        //mav.addObject("postList", sProviderService.getPosts(providerId));

        return mav;
    }

}
