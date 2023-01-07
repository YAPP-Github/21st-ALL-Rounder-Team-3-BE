package yapp.allround3.task.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.repository.TaskRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;


    public List<Task> findTaskByParticipant(Participant participant){
        return taskRepository.findTasksByParticipant(participant);
    }


    //member Service 로 이동
    public Task findTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(()->new CustomException("할당된 태스크가 없습니다."));
    }
}
