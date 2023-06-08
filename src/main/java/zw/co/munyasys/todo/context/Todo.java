package zw.co.munyasys.todo.context;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import zw.co.munyasys.common.jpa.BaseEntity;
import zw.co.munyasys.common.utils.BasicFormats;
import zw.co.munyasys.todocategory.context.TodoCategory;
import zw.co.munyasys.users.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 3, max = 50)
    private String title;

    @Size(max = 250)
    private String comment;

    private boolean isCompleted;

    @DateTimeFormat(pattern = BasicFormats.FULL_DATE_TIME)
    private LocalDateTime dueDateTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "todo_category_id")
    private TodoCategory todoCategory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

}
