package zw.co.munyasys.todocategory.context;

import org.springframework.stereotype.Service;
import zw.co.munyasys.common.exceptions.DuplicateResourceException;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.security.Principal;

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
    public TodoCategoryDto create(Principal principal, CreateTodoCategoryCommand createTodoCategoryCommand) {

        final User user = userReaderService.getLoggedInUser();
        String username = user.getUsername();

        boolean existsByName = todoCategoryRepository.existsByUser_UsernameAndName(username, createTodoCategoryCommand.name());

        if (existsByName) {
            throw new DuplicateResourceException(String.format("Todo Category %s already exists", createTodoCategoryCommand.name()));
        }

        final TodoCategory todoCategory = mapper.toEntity(createTodoCategoryCommand);

        todoCategory.setUser(user);

        return mapper.toDto(todoCategoryRepository.save(todoCategory));
    }
}
