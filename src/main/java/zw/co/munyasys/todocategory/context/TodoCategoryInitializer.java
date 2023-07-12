package zw.co.munyasys.todocategory.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import zw.co.munyasys.todocategory.context.dao.TodoCategoryRepository;
import zw.co.munyasys.todocategory.context.model.TodoCategory;

import java.util.Optional;

import static zw.co.munyasys.common.Constants.DEFAULT_CATEGORY;

@Slf4j
@Component
public class TodoCategoryInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final TodoCategoryRepository todoCategoryRepository;

    public TodoCategoryInitializer(TodoCategoryRepository todoCategoryRepository) {
        this.todoCategoryRepository = todoCategoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Optional<TodoCategory> optionalTodoCategory = todoCategoryRepository.findByNameIgnoreCase(DEFAULT_CATEGORY);

        if (optionalTodoCategory.isEmpty()) {
            TodoCategory todoCategory = TodoCategory.builder()
                    .name(DEFAULT_CATEGORY)
                    .description("Default category")
                    .build();
            todoCategoryRepository.save(todoCategory);
            log.info("############ Default Todo Category created");
        }

    }
}
