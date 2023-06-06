package zw.co.munyasys.todocategory.context;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TodoCategoryMapper {

    TodoCategoryMapper INSTANCE = Mappers.getMapper(TodoCategoryMapper.class);

    TodoCategory toEntity(CreateTodoCategoryCommand createTodoCategoryCommand);

    TodoCategoryDto toDto(TodoCategory todoCategory);
}
