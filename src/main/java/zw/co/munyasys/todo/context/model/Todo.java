package zw.co.munyasys.todo.context.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import zw.co.munyasys.common.jpa.BaseEntity;
import zw.co.munyasys.common.utils.BasicFormats;
import zw.co.munyasys.todocategory.context.model.TodoCategory;
import zw.co.munyasys.users.model.User;

import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Todo extends BaseEntity {

    @Size(min = 3, max = 50)
    private String title;

    @Size(max = 250)
    private String comment;

    private boolean completed;

    @DateTimeFormat(pattern = BasicFormats.FULL_DATE_TIME)
    private LocalDateTime dueDateTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "todo_category_id")
    private TodoCategory todoCategory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public TodoCategory getTodoCategory() {
        return todoCategory;
    }

    public void setTodoCategory(TodoCategory todoCategory) {
        this.todoCategory = todoCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
