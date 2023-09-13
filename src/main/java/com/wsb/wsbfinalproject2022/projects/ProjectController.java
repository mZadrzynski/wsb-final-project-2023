package com.wsb.wsbfinalproject2022.projects;

import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final PersonRepository personRepository;
    final private ProjectService projectService;

    public ProjectController(ProjectRepository projectRepository, PersonRepository personRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
        this.projectService = projectService;
    }


    @GetMapping()
    ModelAndView index(@ModelAttribute ProjectFilter filter) {
        ModelAndView modelAndView = new ModelAndView("projects/index");
        modelAndView.addObject("projects", projectRepository.findAll(filter.buildQuery()));
        modelAndView.addObject("persons", projectRepository.findAll());
        modelAndView.addObject("filter", filter);

        return modelAndView;
    }

    @Secured({"ROLE_MENAGE_PROJECT","ROLE_ADMIN"})
    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project = new Project();
        modelAndView.addObject("project", project);
        modelAndView.addObject("persons",personRepository.findAll());
        return modelAndView;
    }
    @Secured({"ROLE_MENAGE_PROJECT","ROLE_ADMIN"})
    @PostMapping("/save")
    ModelAndView save(@ModelAttribute @Valid Project project, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("projects/create");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("project", project);
            modelAndView.addObject("persons",personRepository.findAll());
            return modelAndView;
            }

        projectRepository.save(project);
        modelAndView.setViewName("redirect:/projects");
        return modelAndView;
    }
    @Secured({"ROLE_MENAGE_PROJECT","ROLE_ADMIN"})
    @GetMapping("/edit/{id}")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project =  projectRepository.findById(id).orElse(null);
        modelAndView.addObject("project", project);
        modelAndView.addObject("persons",personRepository.findAll());
        return modelAndView;
    }
    @Secured({"ROLE_MENAGE_PROJECT","ROLE_ADMIN"})
    @GetMapping("/delete/{id}")
    String delete(@PathVariable Long id) {
        try {
            projectRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("nie udalo sie usunać projektu " + e);
        }
        return "redirect:/projects";
    }


}



