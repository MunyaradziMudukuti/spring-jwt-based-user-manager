package zw.co.munyasys.todocategory.context.dto;

import java.util.UUID;

public record TodoCategoryDto(

        UUID id,
        String name,
        String description

) {

}
