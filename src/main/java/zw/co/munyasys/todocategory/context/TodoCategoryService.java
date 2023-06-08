package zw.co.munyasys.todocategory.context;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface TodoCategoryService {
    TodoCategoryDto create(Principal principal, TodoCategoryCommand createTodoCategoryCommand);

    List<TodoCategoryDto> getCategories(Principal principal);

    TodoCategoryDto update(UUID todoCategoryId, Principal currentUser, TodoCategoryCommand updateTodoCategoryCommand);

    TodoCategory findByIdAndUsername(UUID id, String username);
}
