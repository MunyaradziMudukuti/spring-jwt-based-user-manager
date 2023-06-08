package zw.co.munyasys.todocategory.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.co.munyasys.common.exceptions.DuplicateResourceException;
import zw.co.munyasys.common.exceptions.ResourceNotFoundException;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

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

        boolean existsByName = todoCategoryRepository.existsByUser_UsernameAndName(username, createTodoCategoryCommand.name());

        if (existsByName) {
            log.error("Create Todo Category failed for user % -> Todo Category {} already exists {}", username, createTodoCategoryCommand.name());
            throw new DuplicateResourceException(String.format("Todo Category %s already exists", createTodoCategoryCommand.name()));
        }

        final TodoCategory todoCategory = mapper.toEntity(createTodoCategoryCommand);

        todoCategory.setUser(user);

        return mapper.toDto(todoCategoryRepository.save(todoCategory));
    }

    @Override
    public List<TodoCategoryDto> getCategories(Principal principal) {
        String username = principal.getName();
        List<TodoCategory> todoCategories = todoCategoryRepository.findByUser_Username(username);
        return mapper.toDtos(todoCategories);
    }

    @Override
    public TodoCategoryDto update(UUID todoCategoryId, Principal currentUser, TodoCategoryCommand updateTodoCategoryCommand) {

        String username = currentUser.getName();

        TodoCategory todoCategory = todoCategoryRepository.findById(todoCategoryId)
                .orElseThrow(() -> {
                    log.error("Update Todo Category failed for user {} -> Todo Category with id {} not found", username, todoCategoryId);
                    return new ResourceNotFoundException(String.format("Todo Category with id %s not found", todoCategoryId));
                });

        boolean existsByName = todoCategoryRepository.existsByIdIsNotAndUser_UsernameAndName(todoCategoryId, username, updateTodoCategoryCommand.name());

        if (existsByName) {
            log.error("Update Todo Category failed for user {} -> Todo Category {} already exists", username, updateTodoCategoryCommand.name());
            throw new DuplicateResourceException(String.format("Todo Category %s already exists", updateTodoCategoryCommand.name()));
        }

        todoCategory.setName(updateTodoCategoryCommand.name());
        todoCategory.setDescription(updateTodoCategoryCommand.description());

        return mapper.toDto(todoCategoryRepository.save(todoCategory));
    }
}
