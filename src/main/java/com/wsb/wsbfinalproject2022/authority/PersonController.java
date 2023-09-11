package com.wsb.wsbfinalproject2022.authority;

import com.wsb.wsbfinalproject2022.projects.Project;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;
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
        modelAndView.addObject("persons", personRepository.findAll());
        modelAndView.addObject("roles", roleRepository.findAll());
        return modelAndView;
    }

    //@Secured("ROLE_CREATE_USER")
    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("users/create");
        modelAndView.addObject("person", new Person());
        modelAndView.addObject("persons", personRepository.findAll());
        modelAndView.addObject("roles", roleRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView saveUser(@ModelAttribute @Valid Person person, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("users/create");
            modelAndView.addObject("authorities", roleRepository.findAll());
            modelAndView.addObject("person", person);
            return modelAndView;
        }
        savePerson(person);
        modelAndView.setViewName("redirect:/users/users");

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("users/create");
        Person person = personRepository.findById(id).orElse(null);
        modelAndView.addObject("roles", roleRepository.findAll());
        modelAndView.addObject("person", person);
        return modelAndView;

    }

    @GetMapping("/account")
    ModelAndView account() {
        ModelAndView modelAndView = new ModelAndView("users/account");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipal = authentication.getName();

        Person person = personRepository.findByUsername(currentPrincipal);
        modelAndView.addObject("person", person);
        modelAndView.addObject("roles", roleRepository.findAll());

        return modelAndView;
    }

    protected void savePerson(Person person) {
        String hashedPassword = new BCryptPasswordEncoder().encode(person.password);
        person.setPassword(hashedPassword);
        personRepository.save(person);
    }


}