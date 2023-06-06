package zw.co.munyasys.todocategory.context;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import zw.co.munyasys.common.jpa.BaseEntity;

@Data
@Entity
public class TodoCategory extends BaseEntity {

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(max = 250)
    private String description;

}
