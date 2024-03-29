package yapp.allround3.project.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import yapp.allround3.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "project_id")
	private Long id;

	private String name;

	private LocalDate startDate;

	private LocalDate dueDate;

	private String goal;

	@Enumerated(value = EnumType.STRING)
	private Difficulty difficulty;

	@Enumerated(value = EnumType.STRING)
	private ProjectStatus projectStatus;

	@Enumerated(value = EnumType.STRING)
	private ProjectImage projectImage;

	@Builder
	private Project(String name,
		LocalDate startDate,
		LocalDate dueDate,
		String goal,
		Difficulty difficulty,
		ProjectStatus projectStatus,
		ProjectImage projectImage
	) {
		this.name = name;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.goal = goal;
		this.difficulty = difficulty;
		this.projectStatus= projectStatus;
		this.projectImage = projectImage;
	}


	public void updateName(String name) {
		this.name = name;
	}

	public void updateStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void updateDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public void updateGoal(String goal) {
		this.goal = goal;
	}

	public void delete() {
		projectStatus = ProjectStatus.DELETED;
	}
}
