package yapp.allround3.task.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.domain.TaskContent;
import yapp.allround3.task.domain.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public class TaskResponse {
    @Data
    @NoArgsConstructor
    public static class TaskInfo {
        private Long id;
        private MemberInfo representative;
        private String title;
        private LocalDate startDate;
        private LocalDate dueDate;
        private String memo;
        private TaskStatus taskStatus;
        private int confirmCount;
        private int participantCount;

        private List<TaskContentInfo> taskContents;

        public static TaskInfo of(Task task, Participant participant, int participantCount, List<TaskContent> taskContents) {
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

            List<TaskContentInfo> taskContentInfos = taskContents.stream().map(TaskContentInfo::of).toList();
            taskInfo.setTaskContents(taskContentInfos);

            return taskInfo;
        }
    }

    @Data
    @NoArgsConstructor
    static class MemberInfo {
        private Long participantId;
        private String name;
        private String imageUrl;

        public static MemberInfo of(Participant participant) {
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setParticipantId(participant.getId());
            memberInfo.setName(participant.getMember().getName());
            memberInfo.setImageUrl(participant.getMember().getImageUrl());
            return memberInfo;
        }
    }

    @Data
    @NoArgsConstructor
    static class TaskContentInfo {
        private Long taskContentId;
        private String title;
        private String url;

        public static TaskContentInfo of(TaskContent taskContent) {
            TaskContentInfo taskContentInfo = new TaskContentInfo();
            taskContentInfo.setTaskContentId(taskContent.getId());
            taskContentInfo.setUrl(taskContent.getUrl());
            taskContentInfo.setTitle(taskContent.getTitle());
            return taskContentInfo;
        }
    }
}


