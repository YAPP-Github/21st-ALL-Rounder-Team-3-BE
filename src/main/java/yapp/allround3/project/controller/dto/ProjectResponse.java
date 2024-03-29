package yapp.allround3.project.controller.dto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yapp.allround3.participant.controller.dto.ParticipantDto;
import yapp.allround3.project.domain.Difficulty;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.domain.ProjectStatus;


@Data
@NoArgsConstructor
@JsonPropertyOrder({"id","name","startDate","dueDate","dDay","goal","difficulty","projectStatus","progress","participantInfos"})
public class ProjectResponse {
	private Long id;
	private String name;
	private LocalDate startDate;
	private LocalDate dueDate;
	private Long dDay;
	private String goal;
	private Difficulty difficulty;
	private ProjectStatus projectStatus;
	private Long progress;
	private List<ParticipantDto> participantInfos;
	private String projectThumbnailUrl;
	private String teamThumbnailUrl;
	private Long myParticipantId;

	public static ProjectResponse of(Project project, Long myParticipantId, List<ParticipantDto> participantDtos){

		ProjectResponse projectResponse = new ProjectResponse();
		projectResponse.setId(project.getId());
		projectResponse.setName(project.getName());
		projectResponse.setStartDate(project.getStartDate());
		projectResponse.setDueDate(project.getDueDate());
		projectResponse.setDDay(calculateDuration(LocalDate.now(),project.getDueDate()));
		projectResponse.setProjectStatus(project.getProjectStatus());
		projectResponse.setGoal(project.getGoal());
		projectResponse.setDifficulty(project.getDifficulty());
		projectResponse.setProjectStatus(project.getProjectStatus());
		projectResponse.setMyParticipantId(myParticipantId);
		projectResponse.setProjectThumbnailUrl(project.getProjectImage().getProjectThumbnailUrl());
		projectResponse.setTeamThumbnailUrl(project.getProjectImage().getTeamThumbnailUrl());
		if(project.getProjectStatus()==ProjectStatus.COMPLETED){
			projectResponse.setProgress(100L);
		}
		else {
			projectResponse.setProgress(calculateProgress(project.getStartDate(), project.getDueDate()));
		}
		projectResponse.setParticipantInfos(participantDtos);

		return projectResponse;
	}

	public static Long calculateDuration(LocalDate start, LocalDate end){
		return ChronoUnit.DAYS.between(start,end);
	}

	public static Long calculateProgress(LocalDate startDate, LocalDate endDate){
		long projectDuration = calculateDuration(startDate,endDate);
		long progressed = calculateDuration(startDate,LocalDate.now());
		return (long)((float) progressed/projectDuration*100);
	}


}
