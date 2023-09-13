package com.wsb.wsbfinalproject2022.issues;


import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import com.wsb.wsbfinalproject2022.mails.EmailSenderService;
import com.wsb.wsbfinalproject2022.mails.Mail;
import com.wsb.wsbfinalproject2022.projects.Project;
import com.wsb.wsbfinalproject2022.projects.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;
    private final PersonRepository personRepository;
    private final EmailSenderService emailSenderService;

    public IssueController(ProjectRepository projectRepository, IssueRepository issueRepository, PersonRepository personRepository, EmailSenderService emailSenderService) {
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.personRepository = personRepository;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        Issue issue = new Issue();
        modelAndView.addObject("issue", issue);
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("persons",personRepository.findAll());

        return modelAndView;
    }


    @PostMapping("/save")
    ModelAndView save(@ModelAttribute @Valid Issue issue, BindingResult bindingResult, @ModelAttribute Mail mail) {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("issue", issue);
            modelAndView.addObject("persons",personRepository.findAll());
            modelAndView.addObject("projects",projectRepository.findAll());
            return modelAndView;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipal = authentication.getName();
        Person sentTo = personRepository.findByUsername(currentPrincipal);
        emailSenderService.send(String.valueOf(sentTo.getEmail()), mail);

        issueRepository.save(issue);
        modelAndView.setViewName("redirect:/issues/list");
        return modelAndView;

    }
    @GetMapping("/edit/{id}")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issues/create");

        Issue issue =  issueRepository.findById(id).orElse(null);
        modelAndView.addObject("issue", issue);
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("persons",personRepository.findAll());
        return modelAndView;
    }


    @GetMapping("/list")
    ModelAndView list(@ModelAttribute IssueFilter filter) {
        ModelAndView modelAndView = new ModelAndView("issues/list");
        modelAndView.addObject("issues", issueRepository.findAll(filter.buildQuery()));
        modelAndView.addObject("persons",personRepository.findAll());
        modelAndView.addObject("projects",projectRepository.findAll());
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("statuses", IssueStatus.values());
        return modelAndView;
    }


    @Secured({"ROLE_MENAGE_PROJECT","ROLE_ADMIN"})
    @GetMapping("/delete/{id}")
    String delete(@PathVariable Long id) {
        try {
            issueRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("nie udalo sie usunać zgloszenia " + e);
        }
        return "redirect:/issues/list";
    }


}
