package yapp.allround3.project.controller.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import yapp.allround3.participant.controller.dto.ParticipantDto;
import yapp.allround3.project.domain.Difficulty;
import yapp.allround3.project.domain.Project;

@Data
@AllArgsConstructor
public class ProjectResponse {

	private String name;
	private LocalDate startDate;
	private LocalDate dueDate;
	private Long dDay;
	private String goal;
	private Difficulty difficulty;
	private Long progress;
	private List<ParticipantDto> participantInfos;

	public static ProjectResponse of(Project project, List<ParticipantDto> participantDtos) {

		/*
		TODO - 프로젝트 종료 여부도 status로 만들지 논의해야 할 것 같다. 아니면 Dday, 진행률 계산이 생각처럼 안나올 듯함.
		EX) Project status=종료 라면 dday=0, 진행정도=100으로 세팅하도록 하는 로직 추가하면 될듯?
		 */

		return new ProjectResponse(
				project.getName(),
				project.getStartDate(),
				project.getDueDate(),
				calculateDuration(LocalDate.now(),project.getDueDate()),
				project.getGoal(),
				project.getDifficulty(),
				calculateProgress(project.getStartDate(),project.getDueDate()),
				participantDtos
		);
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
