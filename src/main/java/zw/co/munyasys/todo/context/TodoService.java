package zw.co.munyasys.todo.context;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.munyasys.todo.context.dto.CreateTodoCommand;
import zw.co.munyasys.todo.context.dto.TodoDto;
import zw.co.munyasys.todo.context.dto.UpdateTodoCommand;

import java.security.Principal;
import java.util.UUID;

public interface TodoService {
    Page<TodoDto> findAll(Principal principal, Pageable pageable, String searchTerm);

    TodoDto create(Principal currentUser, CreateTodoCommand createTodoCommand);

    TodoDto update(UUID todoId, Principal currentUser, UpdateTodoCommand updateTodoCommand);
}
