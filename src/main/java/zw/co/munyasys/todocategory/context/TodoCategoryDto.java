package zw.co.munyasys.todocategory.context;

import java.util.UUID;

public record TodoCategoryDto(

        UUID id,
        String name,
        String description

) {

}
