package zw.co.munyasys.todocategory.context;

import zw.co.munyasys.common.jpa.BaseDao;

public interface TodoCategoryRepository extends BaseDao<TodoCategory> {
    boolean existsByUser_UsernameAndName(String username, String name);
}
