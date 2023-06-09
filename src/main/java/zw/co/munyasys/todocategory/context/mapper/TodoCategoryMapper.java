package zw.co.munyasys.todocategory.context.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import zw.co.munyasys.todocategory.context.model.TodoCategory;

import java.util.List;

@Mapper
public interface TodoCategoryMapper {

    TodoCategoryMapper INSTANCE = Mappers.getMapper(TodoCategoryMapper.class);

    TodoCategory toEntity(TodoCategoryCommand createTodoCategoryCommand);

    TodoCategoryDto toDto(TodoCategory todoCategory);

    List<TodoCategoryDto> toDtos(List<TodoCategory> todoCategories);
}
