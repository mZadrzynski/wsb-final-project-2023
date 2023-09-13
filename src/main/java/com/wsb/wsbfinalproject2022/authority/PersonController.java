package com.wsb.wsbfinalproject2022.authority;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


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
    @Secured({"ADMIN","ROLE_MENAGE_USERS","ROLE_USER"})
    @GetMapping("/users")
    ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView("users/users");
        modelAndView.addObject("persons", personRepository.findAll());
        modelAndView.addObject("roles", roleRepository.findAll());
        return modelAndView;
    }

    @Secured({"ADMIN","ROLE_MENAGE_USERS"})
    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("users/create");
        modelAndView.addObject("person", new Person());
        modelAndView.addObject("persons", personRepository.findAll());
        modelAndView.addObject("roles", roleRepository.findAll());
        return modelAndView;
    }

    @Secured({"ADMIN","ROLE_MENAGE_USERS"})
    @PostMapping("/save")
    ModelAndView saveUser(@ModelAttribute @Valid Person person, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("users/create");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("authorities", roleRepository.findAll());
            modelAndView.addObject("person", person);
            return modelAndView;
        }
        savePerson(person);
        modelAndView.setViewName("redirect:/users/users");

        return modelAndView;
    }

    @Secured({"ADMIN","ROLE_MENAGE_USERS"})
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

    @Secured({"ADMIN","ROLE_MENAGE_USER"})
    @GetMapping("/delete/{id}")
    String delete(@PathVariable Long id) {
        try {
            personRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("nie udalo sie usunać uzytkownika " + e);
        }
        return "redirect:/users/users";
    }

    protected void savePerson(Person person) {
        String hashedPassword = new BCryptPasswordEncoder().encode(person.password);
        person.setPassword(hashedPassword);
        personRepository.save(person);
    }


}