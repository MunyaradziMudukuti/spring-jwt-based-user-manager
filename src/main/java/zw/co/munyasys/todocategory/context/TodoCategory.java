package zw.co.munyasys.todocategory.context;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import zw.co.munyasys.common.jpa.BaseEntity;
import zw.co.munyasys.users.model.User;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TodoCategory extends BaseEntity {

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(max = 250)
    private String description;

    @ManyToOne
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
