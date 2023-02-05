package yapp.allround3.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.task.controller.dto.TaskUpdateRequest;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.domain.TaskContent;
import yapp.allround3.task.repository.TaskContentRepository;
import yapp.allround3.task.repository.TaskRepository;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskContentRepository taskContentRepository;

    @Transactional
    public void saveTask(Task task){
        taskRepository.save(task);
    }

    @Transactional
    public void saveTaskContents(List<TaskContent> taskContents){
        taskContentRepository.saveAll(taskContents);
    }

    @Transactional
    public void saveTaskContent(TaskContent taskContent){
        taskContentRepository.save(taskContent);
    }

    public List<Task> findTaskByParticipant(Participant participant){
        return taskRepository.findTasksByParticipant(participant);
    }

    public Task findTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(()->new CustomException("할당된 태스크가 없습니다."));
    }

    public List<TaskContent> findTaskContentsByTask(Task task){
        return taskContentRepository.findAllByTask(task);
    }
    public TaskContent findTaskContentById(Long taskContentId){
        return taskContentRepository.findById(taskContentId).orElseThrow(()->new CustomException("task내부 content를 찾을 수 없습니다."));
    }

    @Transactional
    public void updateTaskContent(TaskUpdateRequest.TaskContentRequest taskContentRequest) {
        TaskContent taskContent = findTaskContentById(taskContentRequest.getTaskContentId());
        taskContent.updateUrl(taskContentRequest.getUrl());
        taskContent.updateTitle(taskContentRequest.getTitle());
        taskContentRepository.save(taskContent);
    }

    @Transactional
    public void updateTask(TaskUpdateRequest taskUpdateRequest) {
        Task task = findTaskById(taskUpdateRequest.getTaskId());
        task.updateTaskStatus(taskUpdateRequest.getTaskStatus());
        task.updateDueDate(taskUpdateRequest.getDueDate());
        task.updateMemo(taskUpdateRequest.getMemo());
        task.updateTitle(taskUpdateRequest.getTitle());
        task.updateStartDate(taskUpdateRequest.getStartDate());
        taskRepository.save(task);
    }
}
