package zw.co.munyasys.todo.context;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Page<TodoDto> findAll(Principal principal, Pageable pageable, String searchTerm) {

        String username = principal.getName();

        return null;
    }
}
