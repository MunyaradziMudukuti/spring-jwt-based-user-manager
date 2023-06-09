package zw.co.munyasys.todocategory.context.dao;

import zw.co.munyasys.common.jpa.BaseDao;
import zw.co.munyasys.todocategory.context.model.TodoCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoCategoryRepository extends BaseDao<TodoCategory> {
    boolean existsByUser_UsernameAndName(String username, String name);

    boolean existsByIdIsNotAndUser_UsernameAndName(UUID id, String username, String name);

    List<TodoCategory> findByUser_Username(String username);

    Optional<TodoCategory> findByIdAndUser_Username(UUID id, String username);
}
