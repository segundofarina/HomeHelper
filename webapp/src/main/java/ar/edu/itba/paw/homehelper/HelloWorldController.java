package ar.edu.itba.paw.homehelper;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
    @Autowired
    private UserService us;


    @RequestMapping("/users")
    public ModelAndView helloUsers(@RequestParam("id") int id) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("greeting", us.findById(id).getUsername());

        return mav;
    }

    @RequestMapping("/user/{userId}")
    public ModelAndView helloUser(@PathVariable("userId") int id) {
        return helloUsers(id);
//        final ModelAndView mav = new ModelAndView("index");
//        mav.addObject("greeting", us.findById(id).getUsername());
//
//        return mav;
    }

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam(value = "name", required = true) final String username,@RequestParam(value = "pass", required = true) final String password) {
        final User u = us.create(username,password, "Juan", "Perez", "juancito@hotmail.com", "1134546576");
        return new ModelAndView("redirect:/user/" + u. getId());
    }

}
