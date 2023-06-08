package zw.co.munyasys.todo.context;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import zw.co.munyasys.todocategory.context.TodoCategory;
import zw.co.munyasys.todocategory.context.TodoCategoryMapper;
import zw.co.munyasys.todocategory.context.TodoCategoryService;
import zw.co.munyasys.users.model.Gender;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {
    private final UUID todoCategoryId = UUID.randomUUID();
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

    private CreateTodoCommand getCreateTodoCommand() {
        return new CreateTodoCommand(
                "Go to work",
                "Got to work and write some code dude",
                todoCategoryId,
                LocalDateTime.now()
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

    private Principal getPrincipal() {
        return () -> "username";
    }
}