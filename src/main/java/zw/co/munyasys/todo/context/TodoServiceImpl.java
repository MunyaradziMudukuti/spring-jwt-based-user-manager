package zw.co.munyasys.todo.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.munyasys.todocategory.context.TodoCategory;
import zw.co.munyasys.todocategory.context.TodoCategoryService;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final TodoCategoryService todoCategoryService;
    private final UserReaderService userReaderService;
    private final TodoMapper mapper;

    public TodoServiceImpl(TodoRepository todoRepository, TodoCategoryService todoCategoryService, UserReaderService userReaderService, TodoMapper mapper) {
        this.todoRepository = todoRepository;
        this.todoCategoryService = todoCategoryService;
        this.userReaderService = userReaderService;
        this.mapper = mapper;
    }

    @Override
    public Page<TodoDto> findAll(Principal principal, Pageable pageable, String searchTerm) {

        String username = principal.getName();

        return null;
    }

    @Override
    public TodoDto create(Principal currentUser, CreateTodoCommand createTodoCommand) {
        final User user = userReaderService.getLoggedInUser();
        String username = user.getUsername();

        final Todo todo = mapper.toEntity(createTodoCommand);

        TodoCategory todoCategory = todoCategoryService.findByIdAndUsername(createTodoCommand.todoCategoryId(), username);

        todo.setUser(user);
        todo.setTodoCategory(todoCategory);

        return mapper.toDto(todoRepository.save(todo));
    }
}
