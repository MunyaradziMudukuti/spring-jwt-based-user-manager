package zw.co.munyasys.todocategory.context;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import zw.co.munyasys.common.exceptions.DuplicateResourceException;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoCategoryServiceImplTest {
    @Mock
    TodoCategoryRepository todoCategoryRepository;
    @Spy
    TodoCategoryMapper mapper = TodoCategoryMapper.INSTANCE;
    @Mock
    UserReaderService userReaderService;
    @InjectMocks
    TodoCategoryServiceImpl todoCategoryService;

    @Captor
    ArgumentCaptor<TodoCategory> todoCategoryArgumentCaptor;

    @Test
    void testCreateThrowsDuplicateResourceExceptionWhenCategoryAlreadyExists() {
        Principal principal = getPrincipal();
        CreateTodoCategoryCommand createTodoCategoryCommand = getCreateCommand();

        when(userReaderService.getLoggedInUser()).thenReturn(getUser());
        when(todoCategoryRepository.existsByUser_UsernameAndName(any(), any())).thenReturn(true);

        assertThatThrownBy(() -> todoCategoryService.create(principal, createTodoCategoryCommand))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining(String.format("Todo Category %s already exists", createTodoCategoryCommand.name()));
    }

    @Test
    void testCreateCategory() {
        Principal principal = getPrincipal();
        User user = getUser();
        CreateTodoCategoryCommand createTodoCategoryCommand = getCreateCommand();

        when(userReaderService.getLoggedInUser()).thenReturn(user);
        when(todoCategoryRepository.existsByUser_UsernameAndName(any(), any())).thenReturn(false);

        todoCategoryService.create(principal, createTodoCategoryCommand);

        verify(todoCategoryRepository).save(todoCategoryArgumentCaptor.capture());

        TodoCategory actualCategory = todoCategoryArgumentCaptor.getValue();

        assertThat(actualCategory.getName()).isEqualTo(createTodoCategoryCommand.name());
        assertThat(actualCategory.getDescription()).isEqualTo(createTodoCategoryCommand.description());
        assertThat(actualCategory.getUser()).isEqualTo(user);

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
                .build();
    }

    private Principal getPrincipal() {
        return () -> "username";
    }

    private CreateTodoCategoryCommand getCreateCommand() {
        return new CreateTodoCategoryCommand(
                "WORK",
                "Work related Todos"
        );
    }
}
