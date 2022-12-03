package yapp.allround3.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import yapp.allround3.project.service.ProjectService;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;
}
