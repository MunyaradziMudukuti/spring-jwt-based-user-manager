package zw.co.munyasys.todocategory.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

import static zw.co.munyasys.common.Constants.DEFAULT_CATEGORY;

@Slf4j
@Service
public class TodoCategoryServiceImpl implements TodoCategoryService {

    private final TodoCategoryRepository todoCategoryRepository;
    private final TodoCategoryMapper mapper;
    private final UserReaderService userReaderService;

    public TodoCategoryServiceImpl(TodoCategoryRepository todoCategoryRepository, TodoCategoryMapper mapper, UserReaderService userReaderService) {
        this.todoCategoryRepository = todoCategoryRepository;
        this.mapper = mapper;
        this.userReaderService = userReaderService;
    }

    @Override
    public TodoCategoryDto create(Principal principal, TodoCategoryCommand createTodoCategoryCommand) {

        final User user = userReaderService.getLoggedInUser();
        String username = user.getUsername();

        String categoryName = createTodoCategoryCommand.name();
        boolean existsByName = todoCategoryRepository.existsByUser_UsernameAndName(username, createTodoCategoryCommand.name());

        if (existsByName || categoryName.equals(DEFAULT_CATEGORY)) {
            log.error("Create Todo Category failed for user % -> Todo Category {} already exists {}", username, categoryName);
            throw new DuplicateResourceException(String.format("Todo Category %s already exists", categoryName));
        }

        final TodoCategory todoCategory = mapper.toEntity(createTodoCategoryCommand);

        todoCategory.setUser(user);

        return mapper.toDto(todoCategoryRepository.save(todoCategory));
    }

    @Override
    public List<TodoCategoryDto> getCategories(Principal principal) {
        String username = principal.getName();
        List<TodoCategory> todoCategories = todoCategoryRepository.findByUser_UsernameOrName(username, DEFAULT_CATEGORY);
        return mapper.toDtos(todoCategories);
    }

    @Override
    public TodoCategoryDto update(UUID todoCategoryId, Principal currentUser, TodoCategoryCommand updateTodoCategoryCommand) {

        String username = currentUser.getName();

        TodoCategory todoCategory = findByIdAndUsername(todoCategoryId, username);

        boolean existsByName = todoCategoryRepository.existsByIdIsNotAndUser_UsernameAndName(todoCategoryId, username, updateTodoCategoryCommand.name());

        if (existsByName) {
            log.error("Update Todo Category failed for user {} -> Todo Category {} already exists", username, updateTodoCategoryCommand.name());
            throw new DuplicateResourceException(String.format("Todo Category %s already exists", updateTodoCategoryCommand.name()));
        }

        todoCategory.setName(updateTodoCategoryCommand.name());
        todoCategory.setDescription(updateTodoCategoryCommand.description());

        return mapper.toDto(todoCategoryRepository.save(todoCategory));
    }

    @Override
    public TodoCategory findByIdAndUsername(UUID id, String username) {
        return todoCategoryRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Todo Category with id %s not found for user %s", id, username)));
    }

    @Override
    public TodoCategory fetchUserCategoryOrDefaultById(UUID id, String username) {
        Optional<TodoCategory> optionalTodoCategory = todoCategoryRepository.findByIdAndUser_Username(id, username);
        return optionalTodoCategory.orElseGet(() -> todoCategoryRepository.findByIdAndNameIgnoreCase(id, DEFAULT_CATEGORY)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Todo Category with id %s not found for user %s", id, username))));
    }
}
