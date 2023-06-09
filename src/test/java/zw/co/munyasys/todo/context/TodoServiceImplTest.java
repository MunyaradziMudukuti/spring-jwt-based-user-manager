package zw.co.munyasys.todo.context;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import zw.co.munyasys.common.exceptions.ResourceNotFoundException;
import zw.co.munyasys.todo.context.dao.TodoRepository;
import zw.co.munyasys.todo.context.dto.CreateTodoCommand;
import zw.co.munyasys.todo.context.dto.UpdateTodoCommand;
import zw.co.munyasys.todo.context.mapper.TodoMapper;
import zw.co.munyasys.todo.context.model.Todo;
import zw.co.munyasys.todocategory.context.TodoCategoryService;
import zw.co.munyasys.todocategory.context.mapper.TodoCategoryMapper;
import zw.co.munyasys.todocategory.context.model.TodoCategory;
import zw.co.munyasys.users.model.Gender;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {
    private final UUID todoCategoryId = UUID.randomUUID();
    private final UUID todoId = UUID.randomUUID();
    @Mock
    TodoRepository todoRepository;
    @Mock
    UserReaderService userReaderService;
    @Mock
    TodoCategoryService todoCategoryService;
    @Spy
    TodoCategoryMapper todoCategoryMapper = TodoCategoryMapper.INSTANCE;
    @InjectMocks
    TodoMapper mapper = Mockito.spy(TodoMapper.INSTANCE);
    @InjectMocks
    TodoServiceImpl todoService;
    @Captor
    ArgumentCaptor<Todo> todoArgumentCaptor;

    @Test
    void testCreateTodo() {
        Principal principal = getPrincipal();
        TodoCategory todoCategory = getCategory();
        User user = getUser();
        CreateTodoCommand createTodoCommand = getCreateTodoCommand();

        when(userReaderService.getLoggedInUser()).thenReturn(user);
        when(todoCategoryService.findByIdAndUsername(any(), anyString())).thenReturn(todoCategory);

        todoService.create(principal, createTodoCommand);

        verify(todoRepository).save(todoArgumentCaptor.capture());

        Todo actualTodo = todoArgumentCaptor.getValue();

        assertThat(actualTodo.getTitle()).isEqualTo(createTodoCommand.title());
        assertThat(actualTodo.getComment()).isEqualTo(createTodoCommand.comment());
        assertThat(actualTodo.getDueDateTime()).isEqualTo(createTodoCommand.dueDateTime());
        assertThat(actualTodo.getTodoCategory().getId()).isEqualTo(todoCategoryId);
        assertThat(actualTodo.isCompleted()).isFalse();
    }

    @Test
    void testUpdate() {
        Principal principal = getPrincipal();
        Todo todo = getTodo();
        UUID todoCategoryId = UUID.randomUUID();
        TodoCategory todoCategory = getCategory();
        todoCategory.setId(todoCategoryId);
        UpdateTodoCommand updateTodoCommand = new UpdateTodoCommand(
                "Go to gym",
                "Got to the gym and do push ups",
                todoCategoryId,
                LocalDateTime.now(),
                true
        );

        when(todoRepository.findByIdAndUser_Username(any(), anyString())).thenReturn(Optional.of(todo));
        when(todoCategoryService.findByIdAndUsername(any(), anyString())).thenReturn(todoCategory);

        todoService.update(todoId, principal, updateTodoCommand);

        verify(todoRepository).save(todoArgumentCaptor.capture());

        Todo actualTodo = todoArgumentCaptor.getValue();

        assertThat(actualTodo.getTitle()).isEqualTo(updateTodoCommand.title());
        assertThat(actualTodo.getComment()).isEqualTo(updateTodoCommand.comment());
        assertThat(actualTodo.getDueDateTime()).isEqualTo(updateTodoCommand.dueDateTime());
        assertThat(actualTodo.getTodoCategory().getId()).isEqualTo(todoCategoryId);
        assertThat(actualTodo.isCompleted()).isEqualTo(updateTodoCommand.completed());
    }

    @Test
    void testUpdateThrowsResourceNotFoundExceptionWhenTodoIsNotFound() {
        Principal principal = getPrincipal();
        Todo todo = getTodo();
        UUID todoCategoryId = UUID.randomUUID();
        TodoCategory todoCategory = getCategory();
        todoCategory.setId(todoCategoryId);

        UpdateTodoCommand updateTodoCommand = new UpdateTodoCommand(
                "Go to gym",
                "Got to the gym and do push ups",
                todoCategoryId,
                LocalDateTime.now(),
                true
        );

        when(todoRepository.findByIdAndUser_Username(any(), anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> todoService.update(todoId, principal, updateTodoCommand))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(String.format("Todo %s not found for user %s", todoId, principal.getName()));
    }

    private Principal getPrincipal() {
        return () -> "username";
    }

    private Todo getTodo() {
        return Todo.builder()
                .title("Go to work")
                .comment("Got to work and write some code dude")
                .todoCategory(getCategory())
                .completed(false)
                .dueDateTime(LocalDateTime.now())
                .build();
    }


    private CreateTodoCommand getCreateTodoCommand() {
        return new CreateTodoCommand(
                "Go to work",
                "Got to work and write some code dude",
                todoCategoryId,
                LocalDateTime.now()
        );
    }

    private UpdateTodoCommand getUpdateTodoCommand() {
        return new UpdateTodoCommand(
                "Go to gym",
                "Got to the gym and do push ups",
                todoCategoryId,
                LocalDateTime.now(),
                true
        );
    }

    private TodoCategory getCategory() {
        TodoCategory todoCategory = TodoCategory.builder()
                .user(getUser())
                .name("WORK")
                .description("Work Related")
                .build();
        todoCategory.setId(todoCategoryId);
        return todoCategory;
    }


    private User getUser() {
        return User.builder()
                .email("test@mail.com")
                .dateOfBirth(LocalDate.of(1999, 3, 4))
                .enabled(true)
                .firstName("Munyaradzi Linos")
                .lastName("Mudukuti")
                .mobileNumber("0772994739")
                .username("test@mail.com")
                .gender(Gender.MALE)
                .build();
    }


}