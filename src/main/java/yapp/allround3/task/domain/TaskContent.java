package yapp.allround3.task.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TaskContent {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id")
    private Task task;

    @Builder
    public TaskContent(String title,String url, Task task){
        this.title = title;
        this.url = url;
        this.task = task;
    }
}
