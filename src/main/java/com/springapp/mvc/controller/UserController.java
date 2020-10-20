package com.springapp.mvc.controller;

import com.springapp.mvc.dto.CredentialsDTO;
import com.springapp.mvc.dto.UserDTO;
import com.springapp.mvc.model.Credentials;
import com.springapp.mvc.model.User;
import com.springapp.mvc.model.enums.RoleType;
import com.springapp.mvc.service.AuthenticationService;
import com.springapp.mvc.service.RegisterService;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private AuthenticationService authenticationService;

    private User loggedUser;

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
        List<UserDTO> listOfUsers = new ArrayList<>();
        listOfUsers.add(userService.getUserById(loggedUser.getUserId()));

        model.addAttribute("users", listOfUsers);
        model.addAttribute("title", "Personal Cabinet");
        model.addAttribute("message", "Personal data:");
        model.addAttribute("userRole", userService.getRegisterUserRole(loggedUser));
        return "personalCab";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorConnection(ModelMap model) {
        model.addAttribute("errorMessage", "Invalid Details");
        return "error";
    }

    @RequestMapping(value = "/moreDetails", method = RequestMethod.GET)
    public String showMoreDetails (Model model) {

        model.addAttribute("userInfo", userService.getAllDetailsFromUser(loggedUser));
        return "moreDetails";
    }


}
