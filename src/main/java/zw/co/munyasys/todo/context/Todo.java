package zw.co.munyasys.todo.context;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import zw.co.munyasys.common.jpa.BaseEntity;
import zw.co.munyasys.todocategory.context.TodoCategory;
import zw.co.munyasys.users.model.User;

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

    @ManyToOne
    private TodoCategory todoCategory;

    @ManyToOne
    @Column(updatable = false)
    private User user;

}
