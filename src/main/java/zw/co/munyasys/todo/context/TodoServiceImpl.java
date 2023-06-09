package zw.co.munyasys.todo.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.munyasys.common.exceptions.ResourceNotFoundException;
import zw.co.munyasys.todo.context.dao.TodoRepository;
import zw.co.munyasys.todo.context.dto.CreateTodoCommand;
import zw.co.munyasys.todo.context.dto.TodoDto;
import zw.co.munyasys.todo.context.dto.UpdateTodoCommand;
import zw.co.munyasys.todo.context.mapper.TodoMapper;
import zw.co.munyasys.todo.context.model.Todo;
import zw.co.munyasys.todocategory.context.TodoCategoryService;
import zw.co.munyasys.todocategory.context.dao.TodoCategoryRepository;
import zw.co.munyasys.todocategory.context.model.TodoCategory;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoCategoryRepository todoCategoryRepository;
    private final TodoRepository todoRepository;
    private final TodoCategoryService todoCategoryService;
    private final UserReaderService userReaderService;
    private final TodoMapper mapper;

    public TodoServiceImpl(TodoRepository todoRepository, TodoCategoryService todoCategoryService, UserReaderService userReaderService, TodoMapper mapper,
                           TodoCategoryRepository todoCategoryRepository) {
        this.todoRepository = todoRepository;
        this.todoCategoryService = todoCategoryService;
        this.userReaderService = userReaderService;
        this.mapper = mapper;
        this.todoCategoryRepository = todoCategoryRepository;
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

    @Override
    public TodoDto update(UUID todoId, Principal currentUser, UpdateTodoCommand updateTodoCommand) {

        String username = currentUser.getName();

        Todo todo = todoRepository.findByIdAndUser_Username(todoId, username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Todo %s not found for user %s", todoId, username)));

        TodoCategory todoCategory = todoCategoryService.findByIdAndUsername(updateTodoCommand.todoCategoryId(), username);

        todo = mapper.updateEntity(todo, updateTodoCommand);

        todo.setTodoCategory(todoCategory);

        return mapper.toDto(todoRepository.save(todo));
    }
}
