package zw.co.munyasys.todo.context;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface TodoService {
    Page<TodoDto> findAll(Principal principal, Pageable pageable, String searchTerm);

    TodoDto create(Principal currentUser, CreateTodoCommand createTodoCommand);
}
