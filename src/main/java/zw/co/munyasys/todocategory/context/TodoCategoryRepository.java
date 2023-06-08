package zw.co.munyasys.todocategory.context;

import zw.co.munyasys.common.jpa.BaseDao;

import java.util.List;

public interface TodoCategoryRepository extends BaseDao<TodoCategory> {
    boolean existsByUser_UsernameAndName(String username, String name);

    List<TodoCategory> findByUser_Username(String username);
}
