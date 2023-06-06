package zw.co.munyasys.todocategory.context;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoCategoryCommand(

        @NotBlank(message = "Category name should be provided")
        String name,

        @NotBlank(message = "Description should be provided")
        String description

) {

}
