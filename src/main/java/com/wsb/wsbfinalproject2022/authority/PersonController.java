package com.wsb.wsbfinalproject2022.authority;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class PersonController {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    public PersonController(PersonRepository personRepository, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;

    }


    //@RequestMapping(value="/logout", method = RequestMethod.GET)
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    @GetMapping("/users")
    ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView("users/users");
        modelAndView.addObject("persons",personRepository.findAll());
        modelAndView.addObject("roles",roleRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    //@Secured("ROLE_CREATE_USER")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("users/create");
        modelAndView.addObject("role", new Role());
        modelAndView.addObject("person", new Person());
        Optional<Role> roles = roleRepository.findByName(RoleType.ROLE_ADMIN);
        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView saveUser(@ModelAttribute Person person, @ModelAttribute Role role) {
        ModelAndView modelAndView = new ModelAndView();
        personRepository.save(person);
        roleRepository.save(role);
        return modelAndView;
    }
}