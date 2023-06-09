package zw.co.munyasys.todo.context.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import zw.co.munyasys.todo.context.dto.CreateTodoCommand;
import zw.co.munyasys.todo.context.dto.TodoDto;
import zw.co.munyasys.todo.context.dto.UpdateTodoCommand;
import zw.co.munyasys.todo.context.model.Todo;
import zw.co.munyasys.todocategory.context.mapper.TodoCategoryMapper;

@Mapper(uses = TodoCategoryMapper.class)
public interface TodoMapper {

    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    Todo toEntity(CreateTodoCommand createTodoCommand);

    Todo updateEntity(@MappingTarget Todo todo, UpdateTodoCommand updateTodoCommand);

    TodoDto toDto(Todo todo);
}
