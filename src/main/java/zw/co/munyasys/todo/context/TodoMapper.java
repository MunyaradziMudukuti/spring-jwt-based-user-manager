package zw.co.munyasys.todo.context;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import zw.co.munyasys.todocategory.context.TodoCategoryMapper;

@Mapper(uses = TodoCategoryMapper.class)
public interface TodoMapper {

    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    Todo toEntity(CreateTodoCommand createTodoCommand);

    TodoDto toDto(Todo todo);
}
