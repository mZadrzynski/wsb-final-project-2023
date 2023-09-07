package com.wsb.wsbfinalproject2022.issues;


import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import com.wsb.wsbfinalproject2022.projects.Project;
import com.wsb.wsbfinalproject2022.projects.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;

    private final PersonRepository personRepository;



    public IssueController(ProjectRepository projectRepository, IssueRepository issueRepository, PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.personRepository = personRepository;

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
    String save(@ModelAttribute Issue issue) {
        Boolean isNew = issue.getId() == null;
        issueRepository.save(issue);

        if (isNew) {
            return "redirect:/projects";
        } else {
            return "redirect:/projects/edit/" + issue.getId();
        }

    }

//    @PostMapping("/save")
//    ModelAndView save(@ModelAttribute @Valid Issue issue, BindingResult bindingResult){
//        ModelAndView modelAndView = new ModelAndView("issues/create");
//
//        if (bindingResult.hasErrors()) {
//            modelAndView.addObject("issue", issue);
//            modelAndView.addObject("projects", projectRepository.findAll());
//            modelAndView.addObject("persons",personRepository.findAll());
//            return modelAndView;
//        }
//
//        modelAndView.setViewName("redirect:/projects");
//        issueRepository.save(issue);
//        return modelAndView;
//    }

    @GetMapping("/list")
    ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("issues/list");
        modelAndView.addObject("issues", issueRepository.findAll());
        modelAndView.addObject("persons",personRepository.findAll());
        return modelAndView;
    }
}
