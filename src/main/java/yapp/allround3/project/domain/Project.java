package yapp.allround3.project.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
	@Builder
	private Project(String name, LocalDate startDate, LocalDate dueDate,String goal, Difficulty difficulty) {
		this.name = name;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.goal = goal;
		this.difficulty = difficulty;
	}
}
