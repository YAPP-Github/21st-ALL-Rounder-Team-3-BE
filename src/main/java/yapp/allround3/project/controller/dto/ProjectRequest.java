package yapp.allround3.project.controller.dto;

import java.time.LocalDate;

import lombok.Data;
import yapp.allround3.project.domain.Project;

@Data
public class ProjectRequest {

	private String name;
	private LocalDate startDate;
	private LocalDate dueDate;

	public ProjectRequest(Project project) {
		this.name = project.getName();
		this.startDate = project.getStartDate();
		this.dueDate = project.getDueDate();
	}
}
