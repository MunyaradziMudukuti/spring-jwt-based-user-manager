package zw.co.munyasys.todocategory.context;

import java.security.Principal;

public interface TodoCategoryService {
    TodoCategoryDto create(Principal principal, CreateTodoCategoryCommand createTodoCategoryCommand);
}
