package yapp.allround3.task.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.domain.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public class TaskResponse {
    @Data
    @NoArgsConstructor
    public static class TaskInfo{
        private Long id;
        private MemberInfo representative;
        private String title;
        private LocalDate startDate;
        private LocalDate dueDate;
        private String memo;
        private TaskStatus taskStatus;
        private int confirmCount;
        private int participantCount;

        public static TaskInfo of(Task task, Participant participant,int participantCount){
            TaskInfo taskInfo = new TaskInfo();
            MemberInfo representative = MemberInfo.of(participant);

            taskInfo.setId(task.getId());
            taskInfo.setRepresentative(representative);
            taskInfo.setMemo(task.getMemo());
            taskInfo.setTitle(task.getTitle());
            taskInfo.setStartDate(task.getStartDate());
            taskInfo.setDueDate(task.getDueDate());
            taskInfo.setTaskStatus(task.getStatus());
            taskInfo.setConfirmCount(task.getConfirmCount());
            taskInfo.setParticipantCount(participantCount);
            return taskInfo;
        }
    }

    //태스크 단건 조회시 피드백한 인원/안한 인원의 정보까지 조회하는 dto
    @Data
    @NoArgsConstructor
    public static class DetailedTaskInfo{
        private TaskInfo taskInfo;
        private List<MemberInfo> confirmedMemberInfos;

        public static DetailedTaskInfo of(Task task, Participant participant, int participantCount,
                                          List<Participant> confirmed){
            //participantCount - task entity에 포함 시키는 게 나아보임
            DetailedTaskInfo detailedTaskInfo = new DetailedTaskInfo();
            TaskInfo taskInfo = TaskInfo.of(task,participant,participantCount);
            detailedTaskInfo.setTaskInfo(taskInfo);
            List<MemberInfo> confirmedMemberInfos = confirmed.stream().map(MemberInfo::of).toList();
            detailedTaskInfo.setConfirmedMemberInfos(confirmedMemberInfos);

            return detailedTaskInfo;
        }
    }

    @Data
    @NoArgsConstructor
    static class MemberInfo {
        private Long participantId;
        private String name;
        private String imageUrl;

        public static MemberInfo of(Participant participant){
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setParticipantId(participant.getId());
            memberInfo.setName(participant.getMember().getName());
            memberInfo.setImageUrl(participant.getMember().getImageUrl());
            return memberInfo;
        }
    }
}

