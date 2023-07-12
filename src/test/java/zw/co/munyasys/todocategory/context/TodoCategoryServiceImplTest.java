package zw.co.munyasys.todocategory.context;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import zw.co.munyasys.common.exceptions.DuplicateResourceException;
import zw.co.munyasys.common.exceptions.ResourceNotFoundException;
import zw.co.munyasys.todocategory.context.dao.TodoCategoryRepository;
import zw.co.munyasys.todocategory.context.dto.TodoCategoryCommand;
import zw.co.munyasys.todocategory.context.dto.TodoCategoryDto;
import zw.co.munyasys.todocategory.context.mapper.TodoCategoryMapper;
import zw.co.munyasys.todocategory.context.model.TodoCategory;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static zw.co.munyasys.common.Constants.DEFAULT_CATEGORY;

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

    private final UUID todoCategoryId = UUID.randomUUID();

    @Test
    void testCreateThrowsDuplicateResourceExceptionWhenCategoryAlreadyExists() {
        Principal principal = getPrincipal();
        TodoCategoryCommand createTodoCategoryCommand = getCommand();

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
        TodoCategoryCommand createTodoCategoryCommand = getCommand();

        when(userReaderService.getLoggedInUser()).thenReturn(user);
        when(todoCategoryRepository.existsByUser_UsernameAndName(any(), any())).thenReturn(false);

        todoCategoryService.create(principal, createTodoCategoryCommand);

        verify(todoCategoryRepository).save(todoCategoryArgumentCaptor.capture());

        TodoCategory actualCategory = todoCategoryArgumentCaptor.getValue();

        assertThat(actualCategory.getName()).isEqualTo(createTodoCategoryCommand.name());
        assertThat(actualCategory.getDescription()).isEqualTo(createTodoCategoryCommand.description());
        assertThat(actualCategory.getUser()).isEqualTo(user);

    }

    @Test
    void testGetCategories() {
        Principal principal = getPrincipal();
        TodoCategory todoCategory = getCategory();

        when(todoCategoryRepository.findByUser_UsernameOrName(any(), any())).thenReturn(List.of(todoCategory));

        List<TodoCategoryDto> todoCategoryDtos = todoCategoryService.getCategories(principal);

        verify(todoCategoryRepository).findByUser_UsernameOrName(principal.getName(), DEFAULT_CATEGORY);

        TodoCategoryDto actualCategory = todoCategoryDtos.get(0);

        assertThat(actualCategory.name()).isEqualTo(todoCategory.getName());
        assertThat(actualCategory.description()).isEqualTo(todoCategory.getDescription());

    }

    @Test
    void testUpdateThrowsDuplicateResourceExceptionWhenCategoryAlreadyExists() {
        Principal principal = getPrincipal();
        TodoCategory todoCategory = getCategory();
        TodoCategoryCommand updateTodoCategoryCommand = getCommand();

        when(todoCategoryRepository.findByIdAndUser_Username(any(), any())).thenReturn(Optional.of(todoCategory));
        when(todoCategoryRepository.existsByIdIsNotAndUser_UsernameAndName(any(), any(), any())).thenReturn(true);

        assertThatThrownBy(() -> todoCategoryService.update(todoCategoryId, principal, updateTodoCategoryCommand))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining(String.format("Todo Category %s already exists", updateTodoCategoryCommand.name()));
    }

    @Test
    void testUpdateCategory() {
        Principal principal = getPrincipal();
        TodoCategory todoCategory = getCategory();
        TodoCategoryCommand updateTodoCategoryCommand = getCommand();

        when(todoCategoryRepository.findByIdAndUser_Username(any(), any())).thenReturn(Optional.of(todoCategory));
        when(todoCategoryRepository.existsByIdIsNotAndUser_UsernameAndName(any(), any(), any())).thenReturn(false);

        todoCategoryService.update(todoCategoryId, principal, updateTodoCategoryCommand);

        verify(todoCategoryRepository).save(todoCategoryArgumentCaptor.capture());

        TodoCategory actualCategory = todoCategoryArgumentCaptor.getValue();

        assertThat(actualCategory.getName()).isEqualTo(updateTodoCategoryCommand.name());
        assertThat(actualCategory.getDescription()).isEqualTo(updateTodoCategoryCommand.description());
        assertThat(actualCategory.getUser()).isNotNull();

    }

    @Test
    void testFindByIdAndUsername() {
        String username = "username";
        TodoCategory todoCategory = getCategory();
        when(todoCategoryRepository.findByIdAndUser_Username(any(), any())).thenReturn(Optional.of(todoCategory));

        todoCategoryService.findByIdAndUsername(todoCategoryId, username);

        verify(todoCategoryRepository).findByIdAndUser_Username(todoCategoryId, username);
    }

    @Test
    void testFindByIdAndUsernameThrowsResourceNotFoundExceptionWhenCategoryIsNotFound() {
        String username = "username";
        when(todoCategoryRepository.findByIdAndUser_Username(any(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> todoCategoryService.findByIdAndUsername(todoCategoryId, username))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(String.format("Todo Category with id %s not found for user %s", todoCategoryId, username));

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
                .enabled(true)
                .username("test@mail.com")


                .build();
    }

    private Principal getPrincipal() {
        return () -> "username";
    }

    private TodoCategoryCommand getCommand() {
        return new TodoCategoryCommand(
                "WORK",
                "Work related Todos"
        );
    }

}
