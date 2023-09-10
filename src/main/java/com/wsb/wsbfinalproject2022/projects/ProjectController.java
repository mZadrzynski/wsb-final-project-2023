package com.wsb.wsbfinalproject2022.projects;

import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
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

    //TODO: @secured
    @GetMapping()
    ModelAndView index(@ModelAttribute ProjectFilter filter) {
        ModelAndView modelAndView = new ModelAndView("projects/index");
        modelAndView.addObject("projects", projectRepository.findAll(filter.buildQuery()));
        modelAndView.addObject("creators", projectService.findAll());
        modelAndView.addObject("filter", filter);

        return modelAndView;
    }

    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project = new Project();
        modelAndView.addObject("project", project);
        modelAndView.addObject("persons",personRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/save")
    String save(@ModelAttribute Project project) {
        Boolean isNew = project.getId() == null;
        projectRepository.save(project);

        if (isNew) {
            return "redirect:/projects";
        } else {
            return "redirect:/projects/edit/" + project.getId();
        }

    }

    @GetMapping("/edit/{id}")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project =  projectRepository.findById(id).orElse(null);
        modelAndView.addObject("project", project);
        modelAndView.addObject("persons",personRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    String delete(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return "redirect:/projects";
    }
}
