package zw.co.munyasys.todocategory.context;

import zw.co.munyasys.todocategory.context.dto.TodoCategoryCommand;
import zw.co.munyasys.todocategory.context.dto.TodoCategoryDto;
import zw.co.munyasys.todocategory.context.model.TodoCategory;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface TodoCategoryService {
    TodoCategoryDto create(Principal principal, TodoCategoryCommand createTodoCategoryCommand);

    List<TodoCategoryDto> getCategories(Principal principal);

    TodoCategoryDto update(UUID todoCategoryId, Principal currentUser, TodoCategoryCommand updateTodoCategoryCommand);

    TodoCategory findByIdAndUsername(UUID id, String username);
}
