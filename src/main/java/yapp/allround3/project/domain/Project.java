package yapp.allround3.project.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

	@Id
	@GeneratedValue
	@Column(name = "project_id")
	private Long id;

	private String name;

	private LocalDate startDate;

	private LocalDate dueDate;

	@Builder
	private Project(String name, LocalDate startDate, LocalDate dueDate) {
		this.name = name;
		this.startDate = startDate;
		this.dueDate = dueDate;
	}
}
