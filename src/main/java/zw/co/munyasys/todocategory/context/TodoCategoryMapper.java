package zw.co.munyasys.todocategory.context;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TodoCategoryMapper {

    TodoCategoryMapper INSTANCE = Mappers.getMapper(TodoCategoryMapper.class);

    TodoCategory toEntity(CreateTodoCategoryCommand createTodoCategoryCommand);

    TodoCategoryDto toDto(TodoCategory todoCategory);

    List<TodoCategoryDto> toDtos(List<TodoCategory> todoCategories);
}
