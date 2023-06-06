package zw.co.munyasys.todo.context;

import zw.co.munyasys.todocategory.context.TodoCategoryDto;

import java.util.UUID;

public record TodoDto(
        UUID id,
        String title,
        String comment,
        boolean isCompleted,
        TodoCategoryDto todoCategory
) {
}
