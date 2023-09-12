package com.wsb.wsbfinalproject2022.issues;


import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import com.wsb.wsbfinalproject2022.mails.EmailSenderService;
import com.wsb.wsbfinalproject2022.mails.Mail;
import com.wsb.wsbfinalproject2022.projects.ProjectRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
    String save(@ModelAttribute Issue issue, @ModelAttribute Mail mail) {
        Boolean isNew = issue.getId() == null;
        issueRepository.save(issue);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipal = authentication.getName();
        Person sentTo = personRepository.findByUsername(currentPrincipal);
        emailSenderService.send(String.valueOf(sentTo.getEmail()), mail);

        if (isNew) {
            return "redirect:/projects";
        } else {
            return "redirect:/projects/edit/" + issue.getId();
        }

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


}
