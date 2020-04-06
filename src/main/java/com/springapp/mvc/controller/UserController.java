package com.springapp.mvc.controller;

import com.springapp.mvc.dto.CredentialsDTO;
import com.springapp.mvc.dto.UserMoreDetailsDTO;
import com.springapp.mvc.model.Credentials;
import com.springapp.mvc.model.User;
import com.springapp.mvc.model.enums.RoleType;
import com.springapp.mvc.service.AuthenticationService;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    private User loggedUser;

    private UserMoreDetailsDTO userMoreDetailsDTO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hi there! Please, log in if you want to access our page");
        return "index";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submit(@ModelAttribute("credentials") CredentialsDTO credentials) {

        Credentials userCredentials = authenticationService.confirmAuthentication(credentials);
        loggedUser = userService.getUserByCredentials(userCredentials);

        if (loggedUser != null) {
            if (userCredentials.getRole().equals(RoleType.ROLE_ADMIN)) {
                return "redirect:/allusers";
            } else if (userCredentials.getRole().equals(RoleType.ROLE_USER)) {
                return "redirect:/personal";
            }
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("title", "Admin Panel");
        model.addAttribute("message", "Here are all our users:");
        return "welcome";
    }

    @RequestMapping(value = "/personal", method = RequestMethod.GET)
    public String showPersonalData(Model model) {
        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(userService.getUserById(loggedUser.getUserId()));

        model.addAttribute("users", listOfUsers);
        model.addAttribute("title", "Personal Cabinet");
        model.addAttribute("message", "Personal data:");
        return "personalCab";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorConnection(ModelMap model) {
        model.addAttribute("errorMessage", "Invalid Details");
        return "error";
    }

    @RequestMapping(value = "/moredetails", method = RequestMethod.GET)
    public String moreDetails(Model model) {
        List<UserMoreDetailsDTO> userMoreDetailsDTOS = new ArrayList<>();
        userMoreDetailsDTOS.add(userService.getUserInformationByIdDTO(loggedUser.getUserId()));
        model.addAttribute("usersdetails", userMoreDetailsDTOS);
        model.addAttribute("message", "More personal data:");
        return "moreDetails";
    }

    @RequestMapping(value = {"deleteUser/{id}"}, method = RequestMethod.POST)
    public String deleteUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("users", userService.deletedUserList(userService.getUserById(id)));
        return "redirect:/allusers";
    }

}