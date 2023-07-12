package zw.co.munyasys.todo.context.dto;

import zw.co.munyasys.todocategory.context.dto.TodoCategoryDto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TodoDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDateTime,
        boolean completed,
        TodoCategoryDto todoCategory
) {
}
