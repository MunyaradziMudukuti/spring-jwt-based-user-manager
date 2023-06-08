package zw.co.munyasys.todocategory.context;

import java.security.Principal;
import java.util.List;

public interface TodoCategoryService {
    TodoCategoryDto create(Principal principal, CreateTodoCategoryCommand createTodoCategoryCommand);

    List<TodoCategoryDto> getCategories(Principal principal);
}
